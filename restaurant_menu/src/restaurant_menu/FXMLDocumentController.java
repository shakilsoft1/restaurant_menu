/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package restaurant_menu;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    private Alert alert;

    private final String[] questionList = {"What is yout favourite Color?", "What is your faourite food?", "What is favourite date?"};

    public void regLquestionist() {
        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
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
            alert.setContentText("Please fill all blank firlds");
            alert.showAndWait();
        } else {
            String regData="INSERT INTO employee (username,password,question,answer,date)"
                    + "VALUES(?,?,?,?,?)";
            Connection connect = database.connectDB();
            
            try{
                PreparedStatement prepare = connect.prepareStatement(regData);
                prepare.setString(1, su_userName.getText());
                prepare.setString(2, su_password.getText());
                prepare.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                prepare.setString(4, su_answer.getText());
                
                Date date=new Date();
                java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                prepare.setString(5, String.valueOf(sqlDate));
                prepare.executeUpdate();
                
                prepare.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully registered Account");
                alert.showAndWait();
                
                su_userName.setText("");
                su_password.setText("");
                su_question.getSelectionModel().clearSelection();
                su_answer.setText("");
                
                
            }catch(Exception e){e.printStackTrace();}
        }
    }

}
