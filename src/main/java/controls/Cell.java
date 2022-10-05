package controls;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class Cell extends BaseElement {

    private Cell(By locator) {
        super(locator);
    }

    public static Cell cssSelector(String selector) {
        return new Cell(By.cssSelector(selector));
    }

    public static Cell xpath(String xpathExpression) {
        return new Cell(By.xpath(xpathExpression));
    }

    public static Cell id(String id) {
        return new Cell(By.id(id));
    }

    public static Cell name(String name) {
        return new Cell(By.name(name));
    }

}
