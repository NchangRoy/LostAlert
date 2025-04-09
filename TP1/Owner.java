package TP1;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;

public class Owner  {
    private int DBid;
    private String name;
    private String telephone;
    private String password;
    public String getName() {
        return name;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getPassword() {
        return password;
    }
    public int getDBid() {
        return DBid;
    }
    public void setDBid(int DBid){
        this.DBid=DBid;
    }
    public Owner(String name, String telephone, String password) {
        this.name = name;
        this.telephone = telephone;
        this.password = password;
    }
    public Owner(String name, String password) {
        this.name = name;
        this.telephone = telephone;
        this.password = password;
    }

   
    
}
