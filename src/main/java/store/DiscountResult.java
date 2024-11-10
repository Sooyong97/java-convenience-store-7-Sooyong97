package store;

import java.util.List;

public class DiscountResult {
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private List<Products> freeItems;

    public DiscountResult(int totalAmount, int promotionDiscount, int membershipDiscount, List<Products> freeItems) {
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.freeItems = freeItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public List<Products> getFreeItems() {
        return freeItems;
    }
}