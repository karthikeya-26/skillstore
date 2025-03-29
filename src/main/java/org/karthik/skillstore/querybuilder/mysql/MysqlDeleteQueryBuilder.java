package org.karthik.skillstore.querybuilder.mysql;

import org.karthik.skillstore.querybuilder.BuildException;

import org.karthik.skillstore.querybuilder.CheckDataType;
import org.karthik.skillstore.querylayer.Delete;
import org.karthik.skillstore.querylayer.Condition;

import java.util.StringJoiner;

public class MysqlDeleteQueryBuilder {
    private final Delete deleteObj;

    public MysqlDeleteQueryBuilder(Delete delete) {
        this.deleteObj = delete;
    }

    public String build() throws BuildException {
        if (deleteObj.getConditions().isEmpty()) {
            throw new BuildException("Insufficient data to build the delete statement");
        }

        StringBuilder query = new StringBuilder("DELETE FROM ")
                .append(deleteObj.getTableName().value())
                .append(" WHERE ");

        StringJoiner conditionJoiner = new StringJoiner(" AND ");
        for (Condition condition : deleteObj.getConditions()) {
            String formattedValue = (CheckDataType.isFloat(condition.value) ||
                    CheckDataType.isInt(condition.value) ||
                    CheckDataType.isLong(condition.value))
                    ? condition.value.toString()
                    : "'" + condition.value.toString() + "'";

            conditionJoiner.add(String.format("%s %s %s",
                    condition.column.value(),
                    condition.operator.value(),
                    formattedValue));
        }

        query.append(conditionJoiner);
        return query.append(";").toString();
    }
}
