package com.learning.wheel.paramcheck.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author hangwei
 * @date 2019/5/10 上午 10:35
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MyUpperCaseValidator.class)
public @interface UpperCase {
    String message() default "{must-upper}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
