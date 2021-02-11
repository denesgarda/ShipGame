package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Main extends Application {
    double gameVersion = 2.2;
    String account = "";
    boolean inGame = false;
    boolean doWinAlert = true;

    Random rand = new Random();
    int money = 2000;
    int food = 100;
    int cotton = 0;
    int steel = 0;
    int gold = 0;
    int diamond = 0;
    int wood = 0;
    int cottonPrice = 0;
    int steelPrice = 0;
    int goldPrice = 0;
    int diamondPrice = 0;
    int woodPrice = 0;
    int foodPrice = 0;
    int amountToBuy = 0;
    int amountToSell = 0;
    Text portTitle = null;
    Text portInventory = null;
    Text yourInventory = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
    String whatToBuyEnd = null;
    String whatToSellEnd = null;
    Label moneyBeforeBuy = new Label("Current Money: $" + money);
    Label moneyBeforeSell = new Label("Current Money: $" + money);
    String[] portNames = {"Havenbrorough", "Woodham", "Coldfield", "Prumore", "Nurith", "Hapool", "Glilsall", "Vondon", "Zorothin", "Aresset", "Okphis", "Drakta", "Verpbury", "Umul", "Blora", "Blora"};
    @Override
    public void start(Stage primaryStage) throws Exception {
        java.sql.Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/shipgame?user=connector&password=pwd123");
            //conn.setNetworkTimeout(Executors.newSingleThreadExecutor(), 5000);
            // Do something with the Connection
            java.sql.Connection finalConn = conn;
            java.sql.Connection finalConn11 = conn;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if(inGame) {
                    if(!(account.equals(""))) {
                        boolean addLoss = Connection.addLoss(finalConn11, account, gameVersion);
                    }
                }
                try {
                    finalConn.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex);
                }
            }));
        }
        catch (SQLException ex) {
            //handle any errors
            //Alert alert = new Alert(Alert.AlertType.WARNING, "Unable to connect to server. Online functionalities will not work. If you want to use online functionalities, check your connection and try again.", ButtonType.OK);
            //alert.setTitle("Network connection error");
            //alert.setHeaderText("Network connection error");
            //alert.showAndWait();
            System.out.println(ex);
        }
        if(!(conn == null)) {
            if (gameVersion < Connection.retrieveMaxVersion(conn)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are playing an older version of the game. It is highly recommended you update. Do you want to download it now?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Outdated version");
                alert.setHeaderText("Outdated version");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    getHostServices().showDocument(Connection.retrieveLatestLink(conn, Connection.retrieveMaxVersion(conn)));
                    System.exit(0);
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, " Unable to connect to server. Online functionalities will not work. If you want to use online functionalities, check your connection and try again.", ButtonType.OK);
            alert.setTitle("Network connection error");
            alert.setHeaderText("Network connection error");
            alert.showAndWait();
        }
        Button quit = new Button("Quit To Main Menu");
        quit.setStyle("-fx-font-size: 12px;");
        quit.setLayoutX(275);
        quit.setLayoutY(370);
        Pane mainMenuPane = new Pane();
        Button playOnline = new Button("Play Online");
        playOnline.setStyle("-fx-font-size: 15px;");
        playOnline.setLayoutX(150);
        playOnline.setLayoutY(180);
        mainMenuPane.getChildren().add(playOnline);
        Button patchNotes = new Button("Patch Notes");
        patchNotes.setStyle("-fx-font-size: 15px;");
        patchNotes.setLayoutX(148);
        patchNotes.setLayoutY(300);
        mainMenuPane.getChildren().add(patchNotes);
        Text welcome = new Text("Welcome to the Ship Game!");
        welcome.setX(50);
        welcome.setY(150);
        welcome.setStyle("-fx-font-size: 25px;");
        mainMenuPane.getChildren().add(welcome);
        Text version = new Text("v2.2");
        version.setStyle("-fx-font-size: 12px;");
        version.setX(375);
        version.setY(390);
        mainMenuPane.getChildren().add(version);
        Button playOffline = new Button("Play Offline");
        playOffline.setStyle("-fx-font-size: 15px;");
        playOffline.setLayoutX(150);
        playOffline.setLayoutY(220);
        mainMenuPane.getChildren().add(playOffline);
        Button viewStats = new Button("View Stats");
        viewStats.setStyle("-fx-font-size: 15px;");
        viewStats.setLayoutX(155);
        viewStats.setLayoutY(220);
        Button quitGame = new Button("Quit Game");
        quitGame.setStyle("-fx-font-size: 15px;");
        quitGame.setLayoutX(10);
        quitGame.setLayoutY(360);
        mainMenuPane.getChildren().add(quitGame);
        Button feedback = new Button("Feedback");
        feedback.setStyle("-fx-font-size: 15px;");
        feedback.setLayoutX(156);
        feedback.setLayoutY(340);
        mainMenuPane.getChildren().add(feedback);
        Button howToPlay = new Button("How To Play");
        howToPlay.setStyle("-fx-font-size: 15px;");
        howToPlay.setLayoutX(147);
        howToPlay.setLayoutY(260);
        mainMenuPane.getChildren().add(howToPlay);
        Scene mainMenu = new Scene(mainMenuPane, 400, 400);
        primaryStage.setTitle("The Ship Game v2.2");
        primaryStage.setScene(mainMenu);
        primaryStage.show();
        Pane howToPlayPane = new Pane();
        Text howToPlayTitle = new Text("How To Play");
        howToPlayTitle.setX(130);
        howToPlayTitle.setY(150);
        howToPlayTitle.setStyle("-fx-font-size: 25px;");
        howToPlayPane.getChildren().add(howToPlayTitle);
        Button settings = new Button("Settings");
        settings.setStyle("-fx-font-size: 15px;");
        settings.setLayoutX(10);
        settings.setLayoutY(10);
        Text howToPlayText = new Text("You start off with $2000, and you have to buy, sell, and sail to get $60000.\n" +
                "                 Each time you sail, your food diminishes by 5.\n" +
                "             You can also buy food at ports. You cannot sell food.");
        howToPlayText.setStyle("-fx-font-size: 12px;");
        howToPlayText.setLayoutX(10);
        howToPlayText.setLayoutY(180);
        howToPlayPane.getChildren().add(howToPlayText);
        Button backToMainMenu = new Button("Back To Main Menu");
        backToMainMenu.setStyle("-fx-font-size: 15px;");
        backToMainMenu.setLayoutX(122);
        backToMainMenu.setLayoutY(300);
        howToPlayPane.getChildren().add(backToMainMenu);
        Scene howToPlayScene = new Scene(howToPlayPane, 400, 400);
        Pane loginSignupPane = new Pane();
        Button loginOption = new Button("Login");
        loginOption.setStyle("-fx-font-size: 15px;");
        loginOption.setLayoutX(180);
        loginOption.setLayoutY(150);
        loginSignupPane.getChildren().add(loginOption);
        Button SignupOption = new Button("Sign up");
        SignupOption.setStyle("-fx-font-size: 15px;");
        SignupOption.setLayoutX(175);
        SignupOption.setLayoutY(225);
        loginSignupPane.getChildren().add(SignupOption);
        Button forgotPasswordOption = new Button("Forgot Password");
        forgotPasswordOption.setStyle("-fx-font-size: 15px;");
        forgotPasswordOption.setLayoutX(150);
        forgotPasswordOption.setLayoutY(300);
        loginSignupPane.getChildren().add(forgotPasswordOption);
        Scene loginSignupScene = new Scene(loginSignupPane, 400, 400);
        howToPlay.setOnAction(event -> {
            primaryStage.setScene(howToPlayScene);
        });
        java.sql.Connection finalConn12 = conn;
        backToMainMenu.setOnAction(event -> {
            if(inGame) {
                if(!(account.equals(""))) {
                    boolean addLoss = Connection.addLoss(finalConn12, account, gameVersion);
                }
            }
            inGame = false;
            primaryStage.setScene(mainMenu);
        });
        java.sql.Connection finalConn8 = conn;
        playOnline.setOnAction(event -> {
            if(!(finalConn8 == null)) {
                loginSignupPane.getChildren().add(quit);
                primaryStage.setScene(loginSignupScene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are playing in the offline version of the game. If you want to use online functionalities, check your connection and relaunch the game.", ButtonType.OK);
                alert.setTitle("Network connection error");
                alert.setHeaderText("Network connection error");
                alert.showAndWait();
            }
        });
        java.sql.Connection finalConn2 = conn;
        java.sql.Connection finalConn3 = conn;
        loginOption.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Login");
            dialog.setHeaderText("Login");
            dialog.setContentText("Username:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String username = result.get();
                if (username.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username is null", ButtonType.OK);
                    alert.setTitle("Username is null");
                    alert.setHeaderText("Username is null");
                    alert.showAndWait();
                } else {
                    boolean successful = Connection.checkUsername(finalConn2, username);
                    if (successful) {
                        TextInputDialog dialog2 = new TextInputDialog("");
                        dialog2.setTitle("Login");
                        dialog2.setHeaderText("Login");
                        dialog2.setContentText("Password:");
                        Optional<String> result2 = dialog2.showAndWait();
                        if (result2.isPresent()) {
                            String password = result2.get();
                            if (password.equals("")) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is null", ButtonType.OK);
                                alert.setTitle("Password is null");
                                alert.setHeaderText("Password is null");
                                alert.showAndWait();
                            } else {
                                boolean successful2 = Connection.checkPassword(finalConn3, username, password);
                                if (successful2) {
                                    account = username;
                                    mainMenuPane.getChildren().add(viewStats);
                                    playOffline.setText("Play");
                                    playOffline.setLayoutX(175);
                                    playOffline.setLayoutY(180);
                                    mainMenuPane.getChildren().add(settings);
                                    mainMenuPane.getChildren().remove(playOnline);
                                    primaryStage.setScene(mainMenu);
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Online mode has now been activated", ButtonType.OK);
                                    alert.setTitle("Activated");
                                    alert.setHeaderText("Activated");
                                    alert.showAndWait();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong password.", ButtonType.OK);
                                    alert.setTitle("Invalid credentials");
                                    alert.setHeaderText("Invalid credentials");
                                    alert.showAndWait();
                                }
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Account with that username does not exist.", ButtonType.OK);
                        alert.setTitle("Invalid credentials");
                        alert.setHeaderText("Invalid credentials");
                        alert.showAndWait();
                    }
                }
            }
        });
        java.sql.Connection finalConn4 = conn;
        java.sql.Connection finalConn5 = conn;
        java.sql.Connection finalConn18 = conn;
        SignupOption.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Sign Up");
            dialog.setHeaderText("Sign Up");
            dialog.setContentText("Create Username:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String username = result.get();
                if (username.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username is null", ButtonType.OK);
                    alert.setTitle("Username is null");
                    alert.setHeaderText("Username is null");
                    alert.showAndWait();
                } else {
                    boolean exists = Connection.checkUsername(finalConn4, username);
                    if (exists) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Username is already taken", ButtonType.OK);
                        alert.setTitle("Username is already taken");
                        alert.setHeaderText("Username is already taken");
                        alert.showAndWait();
                    } else {
                        TextInputDialog dialog2 = new TextInputDialog("");
                        dialog2.setTitle("Sign Up");
                        dialog2.setHeaderText("Sign Up");
                        dialog2.setContentText("Create Password:");
                        Optional<String> result2 = dialog2.showAndWait();
                        if (result2.isPresent()) {
                            String password = result2.get();
                            if (password.equals("")) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is null", ButtonType.OK);
                                alert.setTitle("Password is null");
                                alert.setHeaderText("Password is null");
                                alert.showAndWait();
                            } else {
                                TextInputDialog dialog3 = new TextInputDialog("");
                                dialog3.setTitle("Sign Up");
                                dialog3.setHeaderText("Sign Up");
                                dialog3.setContentText("Enter email:");
                                Optional<String> result3 = dialog3.showAndWait();
                                if (result3.isPresent()) {
                                    String email = result3.get();
                                    boolean exists2 = Connection.checkEmail(finalConn18, email);
                                    if(result3.equals("")) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Email is null", ButtonType.OK);
                                        alert.setTitle("Email is null");
                                        alert.setHeaderText("Email is null");
                                        alert.showAndWait();
                                    }
                                    else if(exists2) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Email is already in use by another account", ButtonType.OK);
                                        alert.setTitle("Email is already in use");
                                        alert.setHeaderText("Email is already in use");
                                        alert.showAndWait();
                                    }
                                    else if(email.contains("@") && email.contains(".")) {
                                        boolean successful = Connection.insertUser(finalConn5, username, password, email);
                                        if (successful) {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account created! Login to use.", ButtonType.OK);
                                            alert.setTitle("Account created");
                                            alert.setHeaderText("Account created");
                                            alert.showAndWait();
                                        } else {
                                            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! Your account could not be created.", ButtonType.OK);
                                            alert.setTitle("Something went wrong!");
                                            alert.setHeaderText("Something went wrong!");
                                            alert.showAndWait();
                                        }
                                    }
                                    else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Email is invalid", ButtonType.OK);
                                        alert.setTitle("Email is invalid");
                                        alert.setHeaderText("Email is invalid");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        java.sql.Connection finalConn19 = conn;
        java.sql.Connection finalConn20 = conn;
        forgotPasswordOption.setOnAction(event ->{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Forgot Password");
            dialog.setHeaderText("Forgot Password");
            dialog.setContentText("Enter email:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String email = result.get();
                boolean exists = Connection.checkEmail(finalConn19, email);
                if(exists) {
                    int randomNumber = rand.nextInt(99999);
                    String from = "theshipgame.management";
                    String pass = "theshipgamepassword";
                    String[] to = {email}; // list of recipient email addresses
                    String subject = "Your ShipGame code";
                    String body = "Your code is " + randomNumber + ".";
                    Connection.sendFromGMail(from, pass, to, subject, body);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "A code has been sent to your email. Once you got it, click next. \nIf you didn't get the code, wait a minute or two. \nIf you still didn't recieve it, check your connection and try again.", ButtonType.NEXT);
                    alert.setTitle("Code sent");
                    alert.setHeaderText("Code sent");
                    alert.showAndWait();
                    TextInputDialog dialog2 = new TextInputDialog("");
                    dialog2.setTitle("Code");
                    dialog2.setHeaderText("Enter the code that was sent to your email.");
                    dialog2.setContentText("Enter code:");
                    Optional<String> result2 = dialog2.showAndWait();
                    if (result2.isPresent()) {
                        try {
                            if (Integer.parseInt(result2.get()) == randomNumber) {
                                TextInputDialog dialog3 = new TextInputDialog("");
                                dialog3.setTitle("Create password");
                                dialog3.setHeaderText("Create password");
                                dialog3.setContentText("Create new password:");
                                Optional<String> result3 = dialog3.showAndWait();
                                if (result3.isPresent()) {
                                    String password = result3.get();
                                    if (password.equals("")) {
                                        Alert alert2 = new Alert(Alert.AlertType.ERROR, "Password is null", ButtonType.OK);
                                        alert2.setTitle("Password is null");
                                        alert2.setHeaderText("Password is null");
                                        alert2.showAndWait();
                                    } else {
                                        boolean successful = Connection.changePassword(finalConn20, email, password);
                                        if (successful) {
                                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Your password has been changed. Login to use.", ButtonType.OK);
                                            alert2.setTitle("Password changed");
                                            alert2.setHeaderText("Password changed");
                                            alert2.showAndWait();
                                        } else {
                                            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Something went wrong. Your password could not be changed.", ButtonType.OK);
                                            alert2.setTitle("Something went wrong");
                                            alert2.setHeaderText("Something went wrong");
                                            alert2.showAndWait();
                                        }
                                    }
                                }
                            } else {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR, "The code your entered is incorrect", ButtonType.CLOSE);
                                alert2.setTitle("Incorrect code");
                                alert2.setHeaderText("Incorrect code");
                                alert2.showAndWait();
                            }
                        }
                        catch(Exception e) {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR, "The code your entered is incorrect", ButtonType.CLOSE);
                            alert2.setTitle("Incorrect code");
                            alert2.setHeaderText("Incorrect code");
                            alert2.showAndWait();
                        }
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Email is not associated with an account", ButtonType.OK);
                    alert.setTitle("Email does not exist");
                    alert.setHeaderText("Email does not exist");
                    alert.showAndWait();
                }
            }
        });
        java.sql.Connection finalConn1 = conn;
        java.sql.Connection finalConn9 = conn;
        java.sql.Connection finalConn14 = conn;
        java.sql.Connection finalConn15 = conn;
        feedback.setOnAction(event -> {
            if(!(finalConn9 == null)) {
                if(gameVersion == Connection.retrieveMaxVersion(finalConn14)) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Feedback");
                    dialog.setHeaderText("Enter feedback, bugs, etc.");
                    dialog.setContentText("Feedback:");
                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String feedbackToSend = result.get();
                        if (feedbackToSend.equals("")) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Feedback was not recorded as input was null.", ButtonType.OK);
                            alert.setTitle("Feedback was not recorded");
                            alert.setHeaderText("Feedback was not recorded");
                            alert.showAndWait();
                        } else {
                            boolean successful = Connection.insertFeedbackRow(finalConn1, feedbackToSend);
                            if (successful) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thank you for your feedback!", ButtonType.OK);
                                alert.setTitle("Thank you for your feedback");
                                alert.setHeaderText("Thank you for your feedback");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Feedback was not recorded as something went wrong!", ButtonType.OK);
                                alert.setTitle("Error");
                                alert.setHeaderText("Something went wrong");
                                alert.showAndWait();
                            }
                        }
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, ("You are not using the latest supported version of the game. You are using either an outdated version, or a pre-release version. Please use v." + Connection.retrieveMaxVersion(finalConn15) + "."), ButtonType.OK);
                    alert.setTitle("Unsupported version");
                    alert.setHeaderText("Unsupported version");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are playing in the offline version of the game. If you want to use online functionalities, check your connection and relaunch the game.", ButtonType.OK);
                alert.setTitle("Network connection error");
                alert.setHeaderText("Network connection error");
                alert.showAndWait();
            }
            // The Java 8 way to get the response value (with lambda expression).
        });
        Pane settingsPane = new Pane();
        Text accountSettings = new Text("Account Settings");
        accountSettings.setStyle("-fx-font-size: 25px;");
        accountSettings.setX(120);
        accountSettings.setY(150);
        settingsPane.getChildren().add(accountSettings);
        Button logOut = new Button("Log Out");
        logOut.setStyle("-fx-font-size: 15px;");
        logOut.setLayoutX(165);
        logOut.setLayoutY(175);
        settingsPane.getChildren().add(logOut);
        Button changeUsername = new Button("Change Username");
        changeUsername.setStyle("-fx-font-size: 15px;");
        changeUsername.setLayoutX(135);
        changeUsername.setLayoutY(225);
        settingsPane.getChildren().add(changeUsername);
        //TODO add change password
        Button changePassword = new Button("Change Password");
        changePassword.setStyle("-fx-font-size: 15px;");
        changePassword.setLayoutX(137);
        changePassword.setLayoutY(275);
        settingsPane.getChildren().add(changePassword);
        //TODO add change email
        Button changeEmail = new Button("Change Email");
        changeEmail.setStyle("-fx-font-size: 15px;");
        changeEmail.setLayoutX(148);
        changeEmail.setLayoutY(325);
        settingsPane.getChildren().add(changeEmail);
        Scene settingsScene = new Scene(settingsPane, 400, 400);
        settings.setOnAction(event ->{
            if(!(account.equals(""))) {
                settingsPane.getChildren().add(quit);
                primaryStage.setScene(settingsScene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. Could not access account settings.", ButtonType.OK);
                alert.setTitle("Something went wrong");
                alert.setHeaderText("Something went wrong");
                alert.showAndWait();
            }
        });
        logOut.setOnAction(event ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Continue?");
            alert.setHeaderText("Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                account = "";
                mainMenuPane.getChildren().remove(viewStats);
                playOffline.setText("Play Offline");
                playOffline.setLayoutX(150);
                playOffline.setLayoutY(220);
                mainMenuPane.getChildren().remove(settings);
                mainMenuPane.getChildren().add(playOnline);
                primaryStage.setScene(mainMenu);
            }
        });
        java.sql.Connection finalConn21 = conn;
        java.sql.Connection finalConn22 = conn;
        changeUsername.setOnAction(event ->{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Change username");
            dialog.setHeaderText("Enter your current username");
            dialog.setContentText("Current username:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String username = result.get();
                if(username.equals(account)) {
                    TextInputDialog dialog2 = new TextInputDialog("");
                    dialog2.setTitle("Change username");
                    dialog2.setHeaderText("Enter your new username");
                    dialog2.setContentText("New username:");
                    Optional<String> result2 = dialog2.showAndWait();
                    if (result2.isPresent()) {
                        String newUsername = result2.get();
                        if(newUsername.equals("")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Username is null", ButtonType.OK);
                            alert.setTitle("Username is null");
                            alert.setHeaderText("Username is null");
                            alert.showAndWait();
                        }
                        else if(Connection.checkUsername(finalConn22, newUsername)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Username is already taken", ButtonType.OK);
                            alert.setTitle("Username is taken");
                            alert.setHeaderText("Username is taken");
                            alert.showAndWait();
                        }
                        else {
                            boolean successful = Connection.changeUsername(finalConn21, username, newUsername);
                            if(successful) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, ("Your username has been changed to " + newUsername + "."), ButtonType.OK);
                                alert.setTitle("Username changed");
                                alert.setHeaderText("Username changed");
                                alert.showAndWait();
                                account = newUsername;
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK);
                                alert.setTitle("Something went wrong");
                                alert.setHeaderText("Something went wrong");
                                alert.showAndWait();
                            }
                        }
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The username you entered is incorrect.", ButtonType.OK);
                    alert.setTitle("Incorrect username");
                    alert.setHeaderText("Incorrect username");
                    alert.showAndWait();
                }
            }
        });
        patchNotes.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "- 1.0\n" +
                    "    - Made the game\n" +
                    "- 1.1\n" +
                    "    - Removed console updates\n" +
                    "    - Fixed food buying bug\n" +
                    "    - Fixed selling bug\n" +
                    "    - Added patch notes section\n" +
                    "    - Added straightforward insufficient balance alerts\n" +
                    "    - Added better slider intervals\n" +
                    "    - Added money statistics\n" +
                    "    - Made the slider more user friendly\n" +
                    "- 1.2\n" +
                    "    - Made the online mode alert more user friendly\n" +
                    "    - Added network support\n" +
                    "    - Added feedback section\n" +
                    "    - Added automatic updater\n" +
                    "- 2.0\n" +
                    "    - Fixed early game end bug\n" +
                    "    - Added online mode\n" +
                    "    - Fixed timeout bug\n" +
                    "    - Fixed outdated game version bug\n" +
                    "    - Fixed no offline mode bug\n" +
                    "- 2.1\n" +
                    "    - Changed to allow feedback only from latest supported version\n" +
                    "    - Converted to exe\n" +
                    "    - Added option to continue after win\n" +
                    "    - Added account settings\n" +
                    "    - Added option to sign out\n" +
                    "    - Added new stats menu\n" +
                    "    - Set button focus to play online\n" +
                    "    - Made it so you can only change stats using latest supported version\n" +
                    "- 2.2\n" +
                    "    - Fixed a type\n" +
                    "    - Added quit confirmation message", ButtonType.OK);
            alert.setTitle("Patch Notes");
            alert.setHeaderText("Patch Notes");
            alert.showAndWait();
        });
        java.sql.Connection finalConn6 = conn;
        java.sql.Connection finalConn7 = conn;
        java.sql.Connection finalConn10 = conn;
        Pane statsPane = new Pane();
        Text accountStats = new Text("Account Stats");
        accountStats.setStyle("-fx-font-size: 25px;");
        accountStats.setX(140);
        accountStats.setY(150);
        statsPane.getChildren().add(accountStats);
        Button myStats = new Button("My Stats");
        myStats.setStyle("-fx-font-size: 15px;");
        myStats.setLayoutX(5);
        myStats.setLayoutY(200);
        statsPane.getChildren().add(myStats);
        Button friendsLeaderboard = new Button("Friends Leaderboard");
        friendsLeaderboard.setStyle("-fx-font-size: 15px;");
        friendsLeaderboard.setLayoutX(85);
        friendsLeaderboard.setLayoutY(200);
        statsPane.getChildren().add(friendsLeaderboard);
        Button globalLeaderboard = new Button("Global Leaderboard");
        globalLeaderboard.setStyle("-fx-font-size: 15px;");
        globalLeaderboard.setLayoutX(245);
        globalLeaderboard.setLayoutY(200);
        statsPane.getChildren().add(globalLeaderboard);
        Scene statsScene = new Scene(statsPane, 400, 400);
        viewStats.setOnAction(event -> {
            if(!(finalConn10 == null)) {
                statsPane.getChildren().add(quit);
                primaryStage.setScene(statsScene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are playing in the offline version of the game. If you want to use online functionalities, check your connection and relaunch the game", ButtonType.OK);
                alert.setTitle("Network connection error");
                alert.setHeaderText("Network connection error");
                alert.showAndWait();
            }
        });
        myStats.setOnAction(event ->{
            if(!(finalConn10 == null)) {
                String wins = Connection.getWins(finalConn6, account);
                String losses = Connection.getLosses(finalConn7, account);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, ("You have " + wins + " wins and " + losses + " losses."), ButtonType.OK);
                alert.setTitle("Stats");
                alert.setHeaderText("Stats");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are playing in the offline version of the game. If you want to use online functionalities, check your connection and relaunch the game", ButtonType.OK);
                alert.setTitle("Network connection error");
                alert.setHeaderText("Network connection error");
                alert.showAndWait();
            }
        });
        friendsLeaderboard.setOnAction(event ->{
            Alert alert = new Alert(Alert.AlertType.ERROR, "This feature has not yet been implemented.", ButtonType.OK);
            alert.setTitle("Coming soon");
            alert.setHeaderText("Coming soon");
            alert.showAndWait();
        });
        globalLeaderboard.setOnAction(event ->{
            Alert alert = new Alert(Alert.AlertType.ERROR, "This feature has not yet been implemented.", ButtonType.OK);
            alert.setTitle("Coming soon");
            alert.setHeaderText("Coming soon");
            alert.showAndWait();
        });
        Random rand = new Random();
        Pane sailingPane = new Pane();
        Text sailing = new Text("Sailing to trading port...");
        sailing.setStyle("-fx-font-size: 20px;");
        sailing.setX(100);
        sailing.setY(200);
        sailingPane.getChildren().add(sailing);
        Button dock = new Button("Dock");
        dock.setStyle("-fx-font-size: 15px;");
        dock.setLayoutX(170);
        dock.setLayoutY(300);
        sailingPane.getChildren().add(dock);
        Scene sailingScene = new Scene(sailingPane, 400, 400);
        Pane portPane = new Pane();
        String portName = portNames[rand.nextInt(15)];
        Text pt1 = new Text(portName + " trading port");
        portTitle = pt1;
        portTitle.setStyle("-fx-font-size: 20px;");
        portTitle.setTextAlignment(TextAlignment.CENTER);
        portTitle.setX(110);
        portTitle.setY(50);
        portPane.getChildren().add(portTitle);
        Text t1 = new Text("Port Inventory:\n1. Cotton: $" + cottonPrice + "\n2. Steel: $" + steelPrice + "\n3. Gold: $" + goldPrice + "\n4. Diamond: $" + diamondPrice + "\n5. Wood: $" + woodPrice + "\n6. Food: $" + foodPrice + " for 15 food");
        portInventory = t1;
        portInventory.setStyle("-fx-font-size: 15px;");
        portInventory.setX(10);
        portInventory.setY(80);
        portPane.getChildren().add(portInventory);
        Text inv = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
        yourInventory = inv;
        yourInventory.setStyle("-fx-font-size: 15px;");
        yourInventory.setX(200);
        yourInventory.setY(80);
        portPane.getChildren().add(yourInventory);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font-size: 15px;");
        buy.setLayoutX(70);
        buy.setLayoutY(300);
        portPane.getChildren().add(buy);
        Button sell = new Button("Sell");
        sell.setStyle("-fx-font-size: 15px;");
        sell.setLayoutX(170);
        sell.setLayoutY(300);
        portPane.getChildren().add(sell);
        Button sail = new Button("Sail");
        sail.setStyle("-fx-font-size: 15px;");
        sail.setLayoutX(270);
        sail.setLayoutY(300);
        portPane.getChildren().add(sail);
        Scene portScene = new Scene(portPane, 400, 400);
        Pane whatToBuyPane = new Pane();
        Text whatToBuy = new Text("What do you want to buy?");
        whatToBuy.setStyle("-fx-font-size: 20px;");
        whatToBuy.setX(90);
        whatToBuy.setY(100);
        whatToBuyPane.getChildren().add(whatToBuy);
        Button buyCotton = new Button("Cotton");
        buyCotton.setStyle("-fx-font-size: 15px;");
        buyCotton.setLayoutX(160);
        buyCotton.setLayoutY(140);
        whatToBuyPane.getChildren().add(buyCotton);
        Button buySteel = new Button("Steel");
        buySteel.setStyle("-fx-font-size: 15px;");
        buySteel.setLayoutX(167);
        buySteel.setLayoutY(180);
        whatToBuyPane.getChildren().add(buySteel);
        Button buyGold = new Button("Gold");
        buyGold.setStyle("-fx-font-size: 15px;");
        buyGold.setLayoutX(167.5);
        buyGold.setLayoutY(220);
        whatToBuyPane.getChildren().add(buyGold);
        Button buyDiamond = new Button("Diamond");
        buyDiamond.setStyle("-fx-font-size: 15px;");
        buyDiamond.setLayoutX(154);
        buyDiamond.setLayoutY(260);
        whatToBuyPane.getChildren().add(buyDiamond);
        Button buyWood = new Button("Wood");
        buyWood.setStyle("-fx-font-size: 15px;");
        buyWood.setLayoutX(165);
        buyWood.setLayoutY(300);
        whatToBuyPane.getChildren().add(buyWood);
        Button buyFood = new Button("Food");
        buyFood.setStyle("-fx-font-size: 15px;");
        buyFood.setLayoutX(167);
        buyFood.setLayoutY(340);
        whatToBuyPane.getChildren().add(buyFood);
        Button cancelBuy = new Button("Cancel");
        cancelBuy.setStyle("-fx-font-size: 15px;");
        cancelBuy.setLayoutX(10);
        cancelBuy.setLayoutY(360);
        whatToBuyPane.getChildren().add(cancelBuy);
        Scene whatToBuyScene = new Scene(whatToBuyPane, 400, 400);
        Pane howMuchToBuyPane = new Pane();
        Text howMuch = new Text("How much do you want to buy?");
        howMuch.setStyle("-fx-font-size: 20px;");
        howMuch.setLayoutX(60);
        howMuch.setLayoutY(150);
        howMuchToBuyPane.getChildren().add(howMuch);
        Button cancelBuy2 = new Button("Cancel");
        cancelBuy2.setStyle("-fx-font-size: 15px;");
        cancelBuy2.setLayoutX(10);
        cancelBuy2.setLayoutY(360);
        howMuchToBuyPane.getChildren().add(cancelBuy2);
        Slider howMuchToBuy = new Slider(0, 100, 0.5);
        howMuchToBuy.setShowTickMarks(true);
        howMuchToBuy.setShowTickLabels(true);
        howMuchToBuy.setMajorTickUnit(25f);
        howMuchToBuy.setLayoutX(130);
        howMuchToBuy.setLayoutY(200);
        howMuchToBuyPane.getChildren().add(howMuchToBuy);
        Label l = new Label(" ");
        l.setStyle("-fx-font-size: 15px;");
        l.setLayoutX(190);
        l.setLayoutY(250);
        l.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        howMuchToBuy.valueProperty()
                )
        );
        howMuchToBuyPane.getChildren().add(l);
        moneyBeforeBuy.setStyle("-fx-font-size: 15px;");
        moneyBeforeBuy.setLayoutX(10);
        moneyBeforeBuy.setLayoutY(10);
        howMuchToBuyPane.getChildren().add(moneyBeforeBuy);
        Text insufficientBalanceBuy = new Text("Insufficient Balance!");
        insufficientBalanceBuy.setStyle("-fx-font-size: 15px;");
        insufficientBalanceBuy.setLayoutX(130);
        insufficientBalanceBuy.setLayoutY(200);
        howMuchToBuyPane.getChildren().add(insufficientBalanceBuy);
        Button buyButton = new Button("Buy");
        buyButton.setStyle("-fx-font-size: 15px;");
        buyButton.setLayoutX(175);
        buyButton.setLayoutY(290);
        howMuchToBuyPane.getChildren().add(buyButton);
        Scene howMuchToBuyScene = new Scene(howMuchToBuyPane, 400, 400);
        java.sql.Connection finalConn16 = conn;
        java.sql.Connection finalConn17 = conn;
        playOffline.setOnAction(event -> {
            if(!(finalConn16 == null)) {
                if (account != "") {
                    if(!(gameVersion == Connection.retrieveMaxVersion(finalConn17))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "You are using either an outdated version of the game or a pre-release version, so new stats will not be added to your account.", ButtonType.OK);
                        alert.setTitle("Unsupported Version");
                        alert.setHeaderText("Unsupported Version");
                        alert.showAndWait();
                    }
                }
            }
            doWinAlert = true;
            inGame = true;
            portPane.getChildren().remove(portInventory);
            portPane.getChildren().remove(yourInventory);
            money = 2000;
            food = 100;
            cottonPrice = rand.nextInt(70) + 229;
            steelPrice = rand.nextInt(170) + 230;
            goldPrice = rand.nextInt(700) + 1092;
            diamondPrice = rand.nextInt(500) + 2600;
            woodPrice = rand.nextInt(95) + 275;
            foodPrice = rand.nextInt(2000) + 4000;
            cotton = 0;
            steel = 0;
            gold = 0;
            diamond = 0;
            wood = 0;
            Text t = new Text("Port Inventory:\n1. Cotton: $" + cottonPrice + "\n2. Steel: $" + steelPrice + "\n3. Gold: $" + goldPrice + "\n4. Diamond: $" + diamondPrice + "\n5. Wood: $" + woodPrice + "\n6. Food: $" + foodPrice + " for 15 food");
            portInventory = t;
            portInventory.setStyle("-fx-font-size: 15px;");
            portInventory.setX(10);
            portInventory.setY(80);
            Text t3 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
            yourInventory = inv;
            yourInventory.setStyle("-fx-font-size: 15px;");
            yourInventory.setX(200);
            yourInventory.setY(80);
            portPane.getChildren().add(portInventory);
            portPane.getChildren().add(yourInventory);
            sailingPane.getChildren().add(quit);
            primaryStage.setScene(sailingScene);
        /*Pane testPane = new Pane();
        Text testText = new Text("test text");
        testText.setX(10);
        testText.setY(10);
        testPane.getChildren().add(testText);
        Scene testScene = new Scene(testPane, 400, 400);
        primaryStage.setScene(testScene);*/
        });
        dock.setOnAction(event -> {
            portPane.getChildren().remove(portInventory);
            portPane.getChildren().remove(yourInventory);
            portPane.getChildren().add(portInventory);
            portPane.getChildren().add(yourInventory);
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
        });
        buy.setOnAction(event -> {
            howMuchToBuyPane.getChildren().remove(moneyBeforeBuy);
            Label m = new Label("Current Money: $" + money);
            moneyBeforeBuy = m;
            moneyBeforeBuy.setStyle("-fx-font-size: 15px;");
            moneyBeforeBuy.setLayoutX(10);
            moneyBeforeBuy.setLayoutY(10);
            howMuchToBuyPane.getChildren().add(moneyBeforeBuy);
            whatToBuyPane.getChildren().add(quit);
            primaryStage.setScene(whatToBuyScene);
        });
        quit.setOnAction(event -> {
            if(inGame) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit to the main menu? You will lose all of your progress.", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Are you sure you want to quit?");
                alert.setHeaderText("Are you sure you want to quit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    if(!(account.equals(""))) {
                        boolean addLoss = Connection.addLoss(finalConn12, account, gameVersion);
                    }
                    inGame = false;
                    mainMenuPane.getChildren().add(quit);
                    mainMenuPane.getChildren().remove(quit);
                    primaryStage.setScene(mainMenu);
                }
            }
            else {
                inGame = false;
                mainMenuPane.getChildren().add(quit);
                mainMenuPane.getChildren().remove(quit);
                primaryStage.setScene(mainMenu);
            }
        });
        cancelBuy.setOnAction(event -> {
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
        });
        cancelBuy2.setOnAction(event -> {
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
        });
        buyCotton.setOnAction(event -> {
            whatToBuyEnd = ("cotton");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / cottonPrice));
            if (money < cottonPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / cottonPrice) / 4)));
            }
        });
        buySteel.setOnAction(event -> {
            whatToBuyEnd = ("steel");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / steelPrice));
            if (money < steelPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / steelPrice) / 4)));
            }
        });
        buyGold.setOnAction(event -> {
            whatToBuyEnd = ("gold");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / goldPrice));
            if (money < goldPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / goldPrice) / 4)));
            }
        });
        buyDiamond.setOnAction(event -> {
            whatToBuyEnd = ("diamond");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / diamondPrice));
            if (money < diamondPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / diamondPrice) / 4)));
            }
        });
        buyWood.setOnAction(event -> {
            whatToBuyEnd = ("wood");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / woodPrice));
            if (money < woodPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / woodPrice) / 4)));
            }
        });
        buyFood.setOnAction(event -> {
            whatToBuyEnd = ("food");
            howMuchToBuyPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToBuyScene);
            howMuchToBuy.setMax(Math.floor(money / foodPrice));
            if (money < foodPrice) {
                howMuchToBuy.setVisible(false);
                l.setVisible(false);
                buyButton.setVisible(false);
                insufficientBalanceBuy.setVisible(true);
            } else {
                howMuchToBuy.setVisible(true);
                l.setVisible(true);
                buyButton.setVisible(true);
                insufficientBalanceBuy.setVisible(false);
                howMuchToBuy.setMajorTickUnit(((int) (Math.floor(money / foodPrice) / 4)));
            }
        });
        Pane losePane = new Pane();
        Text youLose = new Text("YOU LOST!");
        youLose.setStyle("-fx-font-size: 20px;");
        youLose.setLayoutX(150);
        youLose.setLayoutY(150);
        losePane.getChildren().add(youLose);
        Scene loseScene = new Scene(losePane, 400, 400);
        Pane winPane = new Pane();
        Text youWin = new Text("YOU WIN!");
        youWin.setStyle("-fx-font-size: 20px;");
        youWin.setLayoutX(150);
        youWin.setLayoutY(150);
        winPane.getChildren().add(youWin);
        Scene winScene = new Scene(winPane, 400, 400);
        java.sql.Connection finalConn13 = conn;
        buyButton.setOnAction(event -> {
            amountToBuy = (int) Math.round(howMuchToBuy.getValue());
            portPane.getChildren().remove(yourInventory);
            if (whatToBuyEnd.equals("cotton")) {
                cotton += amountToBuy;
                money -= (cottonPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToBuyEnd.equals("steel")) {
                steel += amountToBuy;
                money -= (steelPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToBuyEnd.equals("gold")) {
                gold += amountToBuy;
                money -= (goldPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToBuyEnd.equals("diamond")) {
                diamond += amountToBuy;
                money -= (diamondPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToBuyEnd.equals("wood")) {
                wood += amountToBuy;
                money -= (woodPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToBuyEnd.equals("food")) {
                food += (amountToBuy * 15);
                money -= (foodPrice * amountToBuy);
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            }
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
            if (money <= 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            } else if (money >= 60000) {
                if(doWinAlert) {
                    if (!(account.equals(""))) {
                        if (inGame) {
                            boolean addWin = Connection.addWin(finalConn13, account, gameVersion);
                            if (addWin) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "A win has been added to your account stats!", ButtonType.OK);
                                alert.setTitle("You win");
                                alert.setHeaderText("You win");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Something went wrong. A win could not be added to your account stats.", ButtonType.OK);
                                alert.setTitle("Something went wrong");
                                alert.setHeaderText("Something went wrong");
                                alert.showAndWait();
                            }
                        }
                    }
                    winPane.getChildren().add(quit);
                    primaryStage.setScene(winScene);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to continue playing? If you lose, it wont count as a loss since you already won.", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Continue?");
                    alert.setHeaderText("Continue?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        doWinAlert = false;
                        sailingPane.getChildren().add(quit);
                        primaryStage.setScene(sailingScene);
                    }
                }
                inGame = false;
            } else if (food < 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            }
        });
        sail.setOnAction(event -> {
            int oldFood = food;
            portPane.getChildren().remove(yourInventory);
            food = oldFood - 5;
            Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
            yourInventory = inv2;
            yourInventory.setStyle("-fx-font-size: 15px;");
            yourInventory.setX(200);
            yourInventory.setY(80);
            portPane.getChildren().add(yourInventory);
            portPane.getChildren().remove(portTitle);
            String portName2 = portNames[rand.nextInt(15)];
            Text pt2 = new Text(portName2 + " trading port");
            portTitle = pt2;
            portTitle.setStyle("-fx-font-size: 20px;");
            portTitle.setTextAlignment(TextAlignment.CENTER);
            portTitle.setX(110);
            portTitle.setY(50);
            portPane.getChildren().add(portTitle);
            portPane.getChildren().remove(portInventory);
            cottonPrice = rand.nextInt(70) + 229;
            steelPrice = rand.nextInt(170) + 230;
            goldPrice = rand.nextInt(700) + 1092;
            diamondPrice = rand.nextInt(500) + 2600;
            woodPrice = rand.nextInt(95) + 275;
            foodPrice = rand.nextInt(2000) + 4000;
            Text t = new Text("Port Inventory:\n1. Cotton: $" + cottonPrice + "\n2. Steel: $" + steelPrice + "\n3. Gold: $" + goldPrice + "\n4. Diamond: $" + diamondPrice + "\n5. Wood: $" + woodPrice + "\n6. Food: $" + foodPrice + " for 15 food");
            portInventory = t;
            portInventory.setStyle("-fx-font-size: 15px;");
            portInventory.setX(10);
            portInventory.setY(80);
            portPane.getChildren().add(portInventory);
            sailingPane.getChildren().add(quit);
            primaryStage.setScene(sailingScene);
            food = oldFood - 5;
            if (money <= 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            } else if (money >= 60000) {
                if(doWinAlert) {
                    if (!(account.equals(""))) {
                        if(inGame) {
                            boolean addWin = Connection.addWin(finalConn13, account, gameVersion);
                            if (addWin) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "A win has been added to your account stats!", ButtonType.OK);
                                alert.setTitle("You win");
                                alert.setHeaderText("You win");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Something went wrong. A win could not be added to your account stats.", ButtonType.OK);
                                alert.setTitle("Something went wrong");
                                alert.setHeaderText("Something went wrong");
                                alert.showAndWait();
                            }
                        }
                    }
                    winPane.getChildren().add(quit);
                    primaryStage.setScene(winScene);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to continue playing? If you lose, it wont count as a loss since you already won.", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Continue?");
                    alert.setHeaderText("Continue?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        doWinAlert = false;
                        sailingPane.getChildren().add(quit);
                        primaryStage.setScene(sailingScene);
                    }
                }
                inGame = false;
            } else if (food < 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            }
        });
        Pane whatToSellPane = new Pane();
        Text whatToSellText = new Text("What do you want to sell?");
        whatToSellText.setStyle("-fx-font-size: 25px;");
        whatToSellText.setX(60);
        whatToSellText.setY(100);
        whatToSellPane.getChildren().add(whatToSellText);
        Button sellCotton = new Button("Cotton");
        sellCotton.setStyle("-fx-font-size: 15px;");
        sellCotton.setLayoutX(160);
        sellCotton.setLayoutY(140);
        whatToSellPane.getChildren().add(sellCotton);
        Button sellSteel = new Button("Steel");
        sellSteel.setStyle("-fx-font-size: 15px;");
        sellSteel.setLayoutX(167);
        sellSteel.setLayoutY(180);
        whatToSellPane.getChildren().add(sellSteel);
        Button sellGold = new Button("Gold");
        sellGold.setStyle("-fx-font-size: 15px;");
        sellGold.setLayoutX(167.5);
        sellGold.setLayoutY(220);
        whatToSellPane.getChildren().add(sellGold);
        Button sellDiamond = new Button("Diamond");
        sellDiamond.setStyle("-fx-font-size: 15px;");
        sellDiamond.setLayoutX(154);
        sellDiamond.setLayoutY(260);
        whatToSellPane.getChildren().add(sellDiamond);
        Button sellWood = new Button("Wood");
        sellWood.setStyle("-fx-font-size: 15px;");
        sellWood.setLayoutX(165);
        sellWood.setLayoutY(300);
        whatToSellPane.getChildren().add(sellWood);
        Button cancelSell = new Button("Cancel");
        cancelSell.setStyle("-fx-font-size: 15px;");
        cancelSell.setLayoutX(10);
        cancelSell.setLayoutY(360);
        whatToSellPane.getChildren().add(cancelSell);
        Scene whatToSellScene = new Scene(whatToSellPane, 400, 400);
        Pane howMuchToSellPane = new Pane();
        sell.setOnAction(event -> {
            howMuchToSellPane.getChildren().remove(moneyBeforeSell);
            Label m = new Label("Current Money: $" + money);
            moneyBeforeSell = m;
            moneyBeforeSell.setStyle("-fx-font-size: 15px;");
            moneyBeforeSell.setLayoutX(10);
            moneyBeforeSell.setLayoutY(10);
            howMuchToSellPane.getChildren().add(moneyBeforeSell);
            whatToSellPane.getChildren().add(quit);
            primaryStage.setScene(whatToSellScene);
        });
        Text howMuchToSellText = new Text("How much do you want to sell?");
        howMuchToSellText.setStyle("-fx-font-size: 20px;");
        howMuchToSellText.setX(60);
        howMuchToSellText.setY(150);
        howMuchToSellPane.getChildren().add(howMuchToSellText);
        Button cancelSell2 = new Button("Cancel");
        cancelSell2.setStyle("-fx-font-size: 15px;");
        cancelSell2.setLayoutX(10);
        cancelSell2.setLayoutY(360);
        howMuchToSellPane.getChildren().add(cancelSell2);
        moneyBeforeSell.setStyle("-fx-font-size: 15px;");
        moneyBeforeSell.setLayoutX(10);
        moneyBeforeSell.setLayoutY(10);
        howMuchToSellPane.getChildren().add(moneyBeforeSell);
        Slider howMuchToSell = new Slider(0, 100, 0.5);
        howMuchToSell.setShowTickMarks(true);
        howMuchToSell.setShowTickLabels(true);
        howMuchToSell.setLayoutX(130);
        howMuchToSell.setLayoutY(200);
        howMuchToSellPane.getChildren().add(howMuchToSell);
        Label l2 = new Label(" ");
        l2.setStyle("-fx-font-size: 15px;");
        l2.setLayoutX(190);
        l2.setLayoutY(250);
        l2.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        howMuchToSell.valueProperty()
                )
        );
        howMuchToSellPane.getChildren().add(l2);
        Button sellButton2 = new Button("Sell");
        sellButton2.setStyle("-fx-font-size: 15px;");
        sellButton2.setLayoutX(175);
        sellButton2.setLayoutY(290);
        howMuchToSellPane.getChildren().add(sellButton2);
        Text insufficientBalance = new Text("Insufficient Balance!");
        insufficientBalance.setStyle("-fx-font-size: 15px;");
        insufficientBalance.setLayoutX(130);
        insufficientBalance.setLayoutY(200);
        howMuchToSellPane.getChildren().add(insufficientBalance);
        Scene howMuchToSellScene = new Scene(howMuchToSellPane, 400, 400);
        sellCotton.setOnAction(event -> {
            whatToSellEnd = "cotton";
            howMuchToSellPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToSellScene);
            howMuchToSell.setMax(cotton);
            if (cotton == 0) {
                howMuchToSell.setVisible(false);
                l2.setVisible(false);
                sellButton2.setVisible(false);
                insufficientBalance.setVisible(true);
            } else {
                howMuchToSell.setVisible(true);
                l2.setVisible(true);
                sellButton2.setVisible(true);
                insufficientBalance.setVisible(false);
                howMuchToSell.setMajorTickUnit(((int) (cotton / 4)));
            }
        });
        sellSteel.setOnAction(event -> {
            whatToSellEnd = "steel";
            howMuchToSellPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToSellScene);
            howMuchToSell.setMax(steel);
            if (steel == 0) {
                howMuchToSell.setVisible(false);
                l2.setVisible(false);
                sellButton2.setVisible(false);
                insufficientBalance.setVisible(true);
            } else {
                howMuchToSell.setVisible(true);
                l2.setVisible(true);
                sellButton2.setVisible(true);
                insufficientBalance.setVisible(false);
                howMuchToSell.setMajorTickUnit(((int) (steel / 4)));
            }
        });
        sellGold.setOnAction(event -> {
            whatToSellEnd = "gold";
            howMuchToSellPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToSellScene);
            howMuchToSell.setMax(gold);
            if (gold == 0) {
                howMuchToSell.setVisible(false);
                l2.setVisible(false);
                sellButton2.setVisible(false);
                insufficientBalance.setVisible(true);
            } else {
                howMuchToSell.setVisible(true);
                l2.setVisible(true);
                sellButton2.setVisible(true);
                insufficientBalance.setVisible(false);
                howMuchToSell.setMajorTickUnit(((int) (gold / 4)));
            }
        });
        sellDiamond.setOnAction(event -> {
            whatToSellEnd = "diamond";
            howMuchToSellPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToSellScene);
            howMuchToSell.setMax(diamond);
            if (diamond == 0) {
                howMuchToSell.setVisible(false);
                l2.setVisible(false);
                sellButton2.setVisible(false);
                insufficientBalance.setVisible(true);
            } else {
                howMuchToSell.setVisible(true);
                l2.setVisible(true);
                sellButton2.setVisible(true);
                insufficientBalance.setVisible(false);
                howMuchToSell.setMajorTickUnit(((int) (diamond / 4)));
            }
        });
        sellWood.setOnAction(event -> {
            whatToSellEnd = "wood";
            howMuchToSellPane.getChildren().add(quit);
            primaryStage.setScene(howMuchToSellScene);
            howMuchToSell.setMax(wood);
            if (wood == 0) {
                howMuchToSell.setVisible(false);
                l2.setVisible(false);
                sellButton2.setVisible(false);
                insufficientBalance.setVisible(true);
            } else {
                howMuchToSell.setVisible(true);
                l2.setVisible(true);
                sellButton2.setVisible(true);
                insufficientBalance.setVisible(false);
                howMuchToSell.setMajorTickUnit(((int) (wood / 4)));
            }
        });
        cancelSell.setOnAction(event -> {
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
        });
        cancelSell2.setOnAction(event -> {
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
        });
        sellButton2.setOnAction(event -> {
            amountToSell = (int) Math.round(howMuchToSell.getValue());
            portPane.getChildren().remove(yourInventory);
            if (whatToSellEnd.equals("cotton")) {
                if (cotton < amountToSell) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The amount you want to sell is more than what you have.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    cotton -= amountToSell;
                    money += (cottonPrice * amountToSell);
                }
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToSellEnd.equals("steel")) {
                if (steel < amountToSell) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The amount you want to sell is more than what you have.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    steel -= amountToSell;
                    money += (steelPrice * amountToSell);
                }
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToSellEnd.equals("gold")) {
                if (gold < amountToSell) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The amount you want to sell is more than what you have.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    gold -= amountToSell;
                    money += (goldPrice * amountToSell);
                }
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToSellEnd.equals("diamond")) {
                if (diamond < amountToSell) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The amount you want to sell is more than what you have.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    diamond -= amountToSell;
                    money += (diamondPrice * amountToSell);
                }
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            } else if (whatToSellEnd.equals("wood")) {
                if (wood < amountToSell) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The amount you want to sell is more than what you have.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    wood -= amountToSell;
                    money += (woodPrice * amountToSell);
                }
                Text inv2 = new Text("Your Inventory:\nMoney: $" + money + "     Food: " + food + "\n1. Cotton: " + cotton + "\n2. Steel: " + steel + "\n3. Gold: " + gold + "\n4. Diamond: " + diamond + "\n5. Wood: " + wood);
                yourInventory = inv2;
                yourInventory.setStyle("-fx-font-size: 15px;");
                yourInventory.setX(200);
                yourInventory.setY(80);
                portPane.getChildren().add(yourInventory);
            }
            portPane.getChildren().add(quit);
            primaryStage.setScene(portScene);
            if (money <= 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            } else if (money >= 60000) {
                if(doWinAlert) {
                    if(inGame) {
                        if (!(account.equals(""))) {
                            boolean addWin = Connection.addWin(finalConn13, account, gameVersion);
                            if (addWin) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "A win has been added to your account stats!", ButtonType.OK);
                                alert.setTitle("You win");
                                alert.setHeaderText("You win");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Something went wrong. A win could not be added to your account stats.", ButtonType.OK);
                                alert.setTitle("Something went wrong");
                                alert.setHeaderText("Something went wrong");
                                alert.showAndWait();
                            }
                        }
                    }
                    winPane.getChildren().add(quit);
                    primaryStage.setScene(winScene);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to continue playing? If you lose, it wont count as a loss since you already won.", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Continue?");
                    alert.setHeaderText("Continue?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        doWinAlert = false;
                        sailingPane.getChildren().add(quit);
                        primaryStage.setScene(sailingScene);
                    }
                }
                inGame = false;
            } else if (food < 0) {
                if(!(account.equals(""))) {
                    if(inGame) {
                        boolean addLoss = Connection.addLoss(finalConn13, account, gameVersion);
                    }
                }
                inGame = false;
                losePane.getChildren().add(quit);
                primaryStage.setScene(loseScene);
            }
        });
        quitGame.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game? It's not like you have anything better to do...", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Are you sure you want to quit?");
            alert.setHeaderText("Are you sure you want to quit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
