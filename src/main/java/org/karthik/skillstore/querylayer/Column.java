package org.karthik.skillstore.querylayer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Available at runtime for reflection
@Target(ElementType.FIELD) // Can be used on fields
public @interface Column {
    String value(); // Annotation will store column name
}
