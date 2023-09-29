package com.onlyteo.sandbox.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPassword {

    String message() default "{validation.password.Valid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field() default "password";

    String confirmField() default "confirmPassword";
}

