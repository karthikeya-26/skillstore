package org.karthik.skillstore.querybuilder.mysql;

import org.karthik.skillstore.querybuilder.BuildException;

import java.util.StringJoiner;

import org.karthik.skillstore.querybuilder.CheckDataType;
import org.karthik.skillstore.querylayer.Condition;
import  org.karthik.skillstore.querylayer.Update;

public class MysqlUpdateQueryBuilder {
    Update updateObj;

    public MysqlUpdateQueryBuilder(Update update) {
        this.updateObj = update;
    }

    public String build() throws BuildException {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(updateObj.getTableName().value() );
        query.append(" SET ");

        if (this.updateObj.getColumns().isEmpty() && this.updateObj.getValues().isEmpty()) {
            throw new BuildException("insufficient column and values data");
        } else if (this.updateObj.getColumns().size() != this.updateObj.getValues().size()) {
            throw new BuildException("unequal columns and values");
        }

        else {
            StringJoiner colAndValueJoiner = new StringJoiner(", ");
            for (int i = 0; i < this.updateObj.getColumns().size(); i++) {
                colAndValueJoiner.add(this.updateObj.getColumns().get(i).value().toString() + " = " +
                        (CheckDataType.isFloat(this.updateObj.getValues().get(i)) ||
                                CheckDataType.isInt(this.updateObj.getValues().get(i)) ||
                                CheckDataType.isLong(this.updateObj.getValues().get(i))
                                ? this.updateObj.getValues().get(i).toString()
                                : "'" + this.updateObj.getValues().get(i) + "'"));
            }
            query.append(colAndValueJoiner.toString());

        }

        if (!this.updateObj.getConditions().isEmpty()) {
            StringJoiner conditionJoiner = new StringJoiner(" AND ");
            for (Condition c : this.updateObj.getConditions()) {
                conditionJoiner.add(String.format("%s %s %s", c.column.value(), c.operator.value(),
                        ((CheckDataType.isFloat(c.value) || CheckDataType.isInt(c.value)
                                || CheckDataType.isLong(c.value)) ? c.value : "'" + c.value + "'")));
            }
            query.append(" WHERE ");
            query.append(conditionJoiner.toString());

        }

        return query.append(";").toString();
    }
}
