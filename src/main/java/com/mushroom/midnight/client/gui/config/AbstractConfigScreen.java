package com.mushroom.midnight.client.gui.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigScreen extends Screen {
    public static final ResourceLocation MN_BACKGROUND_LOCATION = new ResourceLocation("midnight:textures/gui/config_background.png");

    private final Screen previousScreen;
    protected final ConfigOptionList optionList;

    private List<String> tooltip;

    protected AbstractConfigScreen(ITextComponent title, Screen previousScreen) {
        super(title);
        this.previousScreen = previousScreen;

        optionList = new ConfigOptionList(Minecraft.getInstance(), width, height, 32, height - 32, 24);
    }

    @Override
    protected void init() {
        children.add(optionList);
        optionList.updateSize(width, height, 32, height - 32);
    }

    public void queueTooltip(List<String> tt) {
        tooltip = tt;
    }

    protected void renderTooltip(int mouseX, int mouseY) {
        if (tooltip == null) return;

        List<String> lines = new ArrayList<>();
        int h = 0;
        FontRenderer font = Minecraft.getInstance().fontRenderer;

        // Collect all lines to render
        for (String str : tooltip) {
            List<String> wrapped = font.listFormattedStringToWidth(str, width - 64);
            for (String line : wrapped) {
                lines.add(line);
                h += 9;
            }
        }

        if (lines.isEmpty()) return;

        int overlayWidth = width - 58;
        int overlayHeight = h + 6;
        int overlayX = 29;
        int overlayY = height - 32 - overlayHeight;

        if (mouseX >= overlayX && mouseY >= overlayY && mouseX <= overlayX + overlayWidth && mouseY <= overlayY + overlayHeight) {
            overlayY = 32; // Flip to top when mouse is in the overlay
        }

        RenderSystem.disableDepthTest();
        GuiUtils.drawGradientRect(50, overlayX, overlayY, overlayX + overlayWidth, overlayY + overlayHeight, 0xAA000000, 0xAA000000);

        int lineY = overlayY + 3;
        int lineX = 32;
        for (String str : lines) {
            font.drawStringWithShadow(str, lineX, lineY + 1, 0xffffffff);
            lineY += 9;
        }

        tooltip = null;
    }

    @Override
    public void onClose() {
        minecraft.displayGuiScreen(previousScreen);
    }

    @Override
    public void render(int mx, int my, float partialTicks) {
        renderDirtBackground(0);
        optionList.render(mx, my, partialTicks);
        super.render(mx, my, partialTicks);
        drawCenteredString(font, title.getFormattedText(), width / 2, 16, 0xffffffff);
        renderTooltip(mx, my);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void renderDirtBackground(int yOffset) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buff = tess.getBuffer();

        minecraft.getTextureManager().bindTexture(MN_BACKGROUND_LOCATION);
        RenderSystem.color4f(1, 1, 1, 1);

        // @formatter:off
        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buff.pos(0,     height, 0).tex(0,           height / 32.0F + yOffset).color(64, 64, 64, 255).endVertex();
        buff.pos(width, height, 0).tex(width / 32F, height / 32.0F + yOffset).color(64, 64, 64, 255).endVertex();
        buff.pos(width, 0,      0).tex(width / 32F, yOffset                 ).color(64, 64, 64, 255).endVertex();
        buff.pos(0,     0,      0).tex(0,           yOffset                 ).color(64, 64, 64, 255).endVertex();
        tess.draw();
        // @formatter:on

        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.BackgroundDrawnEvent(this));
    }
}
