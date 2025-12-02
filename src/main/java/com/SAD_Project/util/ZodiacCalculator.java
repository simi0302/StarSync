// 這個類別放在 util 套件裡面
package com.SAD_Project.util;

// 引入日期相關的類別（雖然現在沒用到，但可能之後會用到）
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// 算星座用的工具
// 這個類別提供計算星座的功能
// 可以根據生日日期來判斷是哪個星座
public class ZodiacCalculator {

    // 從字串算星座，包含解析日期與判斷星座的所有邏輯
    // birthDate 是生日字串，格式可以是 "3/21" 或 "2024-03-21" 等
    // 這個是靜態方法，可以直接用類別名稱呼叫，不需要建立物件
    public static String calculateZodiacFromString(String birthDate) {
        // 用來存月份
        int month = 0;
        // 用來存日期
        int day = 0;

        // 用 try-catch 來處理可能的錯誤
        try { 
            // 嘗試手動分割解析 (處理如 3/21 這種簡單格式)
            // 用 "/" 或 "-" 來分割字串
            String[] parts = birthDate.split("[/-]");
            // 如果分割後有兩個部分，就表示格式正確
            if (parts.length == 2) {
                // 第一個部分是月份，轉成整數
                month = Integer.parseInt(parts[0].trim());
                // 第二個部分是日期，轉成整數
                day = Integer.parseInt(parts[1].trim());
            } else {
                // 如果格式不對就回傳 null
                return null; // 無法解析日期
            }

            // 根據 month 和 day 判斷星座
            // 下面用 if-else 來判斷是哪個星座
            // 每個星座都有特定的日期範圍
            
            // 牡羊座：3/21 - 4/19
            if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
                return "牡羊座";
            } 
            // 金牛座：4/20 - 5/20
            else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
                return "金牛座";
            } 
            // 雙子座：5/21 - 6/21
            else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
                return "雙子座";
            } 
            // 巨蟹座：6/22 - 7/22
            else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
                return "巨蟹座";
            } 
            // 獅子座：7/23 - 8/22
            else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
                return "獅子座";
            } 
            // 處女座：8/23 - 9/22
            else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
                return "處女座";
            } 
            // 天秤座：9/23 - 10/22
            else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
                return "天秤座";
            } 
            // 天蠍座：10/23 - 11/21
            else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
                return "天蠍座";
            } 
            // 射手座：11/22 - 12/21
            else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
                return "射手座";
            } 
            // 魔羯座：12/22 - 1/19（跨年）
            else if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
                return "魔羯座";
            } 
            // 水瓶座：1/20 - 2/18
            else if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
                return "水瓶座";
            } 
            // 雙魚座：2/19 - 3/20
            else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
                return "雙魚座";
            }

            // 如果都不符合就回傳 "未知"
            return "未知";

        } catch (Exception e) {
            // 如果發生任何錯誤（例如數字格式錯誤），就回傳 null
            return null;
        }
    }
}

