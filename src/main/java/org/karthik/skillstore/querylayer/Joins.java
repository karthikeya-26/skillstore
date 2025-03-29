package org.karthik.skillstore.querylayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Joins {
    InnerJoin("Inner Join"), LeftJoin("Left Join"), RightJoin("Right Join"), FullJoin("Full Join");

    private static final Map<String, Joins> LOOKUP_MAP = new HashMap<>();

    static {
        for (Joins join : Joins.values()) {
            LOOKUP_MAP.put(join.value(), join);
        }
    }

    private final String joinType;

    private Joins(String joinType) {
        this.joinType = joinType;
    }

    public String value() {
        return joinType;
    }

    public static Joins getJoin(String joinName) {
        Joins join = LOOKUP_MAP.get(joinName);
        if (join == null) {
            throw new IllegalArgumentException("Join name " + joinName + " does not exist");
        }
        return join;
    }

    public static List<Joins> getAllJoins() {
        return Arrays.asList(Joins.values());
    }
}
