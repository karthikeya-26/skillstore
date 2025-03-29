package org.karthik.skillstore.querylayer;

import java.util.ArrayList;
import java.util.List;

import org.karthik.skillstore.enums.Table;
import org.karthik.skillstore.querybuilder.BuildException;
import org.karthik.skillstore.querybuilder.mysql.MysqlDeleteQueryBuilder;

public class Delete extends Query {

    private Table tableName;
    private List<Condition> conditions;
    private String query;

    public Delete() {
        this.conditions = new ArrayList<Condition>();
    }

    public Delete table(Table tableName) {
        this.tableName = tableName;
        return this;
    }

    public Delete condition(Columns column, Operators operator, String value) {
        this.conditions.add(new Condition(column,operator,value));
        return this;
    }

    public String build() throws QueryException{
        if (query == null) {
            if (prop.getProperty("database_name").equals("mysql")) {

                try {
                    this.query = new MysqlDeleteQueryBuilder(this).build();
                } catch (BuildException e) {
                    throw new QueryException(e.getMessage());
                }
                return query;
            }
            else if (prop.getProperty("databse_name").equals("postgres")) {
                // pg select query builder

            }
        }
        return query;


    }
    public int executeUpdate() throws QueryException {

        return super.executeUpdate(this);
    }

    public Table getTableName() {
        return this.tableName;
    }

    public List<Condition> getConditions(){
        return this.conditions;
    }

}
