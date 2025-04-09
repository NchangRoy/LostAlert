package TP1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    public static Connection connection;
    @Override
    public void start(Stage primarStage) throws Exception {
          
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("SignUp_and_login.fxml"));
            Parent root=loader.load();

            //get controller
            signUp_Login Controller=(signUp_Login)loader.getController();
            //pass connection to controller
            Controller.getConnection(connection);
            Scene scene=new Scene(root);
            primarStage.setTitle("Create Account/Login");
            primarStage.setScene(scene);
            primarStage.show();
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {

       

         String url = "jdbc:mysql://localhost:3306/TP1";  
            String username = "admin";  
            String password = "password";  
            
            try {
               
                Class.forName("com.mysql.cj.jdbc.Driver");
    
               
                connection = DriverManager.getConnection(url, username, password);


            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect");
        }

      launch(args);

      
        
    }
}
