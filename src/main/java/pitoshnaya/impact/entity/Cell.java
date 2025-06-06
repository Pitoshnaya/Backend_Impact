package pitoshnaya.impact.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "cell",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"x", "y"})})
public class Cell {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer x;
  private Integer y;
  private String color;

  public Cell(int i, int j, String color) {
    this.x = i;
    this.y = j;
    this.color = color;
  }

  public Cell() {}

  public Long getId() {
    return id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
