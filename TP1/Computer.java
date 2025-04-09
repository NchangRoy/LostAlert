package TP1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Computer extends Item {
    private String MAC;
    Computer(Item item,String MAC){
        super(item.getSerialNumber(),item.getStatus(),item.isReportedStolen(),item.getDescription(),item.getLastSeenLocation());
        this.MAC=MAC;

    }
    public String getMAC() {
        return MAC;
    }
    @Override
    public void save(Connection connection,int ownerId) {
         String query="insert into Items(serialNumber,status,reportedStolen,description,lastSeenLocation,Owner) values(?,?,?,?,?,?)";
        try {
            PreparedStatement pStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, super.getSerialNumber());
            pStatement.setString(2, super.getStatus().name());
            pStatement.setInt(3, super.isReportedStolen()?1:0);
            pStatement.setString(4,super.getDescription());
            pStatement.setString(5, super.getLastSeenLocation());
            pStatement.setInt(6, ownerId);
            int result=pStatement.executeUpdate();
         
            int Itemid=0;
            //getting primary key for inserted item
            if(result>0){
                ResultSet generatedKey=pStatement.getGeneratedKeys();
                if(generatedKey.next()){
                   Itemid =generatedKey.getInt(1);
                }
            }
            query ="insert into Computers(MAC,itemId) values (?,?)";
            pStatement=connection.prepareStatement(query);
            
            pStatement.setString(1,MAC);
            pStatement.setInt(2, Itemid);
            pStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
