import controller.AppController;
import models.user.User;
import services.managers.users.UserManager;
import services.managers.users.UserManagerImpl;
import services.repos.RepoService;
import services.repos.UserRepoService;
import ui.MainWindow;
import ui.components.LoginPanel;
import ui.components.RegisterPanel;

public class Main {
    public static void main(String[] args) {
        RepoService<User> userRepoService = new UserRepoService(null);
        UserManager userManager = new UserManagerImpl(userRepoService);

        MainWindow mw = new MainWindow();
        AppController ap = new AppController(mw);

        LoginPanel loginPanel = new LoginPanel(userManager, ap);
        RegisterPanel registerPanel = new RegisterPanel(userManager, ap);

        System.out.println("Sled tova tuk idva ");
        ap.registerComponents(loginPanel);
        ap.registerComponents(registerPanel);

        ap.showLoginPanel();
        ap.showLoginPanel();
        mw.setVisible(true);
    }
}
