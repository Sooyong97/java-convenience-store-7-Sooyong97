package store;

import java.io.*;
import java.util.*;

public class Products {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Products(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    // Getter 메서드들
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    // md 파일 읽어오기
    public static List<Products> read_md(String fileName) {
        List<Products> productsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 첫 번째 줄 건너뛰기
                if (line.startsWith("name")) {
                    continue;
                }

                String[] data = line.split(",");
                String name = data[0];
                int price = Integer.parseInt(data[1]);
                int quantity = Integer.parseInt(data[2]);
                String promotion = data[3].equals("null") ? null : data[3];

                // Products 객체를 만들어 리스트에 추가
                Products product = new Products(name, price, quantity, promotion);
                productsList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productsList;
    }
}
