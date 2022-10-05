package com.ctong.entrypass.ood.designpatterns.abstractfactory.products.buttons;

/**
 * Abstract Factory assumes that you have several families of products,
 * structured into separate class hierarchies (Button/Checkbox). All products of
 * the same family have the common interface.
 */
public interface Button {

    void paint();
}
