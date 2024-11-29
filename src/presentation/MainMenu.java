package presentation;

import data.models.User;
import di.service.AccountService;
import di.service.UserService;
import domain.mappers.UserMapper;
import domain.models.UserModel;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clasa care reprezintă meniul principal al aplicației E-Banking.
 * Permite utilizatorilor să se autentifice, să creeze un cont nou sau să afle informații despre aplicație.
 */
public class MainMenu {
    private Stage primaryStage;
    private VBox mainLayout;
    private VBox menuLayout;
    private VBox createAccountLayout;
    private VBox aboutUsLayout;

    private TextField firstNameField;
    private TextField lastNameField;
    private Label resultLabel;

    private UserService userService;

    /**
     * Constructor pentru inițializarea meniului principal.
     *
     * @param primaryStage Fereastra principală a aplicației.
     * @param users        Setul de utilizatori pentru gestionarea autentificării.
     */
    public MainMenu(Stage primaryStage, Set<UserModel> users) {
        this.primaryStage = primaryStage;
        this.userService = new UserService();

        setupMainLayout(users);
        setupCreateAccountLayout(users);
        setupAboutUsLayout();

        showMainMenu();
    }

    /**
     * Returnează layout-ul principal al aplicației.
     *
     * @return Layout-ul principal (VBox).
     */
    public VBox getLayout() {
        return mainLayout;
    }

    /**
     * Configurează și afișează layout-ul principal al aplicației.
     *
     * @param users Setul de utilizatori pentru validarea autentificării.
     */
    private void setupMainLayout(Set<UserModel> users) {
        mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #dcdcdc);");

        Label welcomeLabel = new Label("Welcome to E-Banking App!");
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Button enterAccountButton = createButton("Enter Account");
        AccountService accountService = new AccountService();
        enterAccountButton.setOnAction(e -> showEnterAccount(users));

        Button createAccountButton = createButton("Create Account");
        createAccountButton.setOnAction(e -> showCreateAccount());

        Button aboutUsButton = createButton("About Us");
        aboutUsButton.setOnAction(e -> showAboutUs());

        menuLayout = new VBox(15, welcomeLabel, enterAccountButton, createAccountButton, aboutUsButton);
        menuLayout.setAlignment(Pos.CENTER);

        mainLayout.getChildren().add(menuLayout);
    }

    /**
     * Crează un buton personalizat cu un text specificat.
     *
     * @param text Textul care va apărea pe buton.
     * @return Butonul creat.
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
     * Afișează formularul de autentificare în cont.
     * Permite utilizatorilor să își introducă numele și prenumele pentru a accesa contul.
     *
     * @param users Setul de utilizatori pentru validarea autentificării.
     */
    private void showEnterAccount(Set<UserModel> users) {
        Label instructionsLabel = new Label("Please enter your details:");
        instructionsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333333;");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        Button submitButton = createButton("Submit");
        submitButton.setOnAction(e -> handleUserSubmission(users));

        resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> showMainMenu());

        VBox enterAccountLayout = new VBox(15, instructionsLabel, firstNameField, lastNameField, submitButton, resultLabel, backButton);
        enterAccountLayout.setAlignment(Pos.CENTER);

        mainLayout.getChildren().setAll(enterAccountLayout);
    }

    /**
     * Configurează layout-ul pentru crearea unui cont nou.
     *
     * @param users Setul de utilizatori pentru gestionarea conturilor.
     */
    private void setupCreateAccountLayout(Set<UserModel> users) {
        Label createLabel = new Label("Create a New Account");
        createLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField idInput = new TextField();
        idInput.setPromptText("ID");

        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("First Name");

        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Last Name");

        Button createButton = createButton("Create Account");
        createButton.setOnAction(e -> {
            try {
                createAccount(users, idInput.getText(), firstNameInput.getText(), lastNameInput.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> showMainMenu());

        createAccountLayout = new VBox(15, createLabel, idInput, firstNameInput, lastNameInput, createButton, resultLabel, backButton);
        createAccountLayout.setAlignment(Pos.CENTER);
    }

    /**
     * Configurează layout-ul pentru secțiunea "Despre noi".
     */
    private void setupAboutUsLayout() {
        Label aboutUsLabel = new Label("About Us");
        aboutUsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label aboutText = new Label("This E-Banking application allows you to manage accounts and transactions securely and efficiently.");
        aboutText.setWrapText(true);
        aboutText.setStyle("-fx-text-fill: #555555;");

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> showMainMenu());

        aboutUsLayout = new VBox(15, aboutUsLabel, aboutText, backButton);
        aboutUsLayout.setAlignment(Pos.CENTER);
    }

    /**
     * Afișează secțiunea de creare a contului nou.
     */
    private void showCreateAccount() {
        mainLayout.getChildren().setAll(createAccountLayout);
    }

    /**
     * Afișează secțiunea "Despre noi".
     */
    private void showAboutUs() {
        mainLayout.getChildren().setAll(aboutUsLayout);
    }

    /**
     * Afișează meniul principal.
     */
    private void showMainMenu() {
        mainLayout.getChildren().setAll(menuLayout);
    }

    /**
     * Procesează autentificarea utilizatorului pe baza numelui și prenumelui.
     * Dacă utilizatorul este găsit, se vor afișa detaliile contului.
     *
     * @param users Setul de utilizatori pentru validarea autentificării.
     */
    private void handleUserSubmission(Set<UserModel> users) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        Map<String, UserModel> map = convertSetToMap(users);
        UserModel userFound = map.values().stream()
                .filter(user -> user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);

        if (userFound != null) {
            showAccountDetails(userFound);
        } else {
            resultLabel.setText("User not found! You entered: " + firstName + " " + lastName);
            fadeOutLabel(resultLabel);
        }
    }

    /**
     * Creează un cont pentru un utilizator nou și adaugă-l în setul de utilizatori.
     *
     * @param users     Setul de utilizatori pentru gestionarea conturilor.
     * @param id        ID-ul utilizatorului nou.
     * @param firstName Prenumele utilizatorului nou.
     * @param lastName  Numele utilizatorului nou.
     * @throws IOException Dacă apar erori la salvarea contului.
     */
    private void createAccount(Set<UserModel> users, String id, String firstName, String lastName) throws IOException {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID invalid!");
        }
        UserModel newUser = new UserModel(id, firstName, lastName);
        userService.addUser(users, UserMapper.toData(newUser));
        resultLabel.setText("Account created successfully for " + firstName + " " + lastName);
        fadeOutLabel(resultLabel);
    }

    /**
     * Adaugă un efect de dispariție a unui text într-un interval de 2 secunde.
     *
     * @param label Eticheta care va dispărea.
     */
    private void fadeOutLabel(Label label) {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> label.setText(""));
        pause.play();
    }

    /**
     * Afișează detaliile unui cont pentru un utilizator.
     *
     * @param user Utilizatorul pentru care vor fi afișate detaliile contului.
     */
    private void showAccountDetails(UserModel user) {
        AccountDetails accountDetails = new AccountDetails(primaryStage, user);
        primaryStage.getScene().setRoot(accountDetails.getLayout());
    }

    /**
     * Convertește un set de utilizatori într-un map pentru a facilita căutarea.
     *
     * @param users Setul de utilizatori care trebuie convertit.
     * @return Map-ul rezultat care mapează ID-urile utilizatorilor la obiectele UserModel.
     */
    private Map<String, UserModel> convertSetToMap(Set<UserModel> users) {
        Map<String, UserModel> map = new HashMap<>();
        for (UserModel user : users) {
            map.put(user.getId(), user);
        }
        return map;
    }
}