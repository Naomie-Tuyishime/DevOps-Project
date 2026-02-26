import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortingTest {
    public static void main (String[] args){
        Playwright playwright  = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
Page page = browser.newPage();
page.navigate("https://practicesoftwaretesting.com/");
        Locator sortDropdown = page.locator("[data-test='sort']");
        sortDropdown.selectOption("name,asc");
        page.waitForTimeout(3000);
        Locator titles = page.locator(".card-title");

        int count = titles.count();

        List<String> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            products.add(titles.nth(i).innerText().trim());
        }
        for (int i = 0; i < products.size() - 1; i++) {

            String current = products.get(i);
            String next = products.get(i + 1);

            if (current.compareToIgnoreCase(next) > 0) {
                throw new AssertionError(
                        "Sorting failed between: " + current + " and " + next
                );
            } else {
                assertTrue(
                        products.get(i).compareToIgnoreCase(products.get(i + 1)) <= 0
                );

        }
        sortDropdown.selectOption("name,desc");
        page.waitForTimeout(3000);
        sortDropdown.selectOption("price,desc");
        page.waitForTimeout(3000);
        sortDropdown.selectOption("price,asc");
        page.waitForTimeout(3000);
        sortDropdown.selectOption("co2_rating,asc");
        page.waitForTimeout(3000);
        sortDropdown.selectOption("co2_rating,desc")

        browser.close();
        playwright.close();



    }

}}
