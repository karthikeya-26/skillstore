package org.karthik.skillstore.querylayer.util;

import com.google.gson.JsonObject;
import org.karthik.skillstore.querylayer.*;

import java.util.List;
import java.util.Map;

public class PostExecuteTasksHelper {
    public void auditInsert(Query query) throws QueryException {
        Insert insertQuery = (Insert) query;
        String table = insertQuery.getTableName().value();
        List<Columns> cols = insertQuery.getColumns();
        List<Object> vals = insertQuery.getValues();
//        Insert audit = new Insert();
//        audit.table(Table.ChangeLog).columns(ChangeLog.TABLE_NAME, ChangeLog.REQ_TYPE, ChangeLog.NEW_VAL);
//        JsonObject newData = new JsonObject();
//        for (int i = 0; i < cols.size(); i++) {
//            newData.addProperty(cols.get(i).value(), vals.get(i).toString());
//        }
//        audit.values(table, "INSERT", newData.toString());
//        metaDataAdder(audit);
//        audit.executeUpdate();
        return;
    }

    public void auditUpdate(Query query, Map<String,Object> resultMap) throws QueryException {
        Update updateQuery = (Update) query;
        String table = query.getTableName().value();
        List<Columns> cols = updateQuery.getColumns();
        List<String> vals = updateQuery.getValues();
        List<Condition> conditions = updateQuery.getConditions();
        @SuppressWarnings("unchecked")
        List<Map<Columns, Object>> refData = (List<Map<Columns, Object>>) resultMap.get("getRefData");
        if (refData == null) {
            return;
        }
        for (Map<Columns, Object> data : refData) {
            JsonObject oldData = new JsonObject();
            for (Map.Entry<Columns, Object> row : data.entrySet()) {
                String key = row.getKey().value();
                Object value = row.getValue();

                if (value != null) {
                    oldData.addProperty(key, value.toString());
                } else {
                    oldData.addProperty(key, (String) "0");
                }
            }
            for (Condition c : conditions) {
                oldData.addProperty(c.getColumn().value(), c.getValue().toString());
            }
            JsonObject newData = new JsonObject();
            for (int i = 0; i < cols.size(); i++) {
                newData.addProperty(cols.get(i).value(), vals.get(i));
            }
            for (Condition c : conditions) {
                newData.addProperty(c.getColumn().value(), c.getValue().toString());
            }

//            Insert audit = new Insert();
//            audit.table(Table.ChangeLog)
//                    .columns(ChangeLog.TABLE_NAME, ChangeLog.REQ_TYPE, ChangeLog.OLD_VAL, ChangeLog.NEW_VAL)
//                    .values(table, "UPDATE", oldData.toString(), newData.toString());
//            metaDataAdder(audit);
//            audit.executeUpdate();
        }
    }

    public void auditDelete(Query query, Map<String, Object> resultMap) throws QueryException{
        Delete deleteQuery = (Delete) query;
        String table = deleteQuery.getTableName().value();
        @SuppressWarnings("unchecked")
        List<Map<Columns, Object>> refData = (List<Map<Columns, Object>>) resultMap.get("getRefData");
        if (refData == null) {
            return;
        }
        for (Map<Columns, Object> data : refData) {
            JsonObject oldData = new JsonObject();
            for (Map.Entry<Columns, Object> row : data.entrySet()) {
                String key = row.getKey().value();
                Object value = row.getValue();
                if (value != null) {
                    oldData.addProperty(key, value.toString());
                } else {
                    oldData.addProperty(key, (String) "0");
                }
            }
//            Insert audit = new Insert();
//            audit.table(Table.ChangeLog).columns(ChangeLog.TABLE_NAME, ChangeLog.REQ_TYPE, ChangeLog.OLD_VAL)
//                    .values(table, "DELETE", oldData.toString());
//            metaDataAdder(audit);
//            audit.executeUpdate();
        }

    }

//    private void metaDataAdder(Insert audit) {
//        if(SessionFilter.SESSION_ID.get() !=null) {
//            audit.columns(ChangeLog.SESSION_ID,ChangeLog.END_POINT,ChangeLog.MODIFIED_BY);
//            audit.values(SessionFilter.SESSION_ID.get(),SessionFilter.ENDPOINT.get(),"user :"+SessionFilter.USER_ID.get().toString());
//        }else {
//            audit.columns(ChangeLog.MODIFIED_BY);
//            audit.values(Thread.currentThread().getName());
//        }
//    }
}
