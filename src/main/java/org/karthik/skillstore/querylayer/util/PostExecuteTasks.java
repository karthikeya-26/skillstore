package org.karthik.skillstore.querylayer.util;

import java.util.HashMap;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.karthik.skillstore.enums.*;
import org.karthik.skillstore.querylayer.*;


public class PostExecuteTasks {
    static Set<Table> nonAuditTables;
    static Set<Table> serverNotifications;

    static {
        nonAuditTables = new HashSet<>();
        serverNotifications = new HashSet<>();
//
//        nonAuditTables.add(Table.Sessions);
//        nonAuditTables.add(Table.ChangeLog);
//        nonAuditTables.add(Table.Servers);
//
//        serverNotifications.add(Table.Sessions);
//        serverNotifications.add(Table.UserDetails);

        // from config populate this
    }

    public void audit(Query query, Map<String, Object> resultMap) throws Exception {
        if(nonAuditTables.contains(query.getTableName())) {
            return;
        }
        if (query instanceof Insert) {
            new PostExecuteTasksHelper().auditInsert(query);
        } else if (query instanceof Update) {
            new PostExecuteTasksHelper().auditUpdate(query, resultMap);
        } else if (query instanceof Delete) {
            new PostExecuteTasksHelper().auditDelete(query, resultMap);
        }
    }

    public void notifyServers(Query query, HashMap<String, Object> resultMap) {
        if (!(serverNotifications.contains(query.getTableName()))) {
            return;
        }
        if (query instanceof Update) {
//            Update updateQuery = (Update) query;
//            List<Condition> conditions = updateQuery.getConditions();
//            if (conditions.isEmpty()) {
//                return;
//            }
//            RequestSender rs = new RequestSender();
//            StringJoiner paramsAndValues = new StringJoiner("&");
//            for (Condition c : conditions) {
//                paramsAndValues.add(c.getColumn().value() +"=" + c.getValue());
//            }
//     		rs.send(paramsAndValues.toString());
            return;
        }
        if (query instanceof Delete) {
//            Delete deleteQuery = (Delete) query;
//            List<Condition> conditions = deleteQuery.getConditions();
//            if(conditions.isEmpty()) {
//                return;
//            }
//            RequestSender rs = new RequestSender();
//            StringJoiner paramsAndValues = new StringJoiner("&");
//            for(Condition c : conditions) {
//                paramsAndValues.add(c.getColumn().value() + "=" + c.getValue());
//            }
//			rs.send(paramsAndValues.toString());
            return;
        }
    }
}
