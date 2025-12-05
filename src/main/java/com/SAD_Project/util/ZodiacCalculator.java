package com.SAD_Project.util;

// 這個class計算星座
public class ZodiacCalculator {

    // 從字串算星座，包含解析日期與判斷星座的所有邏輯
    // 這個是靜態方法，可以直接用類別名稱呼叫，不需要建立物件
    public static String calculateZodiacFromBirthDate(String birthDate) {
        int month;
        int day;

        try { 
            // 嘗試手動分割解析 (處理如 3/21 這種簡單格式)
            // 用 "/" 或 "-" 來分割字串
            String[] parts = birthDate.split("[/-]");
            // 如果分割後有兩個部分，就表示格式正確
            if (parts.length == 2) {
                month = Integer.parseInt(parts[0].trim()); // 第一個部分是月份,轉成整數
                day = Integer.parseInt(parts[1].trim()); // 第二個部分是日期,轉成整數
            } else {
                return null; //格式不對就回傳 null
            }

            if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
                return "牡羊座";
            }
            else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
                return "金牛座";
            }
            else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
                return "雙子座";
            }
            else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
                return "巨蟹座";
            }
            else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
                return "獅子座";
            }
            else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
                return "處女座";
            }
            else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
                return "天秤座";
            }
            else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
                return "天蠍座";
            }
            else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
                return "射手座";
            }
            else if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
                return "魔羯座";
            }
            else if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
                return "水瓶座";
            }
            else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
                return "雙魚座";
            }

            return "未知";

        } catch (Exception e) {
            return null;
        }
    }
}

