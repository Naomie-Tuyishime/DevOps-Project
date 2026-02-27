import com.microsoft.playwright.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortingTest {

    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
        Locator sortDropdown = page.locator("[data-test='sort']");
        sortDropdown.selectOption("name,asc");
        page.waitForTimeout(2000);
        checkNameSorting(page, true);

        sortDropdown.selectOption("name,desc");
        page.waitForTimeout(2000);
        checkNameSorting(page, false);

        sortDropdown.selectOption("price,desc");
        page.waitForTimeout(2000);
        checkPriceSorting(page, false);

        sortDropdown.selectOption("price,asc");
        page.waitForTimeout(2000);
        checkPriceSorting(page, true);

        sortDropdown.selectOption("co2_rating,asc");
        page.waitForTimeout(2000);
        checkCO2Sorting(page, true);

        sortDropdown.selectOption("co2_rating,desc");
        page.waitForTimeout(2000);
        checkCO2Sorting(page, false);

        browser.close();
        playwright.close();
    }


    private static void checkNameSorting(Page page, boolean ascending) {
        Locator names = page.locator("[data-test='product-name']");
        int total = names.count();

        List<String> productNames = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            productNames.add(names.nth(i).innerText().trim());
        }

        for (int i = 0; i < productNames.size() - 1; i++) {
            String current = productNames.get(i);
            String next = productNames.get(i + 1);
            if (ascending) {
                assertTrue(current.compareToIgnoreCase(next) <= 0,
                        "Name sorting failed: " + current + " vs " + next);
            } else {
                assertTrue(current.compareToIgnoreCase(next) >= 0,
                        "Name sorting failed: " + current + " vs " + next);
            }
        }
        System.out.println("✅ Name sorting " + (ascending ? "A→Z" : "Z→A") + " passed");
    }

    private static void checkPriceSorting(Page page, boolean ascending) {
        Locator prices = page.locator("[data-test='product-price']");
        int total = prices.count();
        List<Integer> priceValues = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            priceValues.add(extractNumber(prices.nth(i).innerText().trim()));
        }

        for (int i = 0; i < priceValues.size() - 1; i++) {
            if (ascending) {
                assertTrue(priceValues.get(i) <= priceValues.get(i + 1),
                        "Price sorting failed: " + priceValues.get(i) + " vs " + priceValues.get(i + 1));
            } else {
                assertTrue(priceValues.get(i) >= priceValues.get(i + 1),
                        "Price sorting failed: " + priceValues.get(i) + " vs " + priceValues.get(i + 1));
            }
        }
        System.out.println("✅ Price sorting " + (ascending ? "Low→High" : "High→Low") + " passed");
    }

    private static void checkCO2Sorting(Page page, boolean ascending) {
        Locator co2 = page.locator("[data-test='co2-rating-badge']");
        int total = co2.count();

        List<Integer> co2Values = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            co2Values.add(extractNumber(co2.nth(i).innerText().trim()));
        }

        for (int i = 0; i < co2Values.size() - 1; i++) {
            if (ascending) {
                assertTrue(co2Values.get(i) <= co2Values.get(i + 1),
                        "CO2 sorting failed: " + co2Values.get(i) + " vs " + co2Values.get(i + 1));
            } else {
                assertTrue(co2Values.get(i) >= co2Values.get(i + 1),
                        "CO2 sorting failed: " + co2Values.get(i) + " vs " + co2Values.get(i + 1));
            }
        }
        System.out.println("✅ CO2 sorting " + (ascending ? "Low→High" : "High→Low") + " passedff");
    }

    private static int extractNumber(String text) {
        String  numberOnly = text.replaceAll("[^0-9]", "");
        return numberOnly.isEmpty() ? 0 : Integer.parseInt(numberOnly);
    }
}