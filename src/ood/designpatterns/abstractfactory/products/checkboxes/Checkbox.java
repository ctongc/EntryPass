package ood.designpatterns.abstractfactory.products.checkboxes;

/**
 * Abstract Factory assumes that you have several families of products,
 * structured into separate class hierarchies (Button/Checkbox). All products of
 * the same family have the common interface.
 */
public interface Checkbox {

    void paint();
}
