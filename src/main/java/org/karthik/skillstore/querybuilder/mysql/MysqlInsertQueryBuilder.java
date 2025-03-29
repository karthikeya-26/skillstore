package org.karthik.skillstore.querybuilder.mysql;
import java.util.StringJoiner;

import org.karthik.skillstore.querybuilder.CheckDataType;
import org.karthik.skillstore.querylayer.Insert;
import org.karthik.skillstore.querybuilder.BuildException;
public class MysqlInsertQueryBuilder {
    private final Insert insertObj;

    public MysqlInsertQueryBuilder(Insert insert) {
        this.insertObj = insert;
    }

    public String build() throws BuildException {
        if (insertObj.getColumns().isEmpty() && insertObj.getValues().isEmpty()) {
            throw new BuildException("Insufficient data of columns and values");
        }

        StringBuilder query = new StringBuilder("INSERT INTO ")
                .append(insertObj.getTableName().value()).append(" ");

        if (insertObj.getColumns().isEmpty()) {
            StringJoiner valueJoiner = new StringJoiner(",", "(", ")");
            insertObj.getValues().forEach(value ->{
                valueJoiner.add(value.toString());
            });
            query.append("VALUES ").append(valueJoiner);
        } else {
            if (insertObj.getColumns().size() != insertObj.getValues().size()) {
                throw new BuildException("Column and values size didn't match, please check your query");
            }

            StringJoiner columnJoiner = new StringJoiner(",", "(", ")");
            insertObj.getColumns().forEach(col -> columnJoiner.add(col.value()));

            StringJoiner valueJoiner = new StringJoiner(",", "(", ")");
            insertObj.getValues().forEach(val -> valueJoiner.add(
                    (CheckDataType.isFloat(val) || CheckDataType.isInt(val) || CheckDataType.isLong(val)) ? val.toString() : "'" + val.toString() + "'"));

            query.append(columnJoiner).append(" VALUES ").append(valueJoiner);
        }
        return query.append(";").toString();
    }
}
