package presentation;

import di.service.AccountService;
import di.service.TransactionService;
import di.service.UserService;
import domain.models.AccountModel;
import domain.models.TransactionModel;
import domain.models.UserModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clasa principală a aplicației.
 * Conține logica pentru inițializarea și pornirea aplicației, precum și pentru încărcarea datelor.
 */
public class MainApp extends Application {
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private TransactionService transactionService = new TransactionService();

    private Set<UserModel> setOfUsers = new HashSet<>();
    private List<AccountModel> listOfAccounts = new ArrayList<>();
    private List<TransactionModel> listOfTransactions = new ArrayList<>();

    /**
     * Metoda de start a aplicației, care încarcă datele și prezintă meniul principal.
     *
     * @param primaryStage fereastra principală a aplicației
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            setOfUsers = userService.getAllUsers().stream().collect(Collectors.toSet());
            listOfAccounts = accountService.getAllAccounts();
            System.out.println(listOfAccounts);
            listOfTransactions = transactionService.getAllTransactions();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        MainMenu mainMenu = new MainMenu(primaryStage, setOfUsers);
        Scene scene = new Scene(mainMenu.getLayout(), 800, 600);
        primaryStage.setTitle("E-Banking App");
        primaryStage.setScene(scene);
        primaryStage.show();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                accountService.saveFinalListOfAccounts();
                transactionService.saveTransactions();
            } catch (Exception e) {
                System.out.println("Error during shutdown: " + e.getMessage());
            }
        }));
    }

    /**
     * Metoda principală a aplicației, care pornește aplicația JavaFX.
     *
     * @param args argumentele din linia de comandă
     */
    public static void main(String[] args) {
        launch(args);
    }
}
