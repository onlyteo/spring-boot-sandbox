package com.onlyteo.sandbox.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.BeanWrapperImpl

/**
 * Constraint validator to check that two passwords match.
 */
class PasswordConstraintValidator : ConstraintValidator<ValidPassword, Any> {

    private var field: String? = null
    private var confirmField: String? = null
    private var message: String? = null

    override fun initialize(annotation: ValidPassword) {
        this.field = annotation.field
        this.confirmField = annotation.confirmField
        this.message = annotation.message
    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val beanWrapper = BeanWrapperImpl(value)
        val fieldValue = beanWrapper.getPropertyValue(field!!)
        val confirmFieldValue = beanWrapper.getPropertyValue(confirmField!!)!!
        if (fieldValue != null && fieldValue != confirmFieldValue) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation()
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(confirmField)
                .addConstraintViolation()
            return false
        } else {
            return true
        }
    }
}