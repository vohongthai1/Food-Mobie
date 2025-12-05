# Food App - Ứng dụng Đặt món ăn

## Giới thiệu
Food App là một ứng dụng di động Android cho phép người dùng đặt món ăn trực tuyến. Ứng dụng được phát triển bằng Java với Android Studio.

## Tính năng
- **Đăng nhập/Đăng ký**: Người dùng có thể tạo tài khoản và đăng nhập vào hệ thống
- **Xem danh sách món ăn**: Duyệt qua các món ăn có sẵn trong menu
- **Đặt món ăn**: Chọn và thêm món ăn vào giỏ hàng
- **Thanh toán**: Thực hiện thanh toán cho đơn hàng
- **Giao diện thân thiện**: Thiết kế Material Design dễ sử dụng

## Yêu cầu kỹ thuật
- Android Studio 4.0+
- Android API Level 28 trở lên
- Java 8
- Gradle 7.0+

## Cài đặt
1. Clone repository này về máy:
   ```bash
   git clone https://github.com/your-username/Food-Mobie.git
   ```

2. Mở project trong Android Studio

3. Đồng bộ Gradle:
   - Vào `File` → `Sync Project with Gradle Files`

4. Chạy ứng dụng:
   - Chọn thiết bị Android hoặc emulator
   - Nhấn `Run` (Shift + F10)

## Cấu trúc project
```
Food-Mobie/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/doan_quanlynhanvien_android/
│   │   │   │   ├── MainActivity/
│   │   │   │   │   ├── HelloActivity.java
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   ├── SignupActivity.java
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── DanhSachFood.java
│   │   │   │   │   ├── Sub_Activity.java
│   │   │   │   │   └── ThanhToanActivity.java
│   │   │   │   └── ...
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   └── ...
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Các Activity chính
- **HelloActivity**: Màn hình chào mừng
- **LoginActivity**: Màn hình đăng nhập
- **SignupActivity**: Màn hình đăng ký tài khoản
- **MainActivity**: Màn hình chính
- **DanhSachFood**: Màn hình danh sách món ăn
- **Sub_Activity**: Màn hình chi tiết món ăn
- **ThanhToanActivity**: Màn hình thanh toán

## Thư viện sử dụng
- AndroidX AppCompat
- Material Design Components
- ConstraintLayout
- JUnit (cho testing)
- Espresso (cho UI testing)

## Tác giả
- **Võ Hồng Thái** - 2763_VOHONGTHAI_FoodApp_CR424AM

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Hỗ trợ
Nếu có bất kỳ vấn đề nào, vui lòng tạo issue trong repository hoặc liên hệ với tác giả.
