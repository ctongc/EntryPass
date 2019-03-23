package ood.designpatterns.abstractfactory;

public class Application {
    private static GuiFactory guiFactory;

    public Application(GuiFactory f) {
        this.guiFactory = f;
    }

    public void makeButton() {
        Button button = guiFactory.createButton();
        button.paint();
    }

}
