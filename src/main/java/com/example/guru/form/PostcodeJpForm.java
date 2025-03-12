package com.example.guru.form;

import java.util.List;

public class PostcodeJpForm {
    private List<AddressData> data;

    // デフォルトコンストラクタ（Jackson用）
    public PostcodeJpForm() {}

    // ゲッターとセッター
    public List<AddressData> getData() {
        return data;
    }

    public void setData(List<AddressData> data) {
        this.data = data;
    }

    public static class AddressData {
        private String prefCode;
        private String cityCode;
        private String postcode;
        private String oldPostcode;
        private String pref;
        private String city;
        private String town;
        private String allAddress;
        private Hiragana hiragana;
        private HalfWidthKana halfWidthKana;
        private FullWidthKana fullWidthKana;
        private English english;
        private boolean generalPostcode;
        private boolean officePostcode;
        private Location location;

        // デフォルトコンストラクタ（Jackson用）
        public AddressData() {}

        // ゲッターとセッター
        public String getPrefCode() { return prefCode; }
        public void setPrefCode(String prefCode) { this.prefCode = prefCode; }
        public String getCityCode() { return cityCode; }
        public void setCityCode(String cityCode) { this.cityCode = cityCode; }
        public String getPostcode() { return postcode; }
        public void setPostcode(String postcode) { this.postcode = postcode; }
        public String getOldPostcode() { return oldPostcode; }
        public void setOldPostcode(String oldPostcode) { this.oldPostcode = oldPostcode; }
        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
        public Hiragana getHiragana() { return hiragana; }
        public void setHiragana(Hiragana hiragana) { this.hiragana = hiragana; }
        public HalfWidthKana getHalfWidthKana() { return halfWidthKana; }
        public void setHalfWidthKana(HalfWidthKana halfWidthKana) { this.halfWidthKana = halfWidthKana; }
        public FullWidthKana getFullWidthKana() { return fullWidthKana; }
        public void setFullWidthKana(FullWidthKana fullWidthKana) { this.fullWidthKana = fullWidthKana; }
        public English getEnglish() { return english; }
        public void setEnglish(English english) { this.english = english; }
        public boolean isGeneralPostcode() { return generalPostcode; }
        public void setGeneralPostcode(boolean generalPostcode) { this.generalPostcode = generalPostcode; }
        public boolean isOfficePostcode() { return officePostcode; }
        public void setOfficePostcode(boolean officePostcode) { this.officePostcode = officePostcode; }
        public Location getLocation() { return location; }
        public void setLocation(Location location) { this.location = location; }
    }

    public static class Hiragana {
        private String pref;
        private String city;
        private String town;
        private String allAddress;

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    public static class HalfWidthKana {
        private String pref;
        private String city;
        private String town;
        private String allAddress;

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    public static class FullWidthKana {
        private String pref;
        private String city;
        private String town;
        private String allAddress;

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    public static class English {
        private String pref;
        private String city;
        private String town;
        private String allAddress;

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    public static class Location {
        private double latitude;
        private double longitude;

        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
}