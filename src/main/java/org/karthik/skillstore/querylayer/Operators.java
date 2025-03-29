package org.karthik.skillstore.querylayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public enum Operators {
    EQUALS("="), NOTEQUALS("!="), LESSTHAN("<"), LESSTHANOREQUALTO("<="), GREATERTHAN(">"), GREATERTHANOREQUALTO(">="),
    MAX("MAX"), MIN("MIN"), AVG("AVG"), COUNT("COUNT");

    private static final Map<String, Operators> LOOKUP_MAP = new HashMap<>();

    static {
        for (Operators op : Operators.values()) {
            LOOKUP_MAP.put(op.value(), op);
        }
    }

    private String operator;

    private Operators(String operator) {
        this.operator = operator;
    }

    public String value() {
        return this.operator;
    }

    public static Operators getOperator(String operator) {
        Operators op = LOOKUP_MAP.get(operator);
        if (op == null) {
            throw new IllegalArgumentException("Operator " + operator + " does not exist");
        }
        return op;
    }

    public static List<Operators> getAllOperators() {
        return Arrays.asList(Operators.values());
    }

}
