package TP1;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OwnerValidation extends Owner {
   
    public OwnerValidation(Owner owner) {
        super(owner.getName(), owner.getTelephone(),owner.getPassword());
        
    }


    public void save(Connection connection) {
        try {
            String query="insert into Owner(name,telephone,password) values (?,?,?)";
            PreparedStatement pStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, super.getName());
            pStatement.setString(2, super.getTelephone());
            pStatement.setString(3, hashPassword(super.getPassword()));
            int result=pStatement.executeUpdate();
            if(result>0){
                ResultSet generatedKey=pStatement.getGeneratedKeys();
                if(generatedKey.next()){
                    super.setDBid(generatedKey.getInt(1));
                }
            }
           

        } catch (Exception e) {
          e.printStackTrace();
        }
        
    }
  
   
    public boolean validateCredential(Connection connection) {
        try {
            String query="select id,password from Owner where name=?";

        PreparedStatement pStatement=connection.prepareStatement(query);
        pStatement.setString(1, super.getName());
        ResultSet resultset=pStatement.executeQuery();
        
        while(resultset.next()){
            
            String hashedpassword=resultset.getString("password");
            System.out.println(hashedpassword);
            if(validatePassword(super.getPassword(), hashedpassword)){
                super.setDBid(resultset.getInt("id"));
                return true;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String hashPassword(String password) {
        StringBuilder hexString=new StringBuilder();
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            byte[] hashBytes=digest.digest(password.getBytes());
            
            for(byte b: hashBytes){
                hexString.append(String.format("%02x",b));
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();

    }
    
    public boolean validatePassword(String password, String hashString) {
        String hashedpass=hashPassword(password);
        return hashedpass.equals(hashString);
    }

}
