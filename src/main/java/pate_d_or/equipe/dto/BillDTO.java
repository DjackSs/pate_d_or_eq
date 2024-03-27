package pate_d_or.equipe.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BillDTO {

  private int tableNumber;
  private String dishName;
  private BigDecimal dishPrice;
}
