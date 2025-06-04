package pitoshnaya.impact.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pitoshnaya.impact.validate.IsoDateTimeValidator;

@Documented
@Constraint(validatedBy = IsoDateTimeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoDateTime {
  String message() default "Дата и время должны быть в формате yyyy-MM-dd'T'HH:mm:ss";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
