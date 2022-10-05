package com.ctong.entrypass.ood.designpatterns.abstractfactory.factories;

import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes.Checkbox;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons.Button;

/**
 * Abstract factory knows about all (abstract) product types
 */
public interface GuiFactory {

    Button createButton();

    Checkbox createCheckbox();
}
