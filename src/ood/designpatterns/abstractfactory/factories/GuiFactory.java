package ood.designpatterns.abstractfactory.factories;

import ood.designpatterns.abstractfactory.products.buttons.Button;
import ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;

/**
 * Abstract factory knows about all (abstract) product types
 */
public interface GuiFactory {

    Button createButton();

    Checkbox createCheckbox();
}
