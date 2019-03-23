package ood.designpatterns.abstractfactory;

public class MacFactory implements GuiFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
}
