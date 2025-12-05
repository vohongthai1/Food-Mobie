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

## Giao diện ứng dụng

### Màn hình giới thiệu
![Màn hình giới thiệu](app/src/main/res/drawable-hdpi/intro_pic.png)

### Màn hình đăng nhập
![Màn hình đăng nhập](app/src/main/res/drawable-hdpi/orange_background.png)

### Màn hình chính - Menu món ăn
![Menu món ăn](app/src/main/res/drawable-hdpi/btn_1.png)
![Menu món ăn 2](app/src/main/res/drawable-hdpi/btn_2.png)
![Menu món ăn 3](app/src/main/res/drawable-hdpi/btn_3.png)
![Menu món ăn 4](app/src/main/res/drawable-hdpi/btn_4.png)

### Màn hình chi tiết món ăn
![Chi tiết món ăn](app/src/main/res/drawable-hdpi/btn_5.png)
![Chi tiết món ăn 2](app/src/main/res/drawable-hdpi/btn_6.png)

### Màn hình giỏ hàng
![Giỏ hàng](app/src/main/res/drawable-hdpi/cart.png)

### Màn hình thanh toán
![Thanh toán](app/src/main/res/drawable-hdpi/dollar.png)

### Các icon và UI components
- **Logo ứng dụng**: ![Logo](app/src/main/res/drawable-hdpi/logo.png)
- **Giỏ hàng**: ![Giỏ hàng](app/src/main/res/drawable-hdpi/cart.png)
- **Tìm kiếm**: ![Tìm kiếm](app/src/main/res/drawable-hdpi/search_icon.png)
- **Vị trí**: ![Vị trí](app/src/main/res/drawable-hdpi/location.png)
- **Thời gian**: ![Thời gian](app/src/main/res/drawable-hdpi/time.png)
- **Đánh giá**: ![Đánh giá](app/src/main/res/drawable-hdpi/star.png)
- **Yêu thích**: ![Yêu thích](app/src/main/res/drawable-hdpi/favorite_white.png)
- **Mũi tên**: ![Mũi tên](app/src/main/res/drawable-hdpi/arrow.png)
- **Cài đặt**: ![Cài đặt](app/src/main/res/drawable-hdpi/settings.png)
- **Đăng xuất**: ![Đăng xuất](app/src/main/res/drawable-hdpi/logout.png)

### Social Login
- **Facebook**: ![Facebook](app/src/main/res/drawable-hdpi/facebook.png)
- **Google**: ![Google](app/src/main/res/drawable-hdpi/google.png)
- **Twitter**: ![Twitter](app/src/main/res/drawable-hdpi/twitter.png)

## Hỗ trợ
Nếu có bất kỳ vấn đề nào, vui lòng tạo issue trong repository hoặc liên hệ với tác giả.
