package org.karthik.skillstore.querylayer;

import org.karthik.skillstore.enums.Table;

import org.karthik.skillstore.querylayer.Joins;
public class Join {
    public Table table1;
    //	public String table1Alias;
    public Table table2;
    //	public String table2Alias;
    public Joins joinType;
    public Columns table1col;
    public Columns table2col;
    public Operators operator;

    public Join(Joins type, Table table1, Columns table1col, Operators operator, Table table2, Columns table2col) {

        // inner join new table on new table. new table col on  old table. old table col
        this.joinType = type;
        this.table1 = table1;
        this.table1col = table1col;
        this.operator = operator;
        this.table2 = table2;
        this.table2col = table2col;
    }

//	public Join(Joins type, String table1alias, Columns table1col, Operators operator, String table2alias, Columns table2col) {
//		this.joinType = type;
//		this.table1Alias = table1alias;
//		this.table1col = table1col;
//		this.operator = operator;
//		this.table2Alias = table2alias;
//		this.table2col = table2col;
//	}

    @Override
    public String toString() {
        System.out.println("join type"+this.joinType);
        System.out.println("table1"+this.table1);
        System.out.println("table1col"+table1col);
        System.out.println("operator :"+this.operator);
        System.out.println("table2 :"+this.table2);
        System.out.println("table2col :"+ this.table2col);
        return "";
    }
}
