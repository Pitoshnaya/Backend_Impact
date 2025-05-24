package Pitoshnaya.Impact.dto;

import Pitoshnaya.Impact.config.GridSize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record DrawRequest(
    @Max(GridSize.WIDTH) @Min(1) int x,
    @Max(GridSize.HEIGHT) @Min(1) int y,
    @Pattern(regexp = "^#([a-f0-9]{6})$", message = "{grid.cell.color}") String color
) {}
