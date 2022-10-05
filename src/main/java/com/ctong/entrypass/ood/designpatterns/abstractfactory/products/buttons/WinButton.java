package com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a Windows variant of a button.
 */
public class WinButton implements Button {

    @Override
    public void paint() {
        System.out.println("WinButton::paint created a Windows button.");
    }
}
