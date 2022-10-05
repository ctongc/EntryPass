package com.ctong.entrypass.ood.designpatterns.abstractfactory.products.checkboxes;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a Windows variant of a checkbox.
 */
public class WinCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("WinCheckbox::paint created a Windows checkbox.");
    }
}
