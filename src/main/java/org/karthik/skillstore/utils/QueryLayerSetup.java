package org.karthik.skillstore.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import org.karthik.skillstore.dbutil.Database;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryLayerSetup {

    public void createTables(String filename) {
        InputStream is = QueryLayerSetup.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            throw new RuntimeException("schema file "+filename+" not found");
        }
        JsonObject schema = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
        StringBuilder createTablesStatement = new StringBuilder();
        for (String tableName : schema.keySet()) {

            StringBuilder createTable=generateCreateTableStatement(schema, tableName);

            createTablesStatement.append(createTable);
        }
        excecuteQuery(createTablesStatement.toString());

    }

    public static StringBuilder generateCreateTableStatement(JsonObject schema, String tableName) {
        StringBuilder createTable = new StringBuilder(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));

        JsonArray cols = schema.getAsJsonObject(tableName).getAsJsonArray("columns");

        for (int i = 0; i < cols.size(); i++) {
            JsonObject column = cols.get(i).getAsJsonObject();

            String name = column.get("name").getAsString();
            String type = column.get("type").getAsString();
            String isNull = column.get("null").getAsString().equalsIgnoreCase("no") ? "NOT NULL" : "NULL";
            String key = column.has("key") && column.get("key").getAsString().equals("PRI") ? "PRIMARY KEY" : "";
            String extra = column.has("extra") ? column.get("extra").getAsString() : "";
            String defaultVal = column.has("default") && !column.get("default").isJsonNull()
                    ? "DEFAULT " + column.get("default")
                    : "";

            createTable.append(name).append(" ").append(type).append(" ").append(isNull);

            if (!key.isEmpty()) {
                createTable.append(" ").append(key);
            }
            if (!defaultVal.isEmpty()) {
                createTable.append(" ").append(defaultVal);
            }
            if (!extra.isEmpty()) {
                createTable.append(" ").append(extra);
            }
            if (i < cols.size() - 1) {
                createTable.append(", ");
            }
        }
        return createTable.append(");");
    }

    public void excecuteQuery(String query) throws RuntimeException {
        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        //A json file for all the tables -> creattion of tables in database
        //->creation of tables enum, and seperate enums for each tables for their columns

        QueryLayerSetup queryLayerSetup = new QueryLayerSetup();

        queryLayerSetup.createTables("schema.json");
        System.out.println(System.getProperty("user.dir"));
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir);

        // List files and directories
        String[] files = directory.list();

        if (files != null) {
            for (String file : files) {
                System.out.println(file);
            }
        } else {
            System.out.println("Failed to list files.");
        }
    }

}
