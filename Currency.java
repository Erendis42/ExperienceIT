import java.math.BigDecimal;

public class Currency {
  private String name;
  private String symbol;

  private BigDecimal[] values;
  private String fractureName;
  private String fractureSymbol;
  private BigDecimal fractureMultiplier;

  public Currency(String name, String symbol, BigDecimal[] values, String fractureName,
      String fractureSymbol, BigDecimal fractureMultiplier) {
    this.name = name;
    this.symbol = symbol;
    this.values = values;
    this.fractureName = fractureName;
    this.fractureSymbol = fractureSymbol;
    this.fractureMultiplier = fractureMultiplier;
  }

  public Currency(String name, String symbol, BigDecimal[] values, BigDecimal fractureMultiplier) {
    this.name = name;
    this.symbol = symbol;
    this.values = values;
    this.fractureName = "";
    this.fractureSymbol = "";
    this.fractureMultiplier = 100;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }


  public BigDecimal[] getValues() {
    return values;
  }

  public String getFractureName() {
    return fractureName;
  }

  public String getFractureSymbol() {
    return fractureSymbol;
  }

  public BigDecimal getFractureMultiplier() {
    return fractureMultiplier;
  }
}
