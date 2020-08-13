import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ChangeMachine {
  public static void main(String[] args) {

    String name = "output";

    System.out
        .println("Enter your username -- this is going to be used as the name of the output file:");
    Scanner scanner = new Scanner(System.in);
    name = scanner.nextLine();
    scanner.close();

    String filename = name + ".txt";

    HashMap<String, Currency> currencies = InitializeHashmapOfCurrencies();

    Random r = new Random();
    Integer indexOfRandomCurrency = r.nextInt(currencies.size());
    String keyOfRandomCurrency = (String) currencies.keySet().toArray()[indexOfRandomCurrency];
    Currency randomCurrency = currencies.get(keyOfRandomCurrency);
    BigDecimal randomPrice = new BigDecimal(r.nextInt(100000) + "." + (r.nextInt(randomCurrency.getFractureMultiplier())));

    System.out.println(System.lineSeparator() + "Price and currency randomly generated are: "
        + System.lineSeparator() + randomPrice + " " + randomCurrency.getName()
        + System.lineSeparator());

    long startTime = System.nanoTime();
    CalculateNumberOfNotesAndCoins(randomCurrency, randomPrice);
    long endTime = System.nanoTime();

    long timeElapsed = (endTime - startTime) / 1000000; // converision to miliseconds

    try {
      FileWriter myWriter = new FileWriter(filename);
      myWriter.write(randomPrice + " " + randomCurrency.getName() + " " + timeElapsed + "ms");
      myWriter.close();
      System.out.println("Successfully wrote to file: " + filename + " in the current working directory.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private static void CalculateNumberOfNotesAndCoins(Currency randomCurrency,
      BigDecimal randomPrice) {
    StringBuffer strbuf = new StringBuffer();
    BigDecimal remainderOfPrice = randomPrice;
    BigDecimal[] valuesOfNotesAndCoins = randomCurrency.getValues();
    BigDecimal smallestUnit = valuesOfNotesAndCoins[0];
    BigDecimal lastUnitNeeded = BigDecimal.ZERO;
    Integer indexOfNoteOrCoinValue = valuesOfNotesAndCoins.length - 1;
    Integer numberOfNotesAndCoinsNeeded = 0;
    Integer numberOfOneKindOfNoteOrCoin = 0;

    // calculate the notes/coins required
    while (remainderOfPrice.compareTo(BigDecimal.ZERO) == 1 && indexOfNoteOrCoinValue >= 0) {
      lastUnitNeeded = valuesOfNotesAndCoins[indexOfNoteOrCoinValue];

      while (remainderOfPrice.compareTo(lastUnitNeeded) >= 0) {
        remainderOfPrice = remainderOfPrice.subtract(lastUnitNeeded);
        numberOfNotesAndCoinsNeeded++;
        numberOfOneKindOfNoteOrCoin++;
      }

      if (indexOfNoteOrCoinValue == 0 && remainderOfPrice.compareTo(lastUnitNeeded) != 0) {
        // special rounding if remainder of price is not 0
        if (remainderOfPrice.compareTo(BigDecimal.ZERO) > 0) {
          // check if remainder is closer to 0 or the smallest unit
          BigDecimal distanceToSmallestUnit = smallestUnit.subtract(remainderOfPrice);
          if (distanceToSmallestUnit.compareTo(remainderOfPrice) <= 0) {
            numberOfNotesAndCoinsNeeded++;
            numberOfOneKindOfNoteOrCoin++;
          }
        }
      }

      if (numberOfOneKindOfNoteOrCoin != 0) {
        BigDecimal decimalPart =
            lastUnitNeeded.remainder(BigDecimal.ONE).multiply(randomCurrency.getFractureMultiplier()));
        BigDecimal wholePart = lastUnitNeeded.subtract(decimalPart);
        String typeOfMoney = "";

        if (decimalPart.compareTo(BigDecimal.ZERO) == 0) {
          typeOfMoney = wholePart.toBigInteger().toString() + " " + randomCurrency.getSymbol();
        } else {
          typeOfMoney =
              decimalPart.toBigInteger().toString() + " " + randomCurrency.getFractureSymbol();
        }
        strbuf.append(numberOfOneKindOfNoteOrCoin + " x " + typeOfMoney + System.lineSeparator());
      }
      indexOfNoteOrCoinValue--;
      numberOfOneKindOfNoteOrCoin = 0;
    }
    System.out.println(
        numberOfNotesAndCoinsNeeded + " notes & coins needed altogether:" + System.lineSeparator());

    System.out.println(strbuf);
  }

  private static HashMap<String, Currency> InitializeHashmapOfCurrencies() {
    HashMap<String, Currency> currencies = new HashMap<String, Currency>();

    Currency huf = new Currency("HUF", "Ft",
        new BigDecimal[] {new BigDecimal("5.0"), new BigDecimal("10.0"), new BigDecimal("20.0"),
            new BigDecimal("50.0"), new BigDecimal("100.0"), new BigDecimal("200.0"),
            new BigDecimal("500.0"), new BigDecimal("1000.0"), new BigDecimal("2000.0"),
            new BigDecimal("5000.0"), new BigDecimal("10000.0"), new BigDecimal("20000.0")});

    Currency eur = new Currency("EUR", "€",
        new BigDecimal[] {new BigDecimal("0.01"), new BigDecimal("0.02"), new BigDecimal("0.05"),
            new BigDecimal("0.1"), new BigDecimal("0.2"), new BigDecimal("0.5"),
            new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("5.0"),
            new BigDecimal("10.0"), new BigDecimal("20.0"), new BigDecimal("50.0"),
            new BigDecimal("100.0"), new BigDecimal("200.0")},
        "euro cent", "¢", 100);

    Currency usd = new Currency("USD", "$",
        new BigDecimal[] {new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.1"),
            new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1.0"),
            new BigDecimal("2.0"), new BigDecimal("5.0"), new BigDecimal("10.0"),
            new BigDecimal("20.0"), new BigDecimal("50.0"), new BigDecimal("100.0")},
        "cent", "¢", 100);

    currencies.put(huf.getName(), huf);
    currencies.put(eur.getName(), eur);
    currencies.put(usd.getName(), usd);
    return currencies;
  }
}
