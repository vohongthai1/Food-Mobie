package com.example.doan_quanlynhanvien_android.Adapter;

import com.example.doan_quanlynhanvien_android.Domain.Food;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static Cart instance; // Singleton Instance
    private Map<Food, Integer> items; // Giỏ hàng lưu trữ sản phẩm và số lượng

    // Constructor private để áp dụng Singleton Pattern
    private Cart() {
        items = new HashMap<>();
    }

    // Lấy instance của giỏ hàng
    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addItem(Food food, int quantity) {
        if (food == null || quantity <= 0) {
            return; // Không thêm nếu dữ liệu không hợp lệ
        }

        if (items.containsKey(food)) {
            // Nếu món ăn đã có trong giỏ, tăng số lượng
            items.put(food, items.get(food) + quantity);
        } else {
            // Nếu chưa có, thêm mới vào giỏ
            items.put(food, quantity);
        }
    }

    // Cập nhật số lượng sản phẩm
    public void updateItem(Food food, int quantity) {
        if (food != null && items.containsKey(food)) {
            if (quantity > 0) {
                items.put(food, quantity); // Cập nhật số lượng mới
            } else {
                removeItem(food); // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ
            }
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(Food food) {
        if (food != null && items.containsKey(food)) {
            items.remove(food);
        }
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public Map<Food, Integer> getItems() {
        return items;
    }

    // Tính tổng giá trị đơn hàng
    public int getTotalPrice() {
        int total = 0;
        for (Map.Entry<Food, Integer> entry : items.entrySet()) {
            total += entry.getKey().getGia() * entry.getValue();
        }
        return total;
    }

    // Xóa tất cả sản phẩm trong giỏ hàng
    public void clear() {
        items.clear();
    }
}
