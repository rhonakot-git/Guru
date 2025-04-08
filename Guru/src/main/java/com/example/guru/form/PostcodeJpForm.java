package com.example.guru.form;

import java.util.List;

/**
 * PostcodeJp APIフォームクラス。
 * PostcodeJp　APIから取得した住所データを格納するためのフォームクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class PostcodeJpForm {

    /**
     * 住所データのリスト。
     * 各要素は郵便番号に対応する住所の詳細情報を含みます。
     */
    private List<AddressData> data;

    /**
     * デフォルトコンストラクタ。
     * JacksonによるJSONデシリアライズ用に使用されます。
     */
    public PostcodeJpForm() {}

    /**
     * 住所データのリストを取得します。
     * 
     * @return 住所データリスト
     */
    public List<AddressData> getData() {
        return data;
    }

    /**
     * 住所データのリストを設定します。
     * 
     * @param data 住所データリスト
     */
    public void setData(List<AddressData> data) {
        this.data = data;
    }

    /**
     * 住所の詳細情報を格納する内部クラス。
     * 都道府県、市区町村、町域などの住所情報を保持します。
     */
    public static class AddressData {

        /**
         * 都道府県コード。
         * 例: "13"（東京都）
         */
        private String prefCode;

        /**
         * 市区町村コード。
         * 例: "13101"（千代田区）
         */
        private String cityCode;

        /**
         * 郵便番号。
         * 例: "1000001"
         */
        private String postcode;

        /**
         * 旧郵便番号。
         * 例: "100"
         */
        private String oldPostcode;

        /**
         * 都道府県名。
         * 例: "東京都"
         */
        private String pref;

        /**
         * 市区町村名。
         * 例: "千代田区"
         */
        private String city;

        /**
         * 町域名。
         * 例: "千代田"
         */
        private String town;

        /**
         * 完全な住所。
         * 都道府県、市区町村、町域を連結した住所。
         */
        private String allAddress;

        /**
         * ひらがな表記の住所情報。
         */
        private Hiragana hiragana;

        /**
         * 半角カナ表記の住所情報。
         */
        private HalfWidthKana halfWidthKana;

        /**
         * 全角カナ表記の住所情報。
         */
        private FullWidthKana fullWidthKana;

        /**
         * 英語表記の住所情報。
         */
        private English english;

        /**
         * 一般郵便番号フラグ。
         * true の場合、一般的な郵便番号。
         */
        private boolean generalPostcode;

        /**
         * 事業所郵便番号フラグ。
         * true の場合、事業所用の郵便番号。
         */
        private boolean officePostcode;

        /**
         * 位置情報（緯度・経度）。
         */
        private Location location;

        /**
         * デフォルトコンストラクタ。
         * JacksonによるJSONデシリアライズ用に使用されます。
         */
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

    /**
     * ひらがな表記の住所情報を格納する内部クラス。
     */
    public static class Hiragana {
        private String pref;       // 都道府県名（ひらがな）
        private String city;       // 市区町村名（ひらがな）
        private String town;       // 町域名（ひらがな）
        private String allAddress; // 完全な住所（ひらがな）

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    /**
     * 半角カナ表記の住所情報を格納する内部クラス。
     */
    public static class HalfWidthKana {
        private String pref;       // 都道府県名（半角カナ）
        private String city;       // 市区町村名（半角カナ）
        private String town;       // 町域名（半角カナ）
        private String allAddress; // 完全な住所（半角カナ）

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    /**
     * 全角カナ表記の住所情報を格納する内部クラス。
     */
    public static class FullWidthKana {
        private String pref;       // 都道府県名（全角カナ）
        private String city;       // 市区町村名（全角カナ）
        private String town;       // 町域名（全角カナ）
        private String allAddress; // 完全な住所（全角カナ）

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    /**
     * 英語表記の住所情報を格納する内部クラス。
     */
    public static class English {
        private String pref;       // 都道府県名（英語）
        private String city;       // 市区町村名（英語）
        private String town;       // 町域名（英語）
        private String allAddress; // 完全な住所（英語）

        public String getPref() { return pref; }
        public void setPref(String pref) { this.pref = pref; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTown() { return town; }
        public void setTown(String town) { this.town = town; }
        public String getAllAddress() { return allAddress; }
        public void setAllAddress(String allAddress) { this.allAddress = allAddress; }
    }

    /**
     * 位置情報（緯度・経度）を格納する内部クラス。
     */
    public static class Location {
        private double latitude;  // 緯度
        private double longitude; // 経度

        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
}