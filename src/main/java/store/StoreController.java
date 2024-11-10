package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreController {

    private List<Products> productsList;
    private List<Promotion> promotions;
    private DiscountCalculator discountCalculator;
    private Scanner scanner;

    public StoreController(List<Products> productsList, List<Promotion> promotions) {
        this.productsList = productsList;
        this.promotions = promotions;
        this.discountCalculator = new DiscountCalculator(productsList, promotions);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            // Step 1: 환영 메시지 및 상품 목록 출력
            View.printHello();
            View.displayProducts(productsList);

            // Step 2: 구매할 상품 입력
            View.printPurchase();
            String input = scanner.nextLine();
            List<Products> selectedProducts = null;

            // 예외 처리: 재고 수량 초과 시 다시 입력 요청
            try {
                selectedProducts = parseInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;  // 오류가 발생하면 다시 입력을 받음
            }

            // Step 3: 프로모션 할인 계산
            DiscountCalculator discountCalculator = new DiscountCalculator(selectedProducts, promotions);
            DiscountResult discountResult = discountCalculator.calculateDiscounts();

            // Step 4: 멤버십 할인 여부 확인
            View.printMembership();
            String membershipInput = scanner.nextLine();
            if (membershipInput.equalsIgnoreCase("Y")) {
                // 멤버십 할인 적용
                discountResult = applyMembershipDiscount(discountResult);
            }

            // Step 5: 영수증 출력
            View.printReceipt(discountResult);

            // Step 6: 추가 구매 여부 확인
            System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
            String continueInput = scanner.nextLine();
            if (continueInput.equalsIgnoreCase("N")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }

    private List<Products> parseInput(String input) {
        List<Products> selectedProducts = new ArrayList<>();
        String[] items = input.split(",");

        // 입력된 아이템 각각을 처리
        for (String item : items) {
            // 대괄호 및 하이픈을 처리
            String[] parts = item.replaceAll("[\\[\\]]", "").split("-");

            // 형식 검사: 제품명과 수량이 정확히 두 개의 부분으로 나뉘어 있는지 확인
            if (parts.length != 2) {
                throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. 예시: [상품명-수량]");
            }

            String productName = parts[0].trim();
            int quantity = 0;
            try {
                quantity = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[ERROR] 수량은 숫자로 입력해야 합니다.");
            }

            boolean foundProduct = false;

            // 제품 목록에서 해당 상품을 찾기
            for (Products product : productsList) {
                if (product.getName().equals(productName)) {
                    // 재고 수량이 부족한지 확인
                    if (product.getQuantity() < quantity) {
                        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                    }

                    // 재고를 업데이트하고, 선택된 상품 리스트에 추가
                    product.setQuantity(product.getQuantity() - quantity);
                    selectedProducts.add(new Products(product.getName(), product.getPrice(), quantity, product.getPromotion()));
                    foundProduct = true;
                    break;
                }
            }

            // 제품이 없으면 예외를 던짐
            if (!foundProduct) {
                throw new IllegalArgumentException("[ERROR] " + productName + " 상품을 찾을 수 없습니다. 다시 입력해 주세요.");
            }
        }

        return selectedProducts;
    }

    // 멤버십 할인 적용
    private DiscountResult applyMembershipDiscount(DiscountResult discountResult) {
        int membershipDiscount = 0;

        // 프로모션 적용 후 남은 금액에 대해 멤버십 할인 계산
        int amountAfterPromotion = discountResult.getTotalAmount() - discountResult.getPromotionDiscount();
        membershipDiscount = (int) (amountAfterPromotion * 0.30);

        // 멤버십 할인의 최대 한도는 8,000원
        if (membershipDiscount > 8000) {
            membershipDiscount = 8000;
        }

        // 최종 금액 계산
        int finalAmount = discountResult.getTotalAmount() - discountResult.getPromotionDiscount() - membershipDiscount;

        return new DiscountResult(
                finalAmount,             // 최종 금액
                discountResult.getPromotionDiscount(),
                membershipDiscount,      // 멤버십 할인 금액
                discountResult.getFreeItems()
        );
    }
}