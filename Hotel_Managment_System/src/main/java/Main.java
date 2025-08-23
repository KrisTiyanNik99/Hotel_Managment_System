import controller.AppController;
import ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        AppController ap = new AppController(mw);

        ap.registerComponents(loginPanel);
        ap.registerComponents(registerPanel);
        ap.registerComponents(menuPanel);

        //ap.showLoginPanel();
        ap.showMainPanel(menuPanel);
        mw.setVisible(true);
    }
}
