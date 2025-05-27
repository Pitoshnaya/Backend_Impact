package pitoshnaya.impact.validate;

import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(List<Map<String, String>> violations) {}
