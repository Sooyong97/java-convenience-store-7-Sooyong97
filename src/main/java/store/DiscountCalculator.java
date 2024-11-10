package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class DiscountCalculator {
    private List<Products> products;
    private List<Promotion> promotions;

    public DiscountCalculator(List<Products> products, List<Promotion> promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public DiscountResult calculateDiscounts() {
        int totalAmount = calculateTotalAmount();
        int promotionDiscount = 0;
        List<Products> freeItems = new ArrayList<>();

        // 프로모션 할인을 적용
        for (Products product : products) {
            promotionDiscount += applyPromotionDiscount(product, freeItems);
        }

        return new DiscountResult(totalAmount, promotionDiscount, 0, freeItems); // 멤버십 할인은 여기서 제외
    }

    private int calculateTotalAmount() {
        return products.stream()
                .mapToInt(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    private int applyPromotionDiscount(Products product, List<Products> freeItems) {
        // 현재 날짜를 LocalDateTime으로 가져오기
        LocalDateTime currentDate = DateTimes.now();
        int discount = 0;

        // 프로모션이 적용 가능한지 확인 후 적용
        for (Promotion promo : promotions) {
            if (!promo.isApplicable(product)) {  // `currentDate`를 넘길 필요 없음
                continue;
            }
            discount += calculatePromotionDiscount(product, promo, freeItems);
        }
        return discount;
    }

    private int calculatePromotionDiscount(Products product, Promotion promo, List<Products> freeItems) {
        if (promo.getName().equals("탄산2+1")) {
            return applySpecialPromotion(product, freeItems);
        }

        int times = product.getQuantity() / promo.getBuyQuantity();
        int discount = promo.getDiscountAmount(product, times);
        freeItems.add(new Products(product.getName(), product.getPrice(), promo.getGetQuantity() * times, null));

        return discount;
    }

    private int applySpecialPromotion(Products product, List<Products> freeItems) {
        int freeQuantity = (product.getQuantity() == 2) ? 1 : product.getQuantity();
        freeItems.add(new Products(product.getName(), product.getPrice(), freeQuantity, null));

        return product.getPrice() * freeQuantity;
    }
}