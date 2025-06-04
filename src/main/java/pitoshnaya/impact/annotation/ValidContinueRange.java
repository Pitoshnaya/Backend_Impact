package pitoshnaya.impact.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pitoshnaya.impact.validate.ContinueRangeValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContinueRangeValidator.class)
public @interface ValidContinueRange {
  String message() default "Начальная дата должна быть меньше или равна конечной";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
