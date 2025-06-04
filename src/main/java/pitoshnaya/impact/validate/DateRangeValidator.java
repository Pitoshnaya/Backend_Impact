package pitoshnaya.impact.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pitoshnaya.impact.annotation.ValidDateRange;
import pitoshnaya.impact.dto.HistoryBetweenRequest;

public class DateRangeValidator
    implements ConstraintValidator<ValidDateRange, HistoryBetweenRequest> {

  @Override
  public boolean isValid(HistoryBetweenRequest dto, ConstraintValidatorContext context) {
    if (dto.getStartDate() == null || dto.getEndDate() == null) {
      return true;
    }

    if (dto.getStartDate().isAfter(dto.getEndDate())) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("Начальная дата должна быть меньше конечной")
          .addPropertyNode("start")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
