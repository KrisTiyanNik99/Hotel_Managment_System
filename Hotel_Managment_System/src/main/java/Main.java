import controller.AppController;
import models.enums.UIElement;
import ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        AppController ap = new AppController(mw);

        ap.registerComponents(UIElement.LOGIN);
        ap.registerComponents(UIElement.REGISTER);
        ap.registerComponents(UIElement.MENU);

        ap.showLoginPanel();
        //ap.showMainPanel();
        mw.setVisible(true);
    }
}
