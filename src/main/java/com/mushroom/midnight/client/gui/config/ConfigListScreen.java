package com.mushroom.midnight.client.gui.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConfigListScreen<T> extends Screen {
    public static final ResourceLocation MN_BACKGROUND_LOCATION = new ResourceLocation("midnight:textures/gui/config_background.png");

    private final Screen previousScreen;
    protected final ListConfigOptionList<T> list;
    private final Button doneButton;
    private final Button cancelButton;

    public ConfigListScreen(Screen previousScreen, ITextComponent title, List<T> entries, Function<ListConfigOptionList.Value<T>, Widget> widgetFactory, Supplier<T> defaultValue, Consumer<List<T>> handler) {
        super(title);
        this.previousScreen = previousScreen;

        list = new ListConfigOptionList<>(Minecraft.getInstance(), width, height, 32, height - 32, 24, entries, widgetFactory, defaultValue);
        doneButton = new Button(0, 0, 150, 20, I18n.format("gui.done"), button -> {
            handler.accept(list.collect());
            onClose();
        });
        cancelButton = new Button(0, 0, 150, 20, I18n.format("gui.cancel"), button -> {
            onClose();
        });
    }

    @Override
    protected void init() {
        children.add(list);
        list.updateSize(width, height, 32, height - 32);

        cancelButton.x = width / 2 - 155;
        cancelButton.x = width / 2 - 155;
        cancelButton.y = height - 26;
        addButton(cancelButton);

        doneButton.x = width / 2 + 5;
        doneButton.y = height - 26;
        addButton(doneButton);
    }

    @Override
    public void onClose() {
        minecraft.displayGuiScreen(previousScreen);
    }

    @Override
    public void render(int mx, int my, float partialTicks) {
        renderDirtBackground(0);
        list.render(mx, my, partialTicks);
        super.render(mx, my, partialTicks);
        drawCenteredString(font, title.getFormattedText(), width / 2, 16, 0xffffffff);
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
