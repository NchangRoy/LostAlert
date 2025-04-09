package TP1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item implements Database {
    public enum status{stolen,recoverd;};
    
    private String serialNumber;
    private status Status;
    private boolean reportedStolen;
    private String description;
    private String lastSeenLocation;
    
    Item(String serialNumber,status Status,boolean reportedStolen,String description, String Lastseen){
        this.serialNumber=serialNumber;
        this.Status=Status;
        this.reportedStolen=reportedStolen;
        this.description=description;
        this.lastSeenLocation=Lastseen;
    }
    public status getStatus() {
        return Status;
    }
    public boolean isReportedStolen() {
        return reportedStolen;
    }
    public String getDescription() {
        return description;
    }
    public String getLastSeenLocation() {
        return lastSeenLocation;
    }
    public String getSerialNumber(){
        return this.serialNumber;
    }

    @Override
    public void save(Connection connection,int ownerId) {
         String query="insert into Items(serialNumber,status,reportedStolen,description,lastSeenLocation,Owner) values(?,?,?,?,?,?)";
        try {
            PreparedStatement pStatement=connection.prepareStatement(query);
            pStatement.setString(1, getSerialNumber());
            pStatement.setString(2, getStatus().name());
            pStatement.setInt(3, isReportedStolen()?1:0);
            pStatement.setString(4,getDescription());
            pStatement.setString(5, getLastSeenLocation());
            pStatement.setInt(6, ownerId);
            pStatement.executeUpdate();
         
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    static public status getStatisEnumFromString(String statusname) {
        for (status Status : status.values()) {
            if (Status.name().equals(statusname)) {
                return Status;
            }
        }
        return null;  // Return null if no matching enum is found
    }
}
