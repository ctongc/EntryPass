package com.ctong.entrypass.ood.designpatterns.abstractfactory.factories;

import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons.Button;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons.MacButton;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes.MacCheckbox;

public class MacFactory implements GuiFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
