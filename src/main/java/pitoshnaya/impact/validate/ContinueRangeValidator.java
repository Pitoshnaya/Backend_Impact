package pitoshnaya.impact.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pitoshnaya.impact.annotation.ValidContinueRange;
import pitoshnaya.impact.dao.CellHistoryDao;
import pitoshnaya.impact.dto.HistoryContinueRequest;

public class ContinueRangeValidator
    implements ConstraintValidator<ValidContinueRange, HistoryContinueRequest> {
  private final CellHistoryDao cellHistoryDao;

  public ContinueRangeValidator(CellHistoryDao cellHistoryDao) {
    this.cellHistoryDao = cellHistoryDao;
  }

  @Override
  public boolean isValid(HistoryContinueRequest dto, ConstraintValidatorContext context) {
    var startDate = cellHistoryDao.getCellHistoryById(dto.startId()).getRedrawingTime();

    if (startDate == null || dto.endDate() == null) {
      return true;
    }
    if (startDate.isAfter(dto.getEndDate())) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              "Часть истории по данному Id находится позже чем конечная дата")
          .addPropertyNode("start")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
