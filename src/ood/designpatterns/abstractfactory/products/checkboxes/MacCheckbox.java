package ood.designpatterns.abstractfactory.products.checkboxes;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a MacOS variant of a Checkbox.
 */
public class MacCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("MacCheckbox::paint created a MacOS checkbox.");
    }
}
