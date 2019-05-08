package com.learning.wheel.paramcheck;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author mazhenjie
 * @since 2019/5/7
 */
public class ValidationUtil {

    /**
     * 开启快速结束模式 failFast (true)
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    private ValidationUtil() {
    }

    /**
     * 注解验证参数
     *
     * @param obj
     */
    public static <T> ValidResult validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            return ValidResult.fail(constraintViolations.iterator().next().getPropertyPath().toString(), constraintViolations.iterator().next().getMessage());
        }
        return ValidResult.success();
    }
}
