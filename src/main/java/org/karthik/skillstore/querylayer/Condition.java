package org.karthik.skillstore.querylayer;


public class Condition {
    public Columns column;
    public Operators operator;
    public Object value;


    public Condition(Columns column, Operators operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Columns getColumn() {
        return this.column;
    }

    public void setColumn(Columns column) {
        this.column = column;
    }

    public Operators getOperator() {
        return this.operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Condition [column=" + column + ", operator=" + operator + ", value=" + value + "]";
    }
}