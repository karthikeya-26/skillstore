package org.karthik.skillstore.utils;

import org.karthik.skillstore.dbutil.Database;

import java.io.IOException;

public class StartupTasks {

    public static void loadProperties() throws IOException {
        Database.properties.load(StartupTasks.class.getClassLoader().getResourceAsStream("app.properties"));
        return;
    }

    public static void createDatabaseTables() {
        QueryLayerSetup dbSetup = new QueryLayerSetup();
        dbSetup.createTables("schema.json");
    }


}
