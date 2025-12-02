// 這個類別放在 model 套件裡面
package com.SAD_Project.model;

// 引入計算星座的工具類別
import com.SAD_Project.util.ZodiacCalculator;

// 存兩個人的資料
// 這個類別用來儲存配對的兩個人基本資料
public class Couple {
    // 第一個人的生日，格式可能是 "3/21" 或 "2024-03-21"
    private String birthDate1;    // 第一個人生日
    // 第一個人的性別，"男" 或 "女"
    private String gender1;       // 第一個人性別
    // 第二個人的生日
    private String birthDate2;    // 第二個人生日
    // 第二個人的性別
    private String gender2;       // 第二個人性別

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

    // 取得第一個人的生日
    public String getBirthDate1() {
        return birthDate1;
    }

    // 設定第一個人的生日，會自動清理資料
    public void setBirthDate1(String birthDate1) {
        this.birthDate1 = clearUpData(birthDate1);
    }

    // 取得第一個人的性別
    public String getGender1() {
        return gender1;
    }

    // 設定第一個人的性別，會自動清理資料
    public void setGender1(String gender1) {
        this.gender1 = clearUpData(gender1);
    }

    // 取得第二個人的生日
    public String getBirthDate2() {
        return birthDate2;
    }

    // 設定第二個人的生日，會自動清理資料
    public void setBirthDate2(String birthDate2) {
        this.birthDate2 = clearUpData(birthDate2);
    }

    // 取得第二個人的性別
    public String getGender2() {
        return gender2;
    }

    // 設定第二個人的性別，會自動清理資料
    public void setGender2(String gender2) {
        this.gender2 = clearUpData(gender2);
    }

    // 根據生日計算第一個人的星座
    // 這個方法會自動根據生日算出星座
    public String getZodiac1() {
        // 呼叫私有方法來計算星座
        return calculateZodiac(birthDate1);
    }

    // 根據生日計算第二個人的星座
    // 這個方法會自動根據生日算出星座
    public String getZodiac2() {
        // 呼叫私有方法來計算星座
        return calculateZodiac(birthDate2);
    }

    // 私有方法，用來計算星座
    // birthDate 參數是生日字串
    private String calculateZodiac(String birthDate) {
        // 如果生日是 null 或空字串，就回傳 null
        if (birthDate == null || birthDate.isEmpty()) {
            return null;
        }
        // 使用工具類別來計算星座
        return ZodiacCalculator.calculateZodiacFromString(birthDate);
    }

    // 檢查字串是否為空白
    // value 是要檢查的字串
    private boolean isBlank(String value) {
        // 如果是 null 或空字串就回傳 true
        return value == null || value.isEmpty();
    }

    // 覆寫 toString 方法，用來顯示兩個人的資訊
    @Override
    public String toString() {
        // 取得第一個人的星座
        String zodiac1 = getZodiac1();
        // 取得第二個人的星座
        String zodiac2 = getZodiac2();
        // 把第一個人的資料轉成字串
        String first = personDataToString(birthDate1, gender1, zodiac1);
        // 把第二個人的資料轉成字串
        String second = personDataToString(birthDate2, gender2, zodiac2);
        // 用 " 與 " 連接兩個人的資訊
        return first + " 與 " + second;
    }

    // 把單一人的資料轉成可顯示的字串
    // birthDate 是生日，gender 是性別，zodiac 是星座
    private String personDataToString(String birthDate, String gender, String zodiac) {
        // 用來顯示性別的字串
        String showGender;
        // 用來顯示星座的字串
        String showZodiac;
        // 用來顯示生日的字串
        String showDate;

        // 如果性別是空白，就不顯示性別
        if(isBlank(gender)){
            showGender = "";
        } else {
            // 否則用 [性別] 的格式顯示
            showGender = " [" + gender + "]";
        }

        // 如果星座是 null，就顯示 "未知星座"
        if(zodiac == null){
            showZodiac = "未知星座";
        } else {
            // 否則顯示實際的星座名稱
            showZodiac = zodiac;
        }

        // 如果生日是 null，就顯示 "未知日期"
        if(birthDate == null){
            showDate = "未知日期";
        } else {
            // 否則顯示實際的生日
            showDate = birthDate;
        }

        // 組合所有資訊：日期 + 性別 + (星座)
        return showDate + showGender + " (" + showZodiac + ")";
    }

    /*clearUpData:清理／淨化輸入資料
    在這個方法中，具體行為是：如果輸入為 null、空字串或僅包含空白，回傳 null；否則回傳去掉前後空白的字串（trim() 的結果）*/
    private String clearUpData(String value) {
        // 如果是空白就回傳 null
        if(isBlank(value)){
            return null;
        } else {
            // 否則回傳原值
            // 注意：這裡可以加上 .trim() 來去掉前後空白
            return value;
        }
    }
}
