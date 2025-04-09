package TP1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class databaseControl {
   static public Map<Phone,Integer> getPhones(Connection connection){
        Map<Phone,Integer> phonesMap=new HashMap<>();

        //we query the database for the Items
        String query="select * from Items natural join Phones";
      try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        ResultSet result=pStatement.executeQuery();
       
     
        while(result.next()){
          
            boolean reportedStolen=(result.getInt("reportedStolen")==0)?false:true;
            Item item=new Item(result.getString("serialNumber"),
                            Item.getStatisEnumFromString(result.getString("status")), 
                            reportedStolen,
                            result.getString("description")
                            ,result.getString("lastSeenLocation"));
            Phone phone=new Phone(item, result.getString("IMEI"), result.getString("MAC"));
            phonesMap.put(phone,result.getInt("phoneId"));
         
            
        }
        
      } catch (Exception e) {
       e.printStackTrace();
      }
        return phonesMap;
    }
    static public Map<Computer,Integer> getComputers(Connection connection){
        Map<Computer,Integer> phonesMap=new HashMap<>();

        //we query the database for the Items
        String query="select * from Items natural join Computers";
      try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        ResultSet result=pStatement.executeQuery();
      
     
        while(result.next()){
          
            boolean reportedStolen=(result.getInt("reportedStolen")==0)?false:true;
            Item item=new Item(result.getString("serialNumber"),
                            Item.getStatisEnumFromString(result.getString("status")), 
                            reportedStolen,
                            result.getString("description")
                            ,result.getString("lastSeenLocation"));
            Computer computer=new Computer(item,  result.getString("MAC"));
            phonesMap.put(computer,result.getInt("computerId"));
         
            
        }
        
      } catch (Exception e) {
       e.printStackTrace();
      }
        return phonesMap;
    }

    static public Map<Item,Integer> getOthers(Connection connection){
        Map<Item,Integer> itemMap=new HashMap<>();

        //we query the database for the Items
        String query="select * from Items where id not in(select id from Phones) and id not in (select id from Computers);";
      try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        ResultSet result=pStatement.executeQuery();
      
     
        while(result.next()){
          
            boolean reportedStolen=(result.getInt("reportedStolen")==0)?false:true;
            Item item=new Item(result.getString("serialNumber"),
                            Item.getStatisEnumFromString(result.getString("status")), 
                            reportedStolen,
                            result.getString("description")
                            ,result.getString("lastSeenLocation"));
            itemMap.put(item,result.getInt("id"));
         
            
        }
        
      } catch (Exception e) {
       e.printStackTrace();
      }
        return itemMap;
    }
    static  public void setPhoneStolen(int phoneId,String status,Connection connection){
        String query="update Items set status=? from Phones  join Items on Phones.id=Items.id where phoneId=?";
       try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        pStatement.setString(1, status);
        pStatement.setInt(2, phoneId);
        pStatement.executeUpdate();
       } catch (Exception e) {
        e.printStackTrace();
       }
    }

    static public void setComputerStolen(int computerId,String status,Connection connection){
        String query="update Items set status=? from Computers natural join Items where computerId=?";
       try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        pStatement.setString(1, status);
        pStatement.setInt(2, computerId);
        pStatement.executeUpdate();
       } catch (Exception e) {
        e.printStackTrace();
       }
    }

   static  public void setOtherStolen(int itemId,String status,Connection connection){
        String query="update Items set status=? where  id=?";
       try {
        PreparedStatement pStatement=connection.prepareStatement(query);
        pStatement.setString(1, status);
        pStatement.setInt(2, itemId);
        pStatement.executeUpdate();
       } catch (Exception e) {
        e.printStackTrace();
       }
    }
}
