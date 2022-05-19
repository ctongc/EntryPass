package ood.designpatterns.abstractfactory.factories;

import ood.designpatterns.abstractfactory.products.buttons.Button;
import ood.designpatterns.abstractfactory.products.buttons.WinButton;
import ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;
import ood.designpatterns.abstractfactory.products.checkboxes.WinCheckbox;

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
