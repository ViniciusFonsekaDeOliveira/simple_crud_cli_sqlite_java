package src.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqliteRepository implements SQLRepository, AutoCloseable{
    private final Connection connection;

    public SqliteRepository(Connection connection) throws SQLException{
        this.connection = connection;
    }
    public Connection getConnection(){
        return this.connection;
    }
    public void createTable(String tableName, Map<String, String> tableSchema) {
        List<String> formatedTableSchema = tableSchema.keySet().stream().map((String column) -> column + " " + tableSchema.get(column)).toList();
        String schemaColumns = String.join(",", formatedTableSchema );
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " ("  + schemaColumns +")";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
            System.out.println("Tabela criada com sucesso!");
        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao criar a tabela: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
    public void findAll(String tableName){
        findAll(tableName, "*");

    }
    public ResultSet findAll(String tableName, String fields){
        String query = "SELECT "+ fields + " FROM " + tableName;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            return preparedStatement.executeQuery();
        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao buscar todos os registros: " + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    public String getWhereClauseConditions(Map<String, String> dataIdentifier){
        Set<String> conditions = dataIdentifier.keySet();
        int dataSize = dataIdentifier.keySet().size();

        if (dataSize == 1){
            return (String) conditions.toArray()[0] + "=?"; //Retorne a única string dentro de set formatada apropriadamente.
        }
        else if( dataSize >= 2){ //Formate apropriadamente cada coluna dentro de set, as una em uma única string através do AND e a retorne.
            return String.join(" AND ",(conditions.stream().map((String column) -> (column + "=?")).toList()));
        }
        else
           throw new Error("Data Identifier não pode ser vazio!");

    }

    public ResultSet findOne(String tableName, Map<String, String> dataIdentifier, String fields){
        String conditions = getWhereClauseConditions(dataIdentifier);
        Collection<String> clauseValues = dataIdentifier.values();
        String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + conditions;
        try(PreparedStatement prepraredStatement = connection.prepareStatement(query)){
            int placeholderIndex = 1;
            for (String clauseValue: clauseValues)
                prepraredStatement.setString(placeholderIndex++, clauseValue);
            return prepraredStatement.executeQuery();
        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao buscar um registro: " + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }
    public void insert(String tableName, Map<String, String> dataToBeInserted){
        String columns = String.join(",",(dataToBeInserted.keySet()));
        String placeholders=  String.join(",", dataToBeInserted.keySet().stream().map((String column) -> "?").toList());
        Collection<String> values = dataToBeInserted.values();
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int placeholderIndex = 1;
            for (String value : values)
                preparedStatement.setString(placeholderIndex++, value);           
            preparedStatement.executeUpdate();
        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao inserir dados: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
    public void update(String tableName, Map<String, String> dataIdentifier, Map<String, String> updatedData){
        String columnsToUpdate = String.join(",", (updatedData.keySet().stream().map((String column) -> column + "=?").toList()));
        String conditions = getWhereClauseConditions(dataIdentifier);
        Collection<String> values = updatedData.values();
        values.addAll(dataIdentifier.values());
        String query = "UPDATE " + tableName + "SET " + columnsToUpdate + " WHERE " + conditions;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int placeholderIndex = 1;
            for (String value : values)
                preparedStatement.setString(placeholderIndex, value);

            preparedStatement.executeUpdate();

        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao atualizar o registro: " + exception.getMessage());
            exception.printStackTrace();
        }

    }
    public void delete(String tableName, Map<String, String> dataIdentifier){
        String conditions = getWhereClauseConditions(dataIdentifier);
        String query = "DELETE FROM " + tableName + " WHERE " + conditions;
        Collection<String> clauseValues = dataIdentifier.values();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int placeholderIndex = 1;
            for (String clauseValue : clauseValues)
                preparedStatement.setString(placeholderIndex++, clauseValue);
            
            preparedStatement.executeUpdate();

        }catch(SQLException exception){
            System.out.println("Ocorreu um erro ao deletar registro: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void closeConnection(){
        if (this.getConnection() != null){
            try{
                this.connection.close();
            }catch(SQLException exception){
                System.out.println("Ocorreu um erro ao encerrar a conexão com o banco: " + exception.getMessage());
                exception.printStackTrace();
            }
        }
    }
    public void close(){
        this.closeConnection();
    }
}