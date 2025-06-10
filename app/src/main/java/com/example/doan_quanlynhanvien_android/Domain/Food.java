package com.example.doan_quanlynhanvien_android.Domain;

public class Food {
    private String ma;
    private String tenMon;
    private double danhGia;
    private int gia;
    private String viTri;
    private byte[] anh;

    public Food() {
    }

    public Food(String ma, String tenMon, double danhGia, int gia, String viTri, byte[] anh) {
        this.ma = ma;
        this.tenMon = tenMon;
        this.danhGia = danhGia;
        this.gia = gia;
        this.viTri = viTri;
        this.anh = anh;
    }


    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
