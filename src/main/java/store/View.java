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

    public static void printReceipt(DiscountResult discountResult) {
        printHeader();
        printProducts(discountResult);
        printFreeItems(discountResult);
        printSummary(discountResult);
    }

    private static void printHeader() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
    }

    private static void printProducts(DiscountResult discountResult) {
        for (Products product : discountResult.getFreeItems()) {
            System.out.printf("%s\t\t%d \t%,d\n", product.getName(), product.getQuantity(), product.getPrice() * product.getQuantity());
        }
    }

    private static void printFreeItems(DiscountResult discountResult) {
        System.out.println("=============증 정===============");
        for (Products freeItem : discountResult.getFreeItems()) {
            System.out.printf("%s\t\t%d\n", freeItem.getName(), freeItem.getQuantity());
        }
    }

    private static void printSummary(DiscountResult discountResult) {
        System.out.println("====================================");
        System.out.printf("총구매액\t\t\t%,d\n", discountResult.getTotalAmount());
        System.out.printf("행사할인\t\t\t-%,d\n", discountResult.getPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%,d\n", discountResult.getMembershipDiscount());
        System.out.printf("내실돈\t\t\t %,d\n", discountResult.getTotalAmount() - discountResult.getPromotionDiscount() - discountResult.getMembershipDiscount());
    }
}
