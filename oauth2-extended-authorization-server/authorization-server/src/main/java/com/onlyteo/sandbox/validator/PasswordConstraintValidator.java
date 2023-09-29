package com.onlyteo.sandbox.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Constraint validator to check that two passwords are equal.
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, Object> {

    private String field;
    private String confirmField;
    private String message;

    @Override
    public void initialize(final ValidPassword annotation) {
        this.field = annotation.field();
        this.confirmField = annotation.confirmField();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        final var beanWrapper = new BeanWrapperImpl(value);
        final var fieldValue = beanWrapper.getPropertyValue(field);
        final var confirmFieldValue = beanWrapper.getPropertyValue(confirmField);
        if (fieldValue != null && !fieldValue.equals(confirmFieldValue)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmField)
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
