package com.ctong.entrypass.ood.designpatterns.abstractfactory.factories;

import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons.Button;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons.WinButton;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes.WinCheckbox;

public class WinFactory implements GuiFactory {

    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}
