package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int getQuantity;
    private String startDate;
    private String endDate;

    public Promotion(String name, int buyQuantity, int getQuantity, String startDate, String endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // 프로모션이 적용 가능한지 확인
    public boolean isApplicable(Products product) {
        // 현재 날짜를 LocalDateTime으로 가져오기
        LocalDateTime currentDate = DateTimes.now();

        // String 날짜를 LocalDateTime으로 변환
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");

        return product.getPromotion() != null && product.getPromotion().equals(this.name)
                && !currentDate.isBefore(startDateTime) && !currentDate.isAfter(endDateTime);
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public int getDiscountAmount(Products product, int times) {
        return product.getPrice() * getQuantity * times;
    }

    // promotion.md 파일 읽기
    public static List<Promotion> readPromotionsFromFile(String fileName) {
        List<Promotion> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 첫 번째 줄 건너뛰기
                if (line.startsWith("name")) {
                    continue;
                }

                String[] data = line.split(",");
                String name = data[0];
                int buyQuantity = Integer.parseInt(data[1]);
                int getQuantity = Integer.parseInt(data[2]);
                String startDate = data[3];
                String endDate = data[4];

                // Promotion 객체 생성 후 리스트에 추가
                promotions.add(new Promotion(name, buyQuantity, getQuantity, startDate, endDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promotions;
    }
}