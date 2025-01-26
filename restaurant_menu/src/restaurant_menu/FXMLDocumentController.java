/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package restaurant_menu;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static javax.management.remote.JMXConnectorFactory.connect;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField su_userName;
    @FXML
    private PasswordField su_password;
    @FXML
    private Button su_signupBtn;
    @FXML
    private ComboBox<String> su_question;  // Specified the type here
    @FXML
    private PasswordField su_answer;
    @FXML
    private TextField si_username;
    @FXML
    private PasswordField si_password;
    @FXML
    private Button si_loginBtn;
    @FXML
    private Hyperlink si_forgot;
    
    private Alert alert;
    @FXML
    private AnchorPane question_form;
    @FXML
    private Button fp_proceedBtn;
    @FXML
    private ComboBox<?> fp_question;
    @FXML
    private TextField fp_answer;
    @FXML
    private Button fp_back;
    @FXML
    private AnchorPane np_newPassForm;
    @FXML
    private Button np_changePassBtn;
    @FXML
    private PasswordField np_newPassword;
    @FXML
    private PasswordField np_confirmPassword;
    @FXML
    private Button np_back;
    @FXML
    private AnchorPane si_loginForm;
    @FXML
    private TextField fp_username;
    
    @FXML
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/password");
            alert.showAndWait();
            
        } else {
            String selectData = "SELECT username, password FROM employee WHERE username=? and password=?";
            Connection connect = database.connectDB();
            
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());
                
                ResultSet result = prepare.executeQuery();
                
                if (result.next()) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();
                    
                    Parent root=FXMLLoader.load(getClass().getResource("mainForm.fxml"));
                    
                    Stage stage=new Stage();
                    Scene scene =new Scene(root);
                    
                    stage.setTitle("Cafe Shopp Management System");
                    stage.setMinWidth(650);
                    
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    si_loginBtn.getScene().getWindow().hide();
                    
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/password");
                    alert.showAndWait();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
        
    }
    
    private final String[] questionList = {"What is yout favourite Color?", "What is your faourite food?", "What is favourite date?"};
    private PreparedStatement prepare;
    
    public void regLquestionist() {
        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }
    
    @FXML
    public void switchForgotPass() {
        question_form.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }
    
    @FXML
    public void proceedBtn() {
        
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {
            String selectData = "SELECT username, question, answer FROM employee WHERE username=? AND question=? AND answer=?";
            Connection connect = database.connectDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());
                
                ResultSet result = prepare.executeQuery();
                
                if (result.next()) {
                    np_newPassForm.setVisible(true);
                    question_form.setVisible(false);
                    
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    @FXML
    public void changePassBtn() {
        
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {
            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String getDate = "SELECT date FROM employee WHERE username= '"
                        + fp_username.getText() + "'";
                
                Connection connect = database.connectDB();
                try {
                    prepare = connect.prepareStatement(getDate);
                    ResultSet result = prepare.executeQuery();
                    String date = "";
                    if (result.next()) {
                        date = result.getString(date);
                    }
                    
                    String updatePass = "UPDATE employee SET password='"
                            + np_newPassword.getText() + "', question='"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer='"
                            + fp_answer.getText() + "', date='"
                            + date + "' WHERE username='"
                            + fp_username.getText() + "'";
                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Changed Password");
                    alert.showAndWait();
                    
                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);
                    
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            } else {
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not Match");
                alert.showAndWait();
            }
            
        }
        
    }
    
    public void forgotPassQuestionList() {
        List<String> listQ = new ArrayList();
        
        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }
    
    @FXML
    public void backToLoginForm(){
            si_loginForm.setVisible(true);
            question_form.setVisible(false);
    }
    @FXML
    public void backToquestionForm(){
            question_form.setVisible(true);
            np_newPassForm.setVisible(false);
    }
    
    ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        regLquestionist();
    }

    // Handle forgot password click
    private void handleForgotPassword() {
        System.out.println("Forgot Password clicked");
    }
    
    @FXML
    private void regBtn(ActionEvent event) {
        if (su_userName.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date) VALUES (?, ?, ?, ?, ?)";
            
            Connection connect = database.connectDB();
            
            if (connect == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Database Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to connect to the database.");
                alert.showAndWait();
                return;
            }
            
            try {
                String checkUsername = "SELECT username FROM employee WHERE username = ?";
                prepare = connect.prepareStatement(checkUsername);
                prepare.setString(1, su_userName.getText());
                ResultSet result = prepare.executeQuery();
                
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_userName.getText() + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password must be at least 8 characters long");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_userName.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
                    prepare.setDate(5, new java.sql.Date(new Date().getTime()));
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered account");
                    alert.showAndWait();
                    
                    su_userName.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private int generateUniqueId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Alert setTitle(String error_Message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private Alert setContenText(String incorrect_Usernamepassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
