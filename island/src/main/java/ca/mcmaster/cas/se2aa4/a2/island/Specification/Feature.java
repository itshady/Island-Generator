package ca.mcmaster.cas.se2aa4.a2.island.Specification;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations for all features within the island generation (shape, elevation, rivers, lakes, etc...)
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {

}
