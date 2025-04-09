package TP1;

import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class signUp_Login {

    Connection connection;
    @FXML
    void logIn(ActionEvent event) {
        try {
            //hide main window
            Button object=(Button)event.getSource();
           Stage parenStage= ((Stage)object.getScene().getWindow());
           parenStage.close();
           //load new stage

            FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root=loader.load();

            //get Controller and pass Connection
            Login loginController=(Login)loader.getController();
            loginController.getConnection(connection);

            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            //hide main window
            Button object=(Button)event.getSource();
           Stage parenStage= ((Stage)object.getScene().getWindow());
           parenStage.close();
           //load new stage

            FXMLLoader loader=new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root=loader.load();

            //get Controller and pass controller
            SignUp signupController=(SignUp)loader.getController();
            signupController.getConnection(connection);


            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("SignUp");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getConnection(Connection connection){
        this.connection=connection;
    }

}
