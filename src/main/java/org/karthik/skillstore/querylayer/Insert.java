package org.karthik.skillstore.querylayer;

import org.karthik.skillstore.enums.Table;

import org.karthik.skillstore.querybuilder.BuildException;
import org.karthik.skillstore.querybuilder.mysql.MysqlInsertQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Insert extends Query {
    private Table tableName;
    private final List<Columns> columns;
    private final List<Object> values;
    private final List<Condition> conditions;
    private String query = null;

    public Insert() {
        this.columns = new ArrayList<>();
        this.values = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    public Insert table(Table tableName) {
        this.tableName = tableName;
        return this;
    }

    public Insert columns(Columns... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public Insert values(Object... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    public Insert condition(Columns column, Operators operator, String value) {
        conditions.add(new Condition(column, operator, value));
        return this;
    }

    public String build() throws QueryException {

        if (prop.getProperty("database_name").equals("mysql")) {
            try {
                this.query = new MysqlInsertQueryBuilder(this).build();
            } catch (BuildException e) {
                throw new QueryException(e.getMessage());
            }
        }
        if (prop.getProperty("database_name").equals("postgres")) {
            System.out.println("currently no support for postgres");
            return null;
        }

        return query;
    }

    public int executeUpdate() throws QueryException {

        return super.executeUpdate(this);
    }

    public int executeUpdate(boolean returnGeneratedKey) throws QueryException {
        return super.executeUpdate(this, returnGeneratedKey);
    }

    public Table getTableName() {
        return tableName;
    }

    public List<Columns> getColumns() {
        return columns;
    }

    public List<Object> getValues() {
        return values;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
