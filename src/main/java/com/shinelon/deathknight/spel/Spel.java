package com.shinelon.deathknight.spel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spel
 *
 * @author shinelon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Spel {

    String keySpel() default "";

    String valueSpel() default "";
}
