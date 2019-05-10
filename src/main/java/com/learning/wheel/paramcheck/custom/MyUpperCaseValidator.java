package com.learning.wheel.paramcheck.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author hangwei
 * @date 2019/5/10 上午 10:37
 */
public class MyUpperCaseValidator implements ConstraintValidator<UpperCase, String> {

    @Override
    public void initialize(UpperCase constraintAnnotation) {

    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        return object.equals( object.toUpperCase() );
    }
}
