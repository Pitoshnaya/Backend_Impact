package pitoshnaya.impact.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import pitoshnaya.impact.annotation.IsoDateTime;

public class IsoDateTimeValidator implements ConstraintValidator<IsoDateTime, String> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }

    try {
      LocalDateTime.parse(value, FORMATTER);
      return true;
    } catch (DateTimeException e) {
      return false;
    }
  }
}
