/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package restaurant_menu;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    // Handle forgot password click
    private void handleForgotPassword() {
        System.out.println("Forgot Password clicked");
    }

}
