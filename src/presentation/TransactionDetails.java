package presentation;

import di.service.TransactionService;
import domain.models.AccountModel;
import domain.models.TransactionModel;
import domain.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Clasa TransactionDetails afișează istoricul tranzacțiilor pentru un cont specific
 * și permite adăugarea de tranzacții noi.
 * Aceasta interfață permite utilizatorilor să vizualizeze detalii despre tranzacțiile unui cont
 * și să adauge tranzacții noi folosind un formular simplu.
 */
public class TransactionDetails {
    private Stage primaryStage;
    private VBox layout;
    private TransactionService transactionService;

    private TextField amountField;
    private TextField dateField;
    private VBox transactionBox;

    /**
     * Constructorul clasei TransactionDetails.
     * Inițializează interfața pentru istoricul tranzacțiilor unui cont și un formular pentru adăugarea de tranzacții.
     *
     * @param primaryStage Fereastra principală a aplicației.
     * @param account      Contul pentru care se vor afișa tranzacțiile.
     * @param user         Utilizatorul conectat la aplicație.
     * @throws IOException Dacă există erori la accesarea datelor tranzacțiilor.
     */
    public TransactionDetails(Stage primaryStage, AccountModel account, UserModel user) throws IOException {
        this.primaryStage = primaryStage;
        this.transactionService = new TransactionService();
        layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #dcdcdc);");
        layout.setAlignment(Pos.TOP_CENTER);

        Label headerLabel = new Label("Transaction History");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.DARKBLUE);
        layout.getChildren().add(headerLabel);

        Label accountLabel = new Label("Account IBAN: " + account.getIban());
        accountLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        accountLabel.setTextFill(Color.web("#333"));
        layout.getChildren().add(accountLabel);

        Separator separator = new Separator();
        layout.getChildren().add(separator);

        layout.getChildren().add(createNewTransactionForm(account));

        Button saveButton = createButton("Add Transaction");
        saveButton.setOnAction(event -> {
            try {
                addNewTransaction(account);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        layout.getChildren().add(saveButton);

        Separator separator2 = new Separator();
        layout.getChildren().add(separator2);

        transactionBox = new VBox(10);
        transactionBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(transactionBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        loadTransactions(account);
        layout.getChildren().add(scrollPane);

        Button backButton = createButton("Back to Accounts");
        backButton.setOnAction(event -> {
            AccountDetails accountsScreen = new AccountDetails(primaryStage, user);
            Scene scene = new Scene(accountsScreen.getLayout(), 800, 600);
            primaryStage.setScene(scene);
        });

        layout.getChildren().add(backButton);
    }

    /**
     * Creează formularul pentru adăugarea unei noi tranzacții.
     * Permite utilizatorului să introducă suma și data tranzacției.
     *
     * @param account Contul pentru care se adaugă tranzacția.
     * @return Un VBox cu elementele necesare pentru a adăuga o tranzacție.
     * @throws IOException Dacă apare o eroare în procesul de creare a formularului.
     */
    private VBox createNewTransactionForm(AccountModel account) throws IOException {
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.setStyle("-fx-background-color: #eeeeee; -fx-padding: 15px; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label formTitle = new Label("Add new transaction:");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        formTitle.setTextFill(Color.DARKBLUE);

        amountField = new TextField();
        amountField.setPromptText("Amount");
        dateField = new TextField();
        dateField.setPromptText("Date (YYYY-MM-DD)");

        formLayout.getChildren().addAll(formTitle, new Label("Amount:"), amountField, new Label("Date:"), dateField);
        return formLayout;
    }

    /**
     * Încarcă tranzacțiile pentru contul specificat și le afișează pe ecran.
     * Dacă nu există tranzacții, se va afișa un mesaj corespunzător.
     *
     * @param account Contul pentru care se încarcă tranzacțiile.
     * @throws IOException Dacă există erori la accesarea tranzacțiilor.
     */
    private void loadTransactions(AccountModel account) throws IOException {
        transactionBox.getChildren().clear();

        List<TransactionModel> transactions = transactionService.getTransactionsByAccountIban(account.getIban());
        if (transactions.isEmpty()) {
            Label noTransactionsLabel = new Label("No transactions found.");
            noTransactionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            noTransactionsLabel.setTextFill(Color.GRAY);
            transactionBox.getChildren().add(noTransactionsLabel);
        } else {
            for (TransactionModel transaction : transactions) {
                transactionBox.getChildren().add(createTransactionItem(transaction));
            }
        }
    }

    /**
     * Adaugă o nouă tranzacție la contul specificat.
     * Tranzacția este salvată și se va actualiza lista de tranzacții afișată.
     *
     * @param account Contul pentru care se adaugă tranzacția.
     * @throws IOException Dacă apare o eroare la adăugarea tranzacției.
     */
    private void addNewTransaction(AccountModel account) throws IOException {
        double amount = Double.parseDouble(amountField.getText());
        LocalDate localDate = dateField.getText().isEmpty() ? LocalDate.now() :
                LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        TransactionModel newTransaction = new TransactionModel(
                account.getIban(), amount, date
        );

        transactionService.addTransaction(newTransaction);
        loadTransactions(account);

        amountField.clear();
        dateField.clear();
    }

    /**
     * Creează un item de tranzacție care este afișat într-un HBox.
     * Fiecare tranzacție include suma și data acesteia.
     *
     * @param transaction Tranzacția de afișat.
     * @return Un HBox ce conține detaliile tranzacției.
     */
    private HBox createTransactionItem(TransactionModel transaction) {
        HBox transactionItem = new HBox(15);
        transactionItem.setPadding(new Insets(10));
        transactionItem.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5; -fx-border-color: #dcdcdc; -fx-border-radius: 5;");
        transactionItem.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(transaction.getAmount() < 0 ? "⬇️" : "⬆️");
        iconLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        iconLabel.setTextFill(transaction.getAmount() < 0 ? Color.RED : Color.GREEN);

        Label amountLabel = new Label(String.format("Amount: %.2f", transaction.getAmount()));
        amountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        amountLabel.setTextFill(transaction.getAmount() < 0 ? Color.RED : Color.GREEN);

        Label dateLabel = new Label("Date: " + transaction.getDate().toString());
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        dateLabel.setTextFill(Color.GRAY);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        transactionItem.getChildren().addAll(iconLabel, amountLabel, spacer, dateLabel);
        return transactionItem;
    }

    /**
     * Creează un buton personalizat cu stilizare.
     *
     * @param text Textul butonului.
     * @return Butonul creat cu stilul aplicat.
     */
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #a0d8b3; -fx-text-fill: #333333; -fx-font-size: 16px; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setPrefWidth(200);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #9bc7a7; -fx-text-fill: #333333; -fx-font-size: 16px; -fx-border-radius: 5px; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #a0d8b3; -fx-text-fill: #333333; -fx-font-size: 16px; -fx-border-radius: 5px; -fx-padding: 10px;"));
        return button;
    }

    /**
     * Returnează layout-ul principal al scenei pentru tranzacțiile unui cont.
     *
     * @return VBox-ul care conține toate elementele UI pentru istoricul tranzacțiilor.
     */

    public VBox getLayout() {
        return layout;
    }
}
