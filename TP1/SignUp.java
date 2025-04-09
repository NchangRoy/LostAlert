package TP1;

import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUp {

    @FXML
    private TextField name_label;

    @FXML
    private PasswordField pass_label;

    @FXML
    private TextField tel_label;

    Connection connection;

    @FXML
    void Save_Owner(ActionEvent event) {
        Owner owner=new Owner(name_label.getText(), tel_label.getText(), pass_label.getText());
        OwnerValidation validate=new OwnerValidation(owner);
        
        validate.save(connection);
        openMainpage(validate);
    }

    public void openMainpage(OwnerValidation owner){
        //close parent
        Stage parenStage=(Stage)name_label.getScene().getWindow();
        parenStage.close();
        //open mainpage
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Mainpage.fxml"));
        try {
            Parent root=loader.load();

            mainpageController controller=(mainpageController)loader.getController();
            controller.initializeParameters(connection,owner);

            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("MainApplication");
            stage.show();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public void getConnection(Connection connection){
        this.connection=connection;
    }
    

}
