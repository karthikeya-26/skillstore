package org.karthik.skillstore.querylayer;

import org.karthik.skillstore.enums.Table;

public class Alter {

    public String tableName;
    public String from;
    public String to;
    //table

    public Alter table(Table table) {
        this.tableName = table.value();
        return this;
    }


    //columns
}