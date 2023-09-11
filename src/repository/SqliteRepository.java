package src.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SqliteRepository implements SQLRepository, AutoCloseable{
    private final Connection connection;

    public SqliteRepository(Connection connection) throws SQLException{
        this.connection = connection;
    }
    public Connection getConnection(){
        return this.connection;
    }
    public void createTable(String tableName, Map<String, String> tableSchema){}
    public void findAll(String tableName, String fields){}
    public void findOne(String tableName, Map<String, String> dataIdentifier, String fields){}
    public void insert(String tableName, Map<String, String> dataToBeInserted){}
    public void update(String tableName, Map<String, String> dataIdentifier, Map<String, String> updatedData){}
    public void delete(String tableName, Map<String, String> dataIdentifier){}
    public void closeConnection(){}
    public void close(){
        this.closeConnection();
    }
}