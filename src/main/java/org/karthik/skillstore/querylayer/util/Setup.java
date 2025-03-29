package org.karthik.skillstore.querylayer.util;
import java.io.*;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.karthik.skillstore.utils.QueryLayerSetup;

public class Setup {

    public static void main(String[] args) {
    }

    public static void createTables() {
        InputStream is = Setup.class.getClassLoader().getResourceAsStream("schema.json");
        if (is == null) {
            throw new RuntimeException("schema.json not found");
        }
        JsonObject schema = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();

        for (String tableName : schema.keySet()) {
            QueryLayerSetup.generateCreateTableStatement(schema, tableName);

        }
    }

    public static void createEnums() {
        InputStream is = Setup.class.getClassLoader().getResourceAsStream("schema.json");
        if (is == null) {
            throw new RuntimeException("schema.json not found");
        }
        JsonObject schema = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
        String enumFolder = "src/main/java/org/karthik/skillstore/enums/";
        createEnumFolderIfNotExist(enumFolder);
        for (Map.Entry<String, JsonElement> entry : schema.entrySet()) {
            String tableName = entry.getKey();
            JsonObject tableObject = entry.getValue().getAsJsonObject();
            JsonArray columnsArray = tableObject.getAsJsonArray("columns");

            System.out.println("Table Name :" + tableName);
            System.out.println();
            System.out.println("Table object :" + tableObject);
            System.out.println();
            System.out.println("column array :" + columnsArray);

            System.out.println();
            System.out.println();

            String fileContent = generateEnumContent("org.karthik.skillstore.enums",tableName, columnsArray);
            System.out.println("\n------------------------------\n");
        }
    }

    public static void createEnumFolderIfNotExist(String enumFolder) {
        File file = new File(enumFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void createFiles(String fileName, String content){
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String generateEnumContent(String packageName ,String tableName, JsonArray columnsArray){
        StringBuilder sb = new StringBuilder();
        sb.append(packageName+"\n\n");
        sb.append("import java.util.Arrays;\n");
        sb.append("import java.util.Map;\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.HashMap;\n\n");

        sb.append("public enum ").append(toPascalCase(tableName)).append(" implements Columns {\n\n");

        for (JsonElement columnElement : columnsArray) {
            JsonObject column = columnElement.getAsJsonObject();
            String columnName = column.get("name").getAsString();
            String type = column.get("type").getAsString();
            sb.append("\t").append(columnName.toUpperCase()).append("(\"").append(columnName).append("\",").append(getDataType(type)).append(")")
                    .append(",\n");
        }
        sb.setLength(sb.length() - 2);
        sb.append(";\n");
        sb.append("\n\tprivate static final Map<String, ").append(toPascalCase(tableName)).append("> LOOKUP_MAP = new HashMap<>();\n\t");
        sb.append("private final String columnName;\n\t" + "private final Class<?> dataType;\n\n");

        sb.append("\tstatic {\n" + //
                "\t\tfor (").append(toPascalCase(tableName)).append(" col : ").append(toPascalCase(tableName)).append(".values()) {\n").append( //
                "\t\t\tLOOKUP_MAP.put(col.value(), col);\n").append( //
                "\t\t}\n").append( //
                "\t}\n\n");

        sb.append("\t@Override\n" + //
                "\tpublic String value() {\n" + //
                "\t\treturn columnName;\n" + //
                "\t}\n" + //
                "\n" + //
                "\t@Override\n" + //
                "\tpublic Class<?> getDataType() {\n" + //
                "\t\treturn dataType;\n" + //
                "\t}\n\n");

        sb.append("\t").append(toPascalCase(tableName)).append("(String colName, Class<?> dataType) {\n").append( //
                "\t\tthis.columnName = colName;\n").append( //
                "\t\tthis.dataType = dataType;\n").append( //
                "\t}\n\n");

        sb.append("\tpublic static ").append(toPascalCase(tableName)).append(" getCol(String columnName) {\n").append( //
                "\t\t").append(toPascalCase(tableName)).append(" colName = LOOKUP_MAP.get(columnName);\n").append( //
                "\t\tif (colName == null) {\n").append( //
                "\t\t\tthrow new IllegalArgumentException(\"Column name \" + colName + \" does not exist.\");\n").append( //
                "\t\t}\n").append( //
                "\t\treturn colName;\n").append( //
                "\t}\n").append( //
                "\n").append( //
                "\tpublic static List<Columns> getAllCols() {\n").append( //
                "\t\treturn Arrays.asList(").append(toPascalCase(tableName)).append(".values());\n").append( //
                "\t}\n\n");

        sb.append("}");

        return sb.toString();
    }

    public static void createEnumFile(String filePath,String tableName, JsonArray columnsArray){
        String content = generateEnumContent(filePath,tableName,columnsArray);
    }
    private static String getDataType(String type) {
        if (type.equals("serial") || type.equals("int") || type.equals("integer")) {
            return "Integer.class";
        } else if (type.equals("bigserial") || type.equals("bigint")) {
            return "Long.class";
        } else if (type.equals("smallserial") || type.equals("smallint")) {
            return "Short.class";
        } else if (type.startsWith("numeric") || type.startsWith("decimal")) {
            return "java.math.BigDecimal.class";
        } else if (type.startsWith("double precision") || type.startsWith("float8")) {
            return "Double.class";
        } else if (type.startsWith("real") || type.startsWith("float4")) {
            return "Float.class";
        } else if (type.equals("boolean") || type.equals("bool")) {
            return "Boolean.class";
        } else if (type.equals("char") || type.startsWith("character") || type.startsWith("varchar")
                || type.startsWith("text")) {
            return "String.class";
        } else if (type.startsWith("date")) {
            return "java.sql.Date.class";
        } else if (type.startsWith("timestamp")) {
            return "java.sql.Timestamp.class";
        } else if (type.startsWith("time")) {
            return "java.sql.Time.class";
        } else if (type.startsWith("bytea")) {
            return "byte[].class";
        } else if (type.equals("uuid")) {
            return "java.util.UUID.class";
        } else {
            return "Object.class"; // Default case
        }
    }

    private static String toPascalCase(String text) {
        String[] words = text.split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

}
