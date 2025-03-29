package org.karthik.skillstore.querylayer;


import java.lang.reflect.Field;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.enums.Table;
import org.karthik.skillstore.querybuilder.BuildException;
import org.karthik.skillstore.querylayer.util.PreExecuteTasks;
import org.karthik.skillstore.querylayer.util.PostExecuteTasks;

public class Query {

    private static List<Table> tablesWithoutTasks;

    static {
        tablesWithoutTasks = new ArrayList<>();
    }

    static Properties prop = Database.properties;

    public Table getTableName() {
        return null;
    }

    public String build() throws QueryException {
        return null;
    }

    public List<ResultObject> executeQuery(Query query, Class<? extends ResultObject> clazz) throws QueryException {
        if (query instanceof Insert || query instanceof Update || query instanceof Delete) {
            executeUpdate(query);
            return null;
        }
        List<ResultObject> resultList = new ArrayList<>();

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(query.build());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ResultObject r = clazz.getDeclaredConstructor().newInstance();
                for (Field f : clazz.getDeclaredFields()) {
                    f.setAccessible(true);
                    String columnName = (f.isAnnotationPresent(Column.class)) ? f.getAnnotation(Column.class).value(): f.getName();

                    try {
                        Object value = rs.getObject(columnName);
                        f.set(r, value);
                    } catch (SQLException e) {
                        f.set(r, null);
                    }
                }
                resultList.add(r);
            }

        } catch (SQLException e) {
            throw new QueryException("Database error occurred while executing the query: " + e.getMessage(), e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new QueryException("Error occurred while instantiating the result object class: " + clazz.getName(),e);
        } catch (ReflectiveOperationException e) {
            throw new QueryException("Reflection error occurred while mapping result set to object: " + e.getMessage(),e);
        } catch (Exception e) {
            throw new QueryException("An unexpected error occurred: " + e.getMessage(), e);
        }
        return resultList;
    }

    public List<Map<Columns, Object>> executeQuery(Query query, HashMap<Columns, Class<?>> fields)
            throws QueryException {
        if (query instanceof Insert || query instanceof Update || query instanceof Delete) {
            executeUpdate(query);
            return null;
        }
        List<Map<Columns, Object>> resultObj = new ArrayList<>();

        try (Connection c = Database.getConnection();
             Statement ps = c.createStatement();
             ResultSet resultSet = ps.executeQuery(query.build())) {

            while (resultSet.next()) {
                HashMap<Columns, Object> row = new HashMap<>();
                for (Columns colName : fields.keySet()) {
                    Class<?> fieldType = fields.get(colName);
                    try {
                        if (fieldType == Integer.class || fieldType == int.class) {
                            row.put(colName, resultSet.getInt(colName.value()));
                        } else if (fieldType == String.class) {
                            row.put(colName, resultSet.getString(colName.value()));
                        } else if (fieldType == Boolean.class) {
                            row.put(colName, resultSet.getBoolean(colName.value()));
                        } else if (fieldType == Double.class) {
                            row.put(colName, resultSet.getDouble(colName.value()));
                        } else if (fieldType == Date.class) {
                            row.put(colName, resultSet.getDate(colName.value()));
                        } else if (fieldType == Long.class) {
                            row.put(colName, resultSet.getLong(colName.value()));
                        } else {
                            row.put(colName, resultSet.getObject(colName.value()));
                        }
                    } catch (SQLException e) {
                        throw new QueryException("Error retrieving value for column: " + colName.value(), e);
                    }
                }
                resultObj.add(row);
            }
        } catch (SQLException e) {
            throw new QueryException("Database error occurred while executing the query: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new QueryException("An unexpected error occurred: " + e.getMessage(), e);
        }
        return resultObj;
    }

    public int executeUpdate(Query query) throws QueryException {
        PreExecuteTasks pretasks = preExecuteHelper(query);
        int status;
        String sqlStatement = query.build();

        try (Connection c = Database.getConnection(); PreparedStatement  ps = c.prepareStatement(sqlStatement)) {
            status = ps.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            throw new QueryException("Database error occurred while executing the query for table: "
                    + query.getTableName() + " | Query: " + sqlStatement, e);
        }
         if (status >= 0 && !query.getTableName().value().equals("ChangeLog") ) {
            postExecuteHelper(query, pretasks);
        }
        return status;
    }

    public int executeUpdate(Query query, boolean returnGeneratedKey) throws QueryException {

        PreExecuteTasks preTasks = preExecuteHelper(query);

        int genKey = -1;
        String sqlStatement;
        sqlStatement = query.build();

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int success = ps.executeUpdate();
            if (success >= 0 && returnGeneratedKey) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        genKey = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    throw new QueryException("Error retrieving generated key for query: " + query.getTableName()
                            + " | Query: " + sqlStatement, e);
                }
            }

        } catch (SQLException e) {
            throw new QueryException("Database error occurred while executing update for query: " + query.getTableName()
                    + " | Query: " + sqlStatement, e);
        }

        if (genKey >= 0 ) {
            postExecuteHelper(query, preTasks);
        }

        return genKey;
    }

    private PreExecuteTasks preExecuteHelper(Query query) throws QueryException {
        PreExecuteTasks preTasks = null;
//        if (!(query.getTableName().equals(Table.ChangeLog))) {
            preTasks = new PreExecuteTasks();
            for (Method m : preTasks.getClass().getDeclaredMethods()) {
                m.setAccessible(true);
                if (m.getParameterCount() == 1) {
                    try {
                        m.invoke(preTasks, query);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new QueryException("Error executing pre-tasks for query: " + query.getTableName(), e);
                    }
                }
            }
//        }
        return preTasks;
    }

    private void postExecuteHelper(Query query, PreExecuteTasks preExecuteTasks) throws QueryException {
        PostExecuteTasks posttasks = new PostExecuteTasks();
        for (Method m : posttasks.getClass().getDeclaredMethods()) {
            m.setAccessible(true);
            try {
                m.invoke(posttasks, query, preExecuteTasks.getResultMap());
            } catch (IllegalAccessException | InvocationTargetException e) {
                try {
                    throw new QueryException("Error executing post-tasks for query: " + query.getTableName()
                            + " | Query: " + query.build(), e);
                } catch (QueryException  e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}