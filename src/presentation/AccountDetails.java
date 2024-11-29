package presentation;

import di.service.AccountService;
import domain.models.AccountModel;
import domain.models.UserModel;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Clasa care prezintă detaliile conturilor unui utilizator.
 * Permite vizualizarea conturilor unui utilizator și efectuarea unor acțiuni asupra acestora,
 * cum ar fi vizualizarea tranzacțiilor sau ștergerea unui cont.
 */
public class AccountDetails {
    private Stage primaryStage;
    private VBox layout;
    private Label resultLabel;
    private AccountService accountService;
    private VBox accountList;

    /**
     * Construieste un obiect AccountDetails pentru a vizualiza conturile unui utilizator.
     *
     * @param primaryStage fereastra principală a aplicației
     * @param user utilizatorul pentru care vor fi afișate conturile
     */
    public AccountDetails(Stage primaryStage, UserModel user) {
        this.primaryStage = primaryStage;
        this.accountService = new AccountService();
        layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f0f0;");

        resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");

        Label userLabel = new Label("Accounts for " + user.getFirstName() + " " + user.getLastName() + ":");
        userLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333;");
        layout.getChildren().add(userLabel);

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
        exitButton.setOnAction(e -> primaryStage.close());
        layout.getChildren().add(exitButton);

        accountList = new VBox(10);
        accountList.setPadding(new Insets(10));

        accountList.getChildren().add(resultLabel);

        ScrollPane scrollPane = new ScrollPane(accountList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setPrefHeight(400);

        layout.getChildren().add(scrollPane);

        loadAccounts(user);
    }

    /**
     * Încarcă lista de conturi pentru un utilizator.
     * Dacă nu există conturi, va afișa un mesaj corespunzător.
     *
     * @param user utilizatorul pentru care se vor încărca conturile
     */
    private void loadAccounts(UserModel user) {
        accountList.getChildren().clear();
        accountList.getChildren().add(resultLabel);

        try {
            List<AccountModel> accounts = accountService.getAccountsByUserId(user.getId());
            if (accounts.isEmpty()) {
                Label noAccountsLabel = new Label("No accounts found for this user!");
                noAccountsLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-font-style: italic;");
                accountList.getChildren().add(noAccountsLabel);
            } else {
                for (AccountModel account : accounts) {
                    HBox accountRow = new HBox(10);
                    Button accountButton = new Button(account.getIban());
                    accountButton.setStyle("-fx-background-color: #ffcc80; -fx-text-fill: #333; -fx-font-weight: bold; -fx-border-color: #ffab40; -fx-border-radius: 5; -fx-padding: 10 20;");
                    accountButton.setOnAction(e -> {
                        try {
                            showTransactionDetails(account, user);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle("-fx-background-color: #e57373; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
                    deleteButton.setOnAction(e -> confirmAndDeleteAccount(accounts, account, user));

                    accountRow.getChildren().addAll(accountButton, deleteButton);
                    accountList.getChildren().add(accountRow);
                }
            }
        } catch (IOException e) {
            resultLabel.setText("Error loading accounts: " + e.getMessage());
        }
    }

    /**
     * Confirma și șterge un cont din listă.
     * Afișează un mesaj de confirmare înainte de a șterge contul.
     *
     * @param listOfAccounts lista conturilor utilizatorului
     * @param account contul care urmează să fie șters
     * @param user utilizatorul pentru care se efectuează acțiunea
     */
    private void confirmAndDeleteAccount(List<AccountModel> listOfAccounts, AccountModel account, UserModel user) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Account");
        confirmationAlert.setHeaderText("Are you sure you want to delete this account?");
        confirmationAlert.setContentText("This action cannot be undone");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    System.out.println("account details");
                    System.out.println(account);
                    accountService.deleteAccountById(account);
                    resultLabel.setText("Account deleted successfully.");
                    loadAccounts(user);
                } catch (IOException e) {
                    resultLabel.setText("Error deleting account: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Returnează layout-ul principal al ferestrei.
     *
     * @return layout-ul principal
     */
    public VBox getLayout() {
        return layout;
    }

    /**
     * Afișează detaliile tranzacțiilor pentru un anumit cont.
     *
     * @param account contul pentru care se vor vizualiza tranzacțiile
     * @param user utilizatorul asociat contului
     * @throws IOException dacă apare o eroare la încărcarea tranzacțiilor
     */
    private void showTransactionDetails(AccountModel account, UserModel user) throws IOException {
        TransactionDetails transactionDetails = new TransactionDetails(primaryStage, account, user);
        primaryStage.getScene().setRoot(transactionDetails.getLayout());
    }
}
