package store;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Promotion> promotions = Promotion.readPromotionsFromFile("src/main/resources/promotions.md");
        List<Products> products = Products.read_md("src/main/resources/products.md");

        StoreController storeController = new StoreController(products, promotions);

        storeController.run();
    }
}
