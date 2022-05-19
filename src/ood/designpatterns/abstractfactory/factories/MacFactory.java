package ood.designpatterns.abstractfactory.factories;

import ood.designpatterns.abstractfactory.products.buttons.Button;
import ood.designpatterns.abstractfactory.products.buttons.MacButton;
import ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;
import ood.designpatterns.abstractfactory.products.checkboxes.MacCheckbox;

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
