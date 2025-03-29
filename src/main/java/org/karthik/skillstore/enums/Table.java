package org.karthik.skillstore.enums;
import  org.karthik.skillstore.querylayer.Columns;
import java.util.List;
public enum Table {
    TABLE;
    public String value(){
        return this.toString();
    }

    public List<Columns> getColumns() {
        return null;
    }

    public Class<?> getColumnClass() {
        return null;
    }
}
