package store;

import java.util.*;

public class View {

    public static void printHello() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.");
    }

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

    public static void printPurchase() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }

    public static void printPromotions(String product, int count) {
        System.out.println("현재 " + product + " " + count + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }

    public static void printMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public static void printReceipt() {
        
    }

}
