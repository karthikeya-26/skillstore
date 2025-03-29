package org.karthik.skillstore.querylayer;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.enums.Table;
import org.karthik.skillstore.querybuilder.mysql.MysqlUpdateQueryBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class Update extends Query {
    private Table tableName;
    private List<Columns> columns;
    private List<String> values;
    private List<Condition> conditions;
    private String query;

    public Update() {
        this.columns = new ArrayList<>();
        this.values = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.query = null;
    }

    public Update table(Table tableName) {
        this.tableName = tableName;
        return this;
    }

    public Update columns(Columns... columns) {
        for (Columns col : columns) {
            this.columns.add(col);
        }
        return this;
    }

    public Update values(String... values) {
        for (String value : values) {
            this.values.add(value);
        }
        return this;
    }

    public Update condition(Columns column, Operators operator, String value) {
        conditions.add(new Condition(column, operator, value));
        return this;
    }

    public String build() {
        Properties prop = Database.properties;
        if (prop.getProperty("database_name").equals("mysql")) {

            try {
                this.query = new MysqlUpdateQueryBuilder(this).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.query;
        }
        if (prop.getProperty("database_name").equals("postgres")) {
            // pg select query builder
        }
        return null;
    }

    public int executeUpdate() throws QueryException {

        return super.executeUpdate(this);
    }

    public Table getTableName() {
        return tableName;
    }

    public List<Columns> getColumns() {
        return columns;
    }

    public List<String> getValues() {
        return values;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
