package com.mushroom.midnight.client.gui.config;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.mushroom.midnight.client.gui.config.ListConfigOptionList.Row;

public class ListConfigOptionList<T> extends AbstractOptionList<Row<T>> {

    private final Function<Value<T>, Widget> widgetFactory;
    private final Supplier<T> defaultValue;

    public ListConfigOptionList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight, List<T> entries, Function<Value<T>, Widget> widgetFactory, Supplier<T> defaultValue) {
        super(mc, width, height, top, bottom, itemHeight);
        this.widgetFactory = widgetFactory;
        this.defaultValue = defaultValue;

        for (T val : entries) {
            addEntry(new Row<>(this, new Value<>(val)));
        }
        addEntry(new Row<>(this, null));
        updateMoveStates();
    }

    public List<T> collect() {
        List<T> collected = new ArrayList<>();
        for (Row<T> row : children()) {
            if (row.value != null)
                collected.add(row.value.get());
        }
        return collected;
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

    @Override
    protected boolean isSelectedItem(int index) {
        return Objects.equals(this.getSelected(), this.children().get(index));
    }

    protected int getIndex(Row<T> row) {
        return children().indexOf(row);
    }

    protected void addNew(int index) {
        children().add(index, new Row<>(this, new Value<>(defaultValue.get())));
        for (Row<T> row : children()) row.updateMoveState();
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (getSelected() != null) {
            Row<T> sel = getSelected();
            if (!sel.widget.isFocused()) {
                if (key == GLFW.GLFW_KEY_UP && Screen.hasShiftDown()) {
                    int i = getIndex(sel);
                    if (i > 0) {
                        children().remove(i);
                        children().add(i - 1, sel);
                    }
                    return true;
                }
                if (key == GLFW.GLFW_KEY_UP && Screen.hasShiftDown()) {
                    int i = getIndex(sel);
                    if (i < getItemCount() - 1) {
                        children().remove(i);
                        children().add(i + 1, sel);
                    }
                    return true;
                }
                if (key == GLFW.GLFW_KEY_BACKSPACE) {
                    removeEntry(sel);
                    return true;
                }
            }
        }
        return super.keyPressed(key, scan, mods);
    }

    protected void updateMoveStates() {
        for (Row<T> row : children()) row.updateMoveState();
    }

    static class Row<T> extends AbstractOptionList.Entry<Row<T>> {

        protected final ListConfigOptionList<T> list;
        protected final Widget widget;
        protected final Button add;
        protected final Button remove;
        protected final Button up;
        protected final Button down;
        protected final Value<T> value;

        private final List<IGuiEventListener> children;

        Row(ListConfigOptionList<T> list, Value<T> value) {
            this.list = list;
            this.widget = value == null ? null : list.widgetFactory.apply(value);
            this.value = value;

            add = new Button(0, 0, 20, 20, "+", btn -> list.addNew(list.getIndex(this)));
            remove = value == null ? null : new Button(0, 0, 20, 20, "-", btn -> {
                list.removeEntry(this);
                list.updateMoveStates();
            });
            up = value == null ? null : new Button(0, 0, 20, 20, "\u23f6", btn -> {
                int i = list.getIndex(this);
                if (i > 0) {
                    list.children().remove(i);
                    list.children().add(i - 1, this);
                }
                list.updateMoveStates();
            });
            down = value == null ? null : new Button(0, 0, 20, 20, "\u23f7", btn -> {
                int i = list.getIndex(this);
                if (i < list.getItemCount() - 2) {
                    list.children().remove(i);
                    list.children().add(i + 1, this);
                }
                list.updateMoveStates();
            });

            children = Lists.newArrayList();
            if (widget != null) children.add(widget);
            children.add(add);
            if (remove != null) children.add(remove);
            if (up != null) children.add(up);
            if (down != null) children.add(down);
        }

        @Override
        public List<? extends IGuiEventListener> children() {
            return children;
        }

        @Override
        public void render(int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            if (widget != null) {
                widget.x = left + width / 2 - 155;
                widget.y = top + 2;
                widget.setWidth(220);
                widget.render(mouseX, mouseY, partialTicks);
            }

            add.x = left + width / 2 + 155 - 80;
            add.y = top + 2;
            add.render(mouseX, mouseY, partialTicks);

            if (remove != null) {
                remove.x = left + width / 2 + 155 - 60;
                remove.y = top + 2;
                remove.render(mouseX, mouseY, partialTicks);
            }

            if (up != null) {
                up.x = left + width / 2 + 155 - 40;
                up.y = top + 2;
                up.active = list.getIndex(this) > 0;
                up.render(mouseX, mouseY, partialTicks);
            }

            if (down != null) {
                down.x = left + width / 2 + 155 - 20;
                down.y = top + 2;
                down.render(mouseX, mouseY, partialTicks);
            }
        }

        private void updateMoveState() {
            if (up != null) {
                up.active = list.getIndex(this) > 0;
            }

            if (down != null) {
                down.active = list.getIndex(this) < list.getItemCount() - 2;
            }
        }
    }

    public static class Value<T> {

        private T value;

        public Value(T value) {
            this.value = value;
        }

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }
}
