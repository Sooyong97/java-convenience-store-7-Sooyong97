package store;

import java.util.*;

public class View {

    // 재고 현황 출력
    public static void displayProducts(List<Products> productsList) {
        for (Products product : productsList) {
            String name = product.getName();
            String price = String.format("%,d", product.getPrice());
            int quantity = product.getQuantity();
            String promotion = product.getPromotion();
            if (quantity == 0) {
                System.out.printf("- %s %s원 재고 없음", name, price);
            } else {
                System.out.printf("- %s %s원 %d개", name, price, quantity);
            }
            if (promotion != null) {
                System.out.print(" " + promotion);
            }
            System.out.println();
        }
    }
}
