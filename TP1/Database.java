package TP1;

import java.sql.Connection;

public interface Database {

   abstract public void save(Connection connection,int OwnerId);
} 
