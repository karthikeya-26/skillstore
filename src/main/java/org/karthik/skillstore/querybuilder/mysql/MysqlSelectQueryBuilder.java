package org.karthik.skillstore.querybuilder.mysql;

import java.util.StringJoiner;

import org.karthik.skillstore.querybuilder.CheckDataType;
import org.karthik.skillstore.querylayer.TableName;
import org.karthik.skillstore.querylayer.Select;
import org.karthik.skillstore.querybuilder.BuildException;

public class MysqlSelectQueryBuilder {
    private final Select selectObj;

    public MysqlSelectQueryBuilder(Select select) {
        this.selectObj = select;
    }

    public String build() throws BuildException {
        StringBuilder query = new StringBuilder("SELECT ");
        StringJoiner columnsJoiner = new StringJoiner(", ");

        if (selectObj.getColumns().isEmpty() && selectObj.getAggregratecolumns().isEmpty()
                && selectObj.getGroupConcat().isEmpty()) {
            query.append("*");
        } else {
            selectObj.getAggregratecolumns()
                    .forEach(col -> columnsJoiner.add(String.format("%s(%s)", col.Aggregate.value(), col.column_name)));

            selectObj.getColumns().forEach(col -> {
                String tableName = col.getClass().isAnnotationPresent(TableName.class)
                        ? col.getClass().getAnnotation(TableName.class).value()
                        : col.getClass().getSimpleName();
                columnsJoiner.add(tableName + "." + col.value());
            });
            query.append(columnsJoiner);
        }

        query.append(" FROM ").append(selectObj.getTable().value());

        if (!selectObj.getJoins().isEmpty()) {
            StringJoiner joinJoiner = new StringJoiner(" ");
            selectObj.getJoins()
                    .forEach(join -> joinJoiner.add(String.format("%s %s ON %s.%s %s %s.%s", join.joinType.value(),
                            join.table1.value(), join.table1.value(), join.table1col.value(), join.operator.value(),
                            join.table2.value(), join.table2col.value())));
            query.append(" ").append(joinJoiner);
        }

        if (!selectObj.getConditions().isEmpty()) {
            StringJoiner conditionsJoiner = new StringJoiner(" AND ");
            selectObj.getConditions().forEach(condition -> {
                String tableName = condition.column.getClass().isAnnotationPresent(TableName.class)
                        ? condition.column.getClass().getAnnotation(TableName.class).value()
                        : condition.column.getClass().getSimpleName();
                conditionsJoiner.add(
                        String.format("%s.%s %s %s", tableName, condition.column.value(), condition.operator.value(),
                                (CheckDataType.isFloat(condition.value) || CheckDataType.isInt(condition.value)
                                        || CheckDataType.isLong(condition.value)) ? condition.value
                                        : "'" + condition.value + "'"));
            });
            query.append(" WHERE ").append(conditionsJoiner);
        }

        if (!selectObj.getGroupBy().isEmpty()) {
            StringJoiner groupByJoiner = new StringJoiner(", ");
            selectObj.getGroupBy().forEach(groupCol -> {
                String tableName = groupCol.getClass().isAnnotationPresent(TableName.class)
                        ? groupCol.getClass().getAnnotation(TableName.class).value()
                        : groupCol.getClass().getSimpleName();
                groupByJoiner.add(tableName + "." + groupCol.value());
            });
            query.append(" GROUP BY ").append(groupByJoiner);
        }

        if (!selectObj.getOrderBy().isEmpty()) {
            StringJoiner orderByJoiner = new StringJoiner(", ");
            selectObj.getOrderBy().forEach(col -> {
                String tableName = col.getClass().isAnnotationPresent(TableName.class)
                        ? col.getClass().getAnnotation(TableName.class).value()
                        : col.getClass().getSimpleName();
                orderByJoiner.add(tableName + "." + col.value());
            });
            query.append(" ORDER BY ").append(orderByJoiner);
        }

        if (selectObj.getLimit() != -1) {
            query.append(" ").append(selectObj.getLimit());
        }

        return query.append(";").toString();
    }
}