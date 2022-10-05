package com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a MacOS variant of a button.
 */
public class MacButton implements Button {

    @Override
    public void paint() {
        System.out.println("MacButton::paint created a MacOS button.");
    }
}
