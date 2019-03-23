package ood.designpatterns.abstractfactory;

public class WinFactory implements GuiFactory {
    @Override
    public Button createButton() {
        return new WinButton(); // WinButton is a derived class of button
    }
}
