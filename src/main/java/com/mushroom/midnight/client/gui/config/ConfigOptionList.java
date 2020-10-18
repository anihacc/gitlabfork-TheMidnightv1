package com.mushroom.midnight.client.gui.config;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigOptionList extends AbstractOptionList<ConfigOptionList.Row> {

    public ConfigOptionList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
        super(mc, width, height, top, bottom, itemHeight);
    }

    @Override
    public int addEntry(Row entry) {
        return super.addEntry(entry);
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    protected int getScrollbarPosition() {
        return width - 6;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void renderHoleBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buff = tess.getBuffer();
        minecraft.getTextureManager().bindTexture(ConfigInterfaceScreen.MN_BACKGROUND_LOCATION);
        RenderSystem.color4f(1, 1, 1, 1);

        // @formatter:off
        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buff.pos(x0,         bottom, 0).tex(0,           bottom / 32F).color(64, 64, 64, bottomAlpha).endVertex();
        buff.pos(x0 + width, bottom, 0).tex(width / 32F, bottom / 32F).color(64, 64, 64, bottomAlpha).endVertex();
        buff.pos(x0 + width, top,    0).tex(width / 32F, top    / 32F).color(64, 64, 64, topAlpha   ).endVertex();
        buff.pos(x0,         top,    0).tex(0,           top    / 32F).color(64, 64, 64, topAlpha   ).endVertex();
        tess.draw();
        // @formatter:on
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        int scrollbarLeft = getScrollbarPosition();
        int scrollbarRight = scrollbarLeft + 6;

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buff = tess.getBuffer();
        minecraft.getTextureManager().bindTexture(ConfigInterfaceScreen.MN_BACKGROUND_LOCATION);
        RenderSystem.color4f(1, 1, 1, 1);

        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buff.pos(x0, y1, 0).tex(x0 / 32F, (y1 + (int) getScrollAmount()) / 32F).color(32, 32, 32, 255).endVertex();
        buff.pos(x1, y1, 0).tex(x1 / 32F, (y1 + (int) getScrollAmount()) / 32F).color(32, 32, 32, 255).endVertex();
        buff.pos(x1, y0, 0).tex(x1 / 32F, (y0 + (int) getScrollAmount()) / 32F).color(32, 32, 32, 255).endVertex();
        buff.pos(x0, y0, 0).tex(x0 / 32F, (y0 + (int) getScrollAmount()) / 32F).color(32, 32, 32, 255).endVertex();
        tess.draw();

        int left = getRowLeft();
        int top = y0 + 4 - (int) getScrollAmount();
        if (renderHeader) {
            renderHeader(left, top, tess);
        }

        renderList(left, top, mouseX, mouseY, partialTicks);
        RenderSystem.disableDepthTest();
        renderHoleBackground(0, y0, 255, 255);
        renderHoleBackground(y1, height, 255, 255);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        RenderSystem.disableAlphaTest();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        RenderSystem.disableTexture();

        // @formatter:off
        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buff.pos(x0, y0 + 4, 0).tex(0, 1).color(0, 0, 0, 0  ).endVertex();
        buff.pos(x1, y0 + 4, 0).tex(1, 1).color(0, 0, 0, 0  ).endVertex();
        buff.pos(x1, y0,     0).tex(1, 0).color(0, 0, 0, 255).endVertex();
        buff.pos(x0, y0,     0).tex(0, 0).color(0, 0, 0, 255).endVertex();
        tess.draw();

        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buff.pos(x0, y1,     0).tex(0, 1).color(0, 0, 0, 255).endVertex();
        buff.pos(x1, y1,     0).tex(1, 1).color(0, 0, 0, 255).endVertex();
        buff.pos(x1, y1 - 4, 0).tex(1, 0).color(0, 0, 0, 0  ).endVertex();
        buff.pos(x0, y1 - 4, 0).tex(0, 0).color(0, 0, 0, 0  ).endVertex();
        tess.draw();
        // @formatter:on

        int maxScroll = getMaxScroll();
        if (maxScroll > 0) {
            int scrollbarHeight = (int) (((y1 - y0) * (y1 - y0)) / (float) getMaxPosition());
            scrollbarHeight = MathHelper.clamp(scrollbarHeight, 32, y1 - y0 - 8);

            int scrollbarY = (int) getScrollAmount() * (y1 - y0 - scrollbarHeight) / maxScroll + y0;
            if (scrollbarY < y0) {
                scrollbarY = y0;
            }

            // @formatter:off
            buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buff.pos(scrollbarLeft,  y1, 0).tex(0, 1).color(0, 0, 0, 255).endVertex();
            buff.pos(scrollbarRight, y1, 0).tex(1, 1).color(0, 0, 0, 255).endVertex();
            buff.pos(scrollbarRight, y0, 0).tex(1, 0).color(0, 0, 0, 255).endVertex();
            buff.pos(scrollbarLeft,  y0, 0).tex(0, 0).color(0, 0, 0, 255).endVertex();
            tess.draw();

            buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buff.pos(scrollbarLeft,  scrollbarY + scrollbarHeight, 0).tex(0, 1).color(128, 128, 128, 255).endVertex();
            buff.pos(scrollbarRight, scrollbarY + scrollbarHeight, 0).tex(1, 1).color(128, 128, 128, 255).endVertex();
            buff.pos(scrollbarRight, scrollbarY,                   0).tex(1, 0).color(128, 128, 128, 255).endVertex();
            buff.pos(scrollbarLeft,  scrollbarY,                   0).tex(0, 0).color(128, 128, 128, 255).endVertex();
            tess.draw();

            buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buff.pos(scrollbarLeft,      scrollbarY + scrollbarHeight - 1, 0).tex(0, 1).color(192, 192, 192, 255).endVertex();
            buff.pos(scrollbarRight - 1, scrollbarY + scrollbarHeight - 1, 0).tex(1, 1).color(192, 192, 192, 255).endVertex();
            buff.pos(scrollbarRight - 1, scrollbarY,                       0).tex(1, 0).color(192, 192, 192, 255).endVertex();
            buff.pos(scrollbarLeft,      scrollbarY,                       0).tex(0, 0).color(192, 192, 192, 255).endVertex();
            tess.draw();
            // @formatter:on
        }

        renderDecorations(mouseX, mouseY);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
    }

    private int getMaxScroll() {
        return Math.max(0, getMaxPosition() - (y1 - y0 - 4));
    }

    public static class Row extends AbstractOptionList.Entry<Row> {

        protected final String label;
        protected final Widget widget;
        protected final ConfigInterfaceScreen screen;
        protected List<String> tooltip;

        private final List<IGuiEventListener> children;

        public Row(String label, Widget widget, ConfigInterfaceScreen screen) {
            this.label = label;
            this.widget = widget;
            this.screen = screen;
            if (widget != null) {
                this.children = Collections.singletonList(widget);
            } else {
                this.children = Collections.emptyList();
            }
        }

        public Row withTooltip(String... tooltip) {
            this.tooltip = Arrays.asList(tooltip);
            return this;
        }

        @Override
        public List<? extends IGuiEventListener> children() {
            return children;
        }

        protected void renderTooltip(int mouseX, int mouseY, boolean mouseOver) {
            if (mouseOver) {
                screen.queueTooltip(tooltip);
            }
        }

        @Override
        public void render(int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            renderTooltip(mouseX, mouseY, isMouseOver);

            FontRenderer font = Minecraft.getInstance().fontRenderer;
            font.drawStringWithShadow(label, left + (float) (width / 2) - 155, top + height / 2F - 2, 0xffffffff);
            if (widget != null) {
                widget.x = left + width / 2 + 5;
                widget.y = top + 2;
                widget.render(mouseX, mouseY, partialTicks);
            }
        }
    }

    public static class ButtonOnlyRow extends Row {

        public ButtonOnlyRow(Widget widget, ConfigInterfaceScreen screen) {
            super("", widget, screen);
        }

        @Override
        public void render(int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            renderTooltip(mouseX, mouseY, isMouseOver);

            if (widget != null) {
                widget.x = left + width / 2 - widget.getWidth() / 2;
                widget.y = top + 2;
                widget.render(mouseX, mouseY, partialTicks);
            }
        }
    }

    public static class HeaderRow extends Row {

        public HeaderRow(String label, ConfigInterfaceScreen screen) {
            super(label, null, screen);
        }


        protected void drawCenteredString(FontRenderer fontRenderer, String string, int x, int y, int color) {
            fontRenderer.drawStringWithShadow(string, (float) (x - fontRenderer.getStringWidth(string) / 2), (float) y, color);
        }

        @Override
        public void render(int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            renderTooltip(mouseX, mouseY, isMouseOver);

            FontRenderer font = Minecraft.getInstance().fontRenderer;
            drawCenteredString(font, TextFormatting.BOLD + label + TextFormatting.RESET, left + width / 2, top + height / 2 - 2, 0xffffffff);
        }
    }
}
