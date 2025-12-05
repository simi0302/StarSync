package com.SAD_Project.model;

import com.SAD_Project.util.ZodiacCalculator; // 引入計算星座的工具類別

// 這個class用來儲存配對的兩個人基本資料
public class Couple {
    private String birthDate1;
    private String gender1;
    private String birthDate2;
    private String gender2;

    // 無參數建構子，建立空的 Couple 物件
    public Couple() {
    }

    // 有參數建構子，建立時就設定兩個人的資料
    public Couple(String birthDate1, String gender1, String birthDate2, String gender2) {
        // 使用 clearUpData 方法清理資料，去掉空白等
        this.birthDate1 = clearUpData(birthDate1);
        this.gender1 = clearUpData(gender1);
        this.birthDate2 = clearUpData(birthDate2);
        this.gender2 = clearUpData(gender2);
    }

    //getter 和 setter 方法
    public String getBirthDate1() {
        return birthDate1;
    }
    public void setBirthDate1(String birthDate1) {
        this.birthDate1 = clearUpData(birthDate1);
    }
    public String getGender1() {
        return gender1;
    }
    public void setGender1(String gender1) {
        this.gender1 = clearUpData(gender1);
    }
    public String getBirthDate2() {
        return birthDate2;
    }
    public void setBirthDate2(String birthDate2) {
        this.birthDate2 = clearUpData(birthDate2);
    }
    public String getGender2() {
        return gender2;
    }
    public void setGender2(String gender2) {
        this.gender2 = clearUpData(gender2);
    }

    // 根據生日計算第一個人的星座, 呼叫私有方法
    public String getZodiac1() {
        return calculateZodiac(birthDate1);
    }

    // 根據生日計算第二個人的星座, 呼叫私有方法
    public String getZodiac2() {
        return calculateZodiac(birthDate2);
    }

    // 私有方法，用來計算星座
    private String calculateZodiac(String birthDate) {
        if (birthDate == null || birthDate.isEmpty()) {
            return null;
        }

        return ZodiacCalculator.calculateZodiacFromBirthDate(birthDate); // 使用工具類別來計算星座
    }

    // 檢查字串value是否為空白
    private boolean isBlank(String value) {
        return value == null || value.isEmpty(); // 如果是 null 或空字串就回傳 true
    }

    // 覆寫 toString 方法，用來顯示兩個人的資訊
    @Override
    public String toString() {
        String zodiac1 = getZodiac1();
        String zodiac2 = getZodiac2();

        // 把資料轉成字串
        String first = personDataToString(birthDate1, gender1, zodiac1);
        String second = personDataToString(birthDate2, gender2, zodiac2);

        return first + " 與 " + second;
    }

    // 把單一人的資料轉成可顯示的字串
    private String personDataToString(String birthDate, String gender, String zodiac) {
        String showGender;
        String showZodiac;
        String showDate;

        if(isBlank(gender)){
            showGender = "";
        } else {
            showGender = " [" + gender + "]";
        }

        if(zodiac == null){
            showZodiac = "未知星座";
        } else {
            showZodiac = zodiac;
        }

        if(birthDate == null){
            showDate = "未知日期";
        } else {
            showDate = birthDate;
        }

        return showDate + showGender + " (" + showZodiac + ")";
    }

    //在這個方法中，具體行為是：如果輸入為 null、空字串或僅包含空白，回傳 null；否則回傳去掉前後空白的字串（trim() 的結果）
    private String clearUpData(String value) {
        if(isBlank(value)){
            return null;
        } else {
            return value.trim();
        }
    }
}
