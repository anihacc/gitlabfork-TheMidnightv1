package com.mushroom.midnight.client.gui.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class MainConfigScreen extends AbstractConfigScreen {
    private final Button doneButton;


    protected MainConfigScreen(ITextComponent title, Screen previousScreen) {
        super(title, previousScreen);
        this.doneButton = new Button(0, 0, 200, 20, "Done", button -> onClose());

        optionList.addEntry(new ConfigOptionList.HeaderRow("This is a header", this).withTooltip("Tooltip", "More tooltip", "More tooltip\nAnd more tooltip after a newline", "More tooltip", "More tooltip", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.ButtonOnlyRow(new Button(0, 0, 200, 20, "Category Button", button -> {
        }), this).withTooltip("Tooltip of a category button", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
        optionList.addEntry(new ConfigOptionList.Row("Setting", new Button(0, 0, 150, 20, "Setting Button", button -> {
        }), this).withTooltip("Tooltip of a setting", "More tooltip"));
    }

    @Override
    protected void init() {
        super.init();

        doneButton.x = width / 2 - 100;
        doneButton.y = height - 26;

        addButton(doneButton);
    }
}
