package src.repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface SQLRepository{
    public Connection getConnection();
    public void createTable(String tableName, Map<String, String> tableSchema) throws SQLException;
    public ResultSet findAll(String tableName, String fields);
    public ResultSet findOne(String tableName, Map<String, String> dataIdentifier, String fields);
    public void insert(String tableName, Map<String, String> dataToBeInserted);
    public void update(String tableName, Map<String, String> dataIdentifier, Map<String, String> updatedData);
    public void delete(String tableName, Map<String, String> dataIdentifier);
    public void closeConnection();
}