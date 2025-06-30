import controller.AppController;
import controller.ServiceController;
import models.user.User;
import services.managers.users.UserManager;
import services.managers.users.UserManagerImpl;
import services.repos.RepoService;
import services.repos.UserRepoService;
import ui.MainWindow;
import ui.components.LoginPanel;

public class Main {
    public static void main(String[] args) {
        RepoService<User> userRepoService = new UserRepoService(null);
        UserManager userManager = new UserManagerImpl(userRepoService);

        MainWindow mw = new MainWindow();
        AppController ap = new AppController(mw, new ServiceController(null, null, null, null));

        LoginPanel loginPanel = new LoginPanel(userManager, ap);
        ap.registerComponents(loginPanel);
        ap.showLoginPanel();
        mw.setVisible(true);
    }
}
