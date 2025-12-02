// 這個類別放在 controller 套件裡面
package com.SAD_Project.controller;

// 引入我們自己寫的 Couple 模型類別
import com.SAD_Project.model.Couple;
// 引入星座介紹的服務類別
import com.SAD_Project.service.ZodiacIntroduction;
// 引入計算星座的工具類別
import com.SAD_Project.util.ZodiacCalculator;
// Spring 的自動注入註解，會自動幫我們建立物件
import org.springframework.beans.factory.annotation.Autowired;
// 用來回傳 HTTP 回應的類別
import org.springframework.http.ResponseEntity;
// 標記這是控制器類別
import org.springframework.stereotype.Controller;
// Model 用來傳資料給前端頁面
import org.springframework.ui.Model;
// 引入所有 Spring 的網頁請求相關註解
import org.springframework.web.bind.annotation.*;

// HashMap 用來存鍵值對的資料
import java.util.HashMap;
// Map 介面，HashMap 有實作它
import java.util.Map;

// Web控制器，處理HTTP請求
// 這個類別負責處理所有網頁的請求
@Controller
public class HoroscopeController {

    // 自動注入分析控制器，用來做配對分析
    @Autowired
    private AnalysisController analysisController;

    // 自動注入星座介紹服務，用來取得星座資料
    @Autowired
    private ZodiacIntroduction zodiacIntro;

    // 首頁
    // 當使用者訪問根路徑 / 時會執行這個方法
    @GetMapping("/")
    public String index() {
        // 回傳 "index" 表示要顯示 index.html 這個頁面
        return "index";
    }

    // 配對頁面
    // 當使用者訪問 /match 路徑時會執行這個方法
    @GetMapping("/match")
    public String matchPage() {
        // 回傳 "match" 表示要顯示 match.html 這個頁面
        return "match";
    }

    // 配對API
    // 這個是處理配對請求的 API，用 POST 方法
    // @ResponseBody 表示回傳的是 JSON 資料，不是網頁
    @PostMapping("/api/match")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> doMatch(
            // 第一個人的生日，從請求參數取得
            @RequestParam String birthDate1,
            // 第一個人的性別
            @RequestParam String gender1,
            // 第二個人的生日
            @RequestParam String birthDate2,
            // 第二個人的性別
            @RequestParam String gender2) {

        // 建立一個 Map 來存回傳的資料
        Map<String, Object> response = new HashMap<>();

        // 用 try-catch 來處理可能的錯誤
        try {
            // 只使用 date 和 gender 建立 Couple
            // 建立一個 Couple 物件來存兩個人的資料
            Couple couple = new Couple(birthDate1, gender1, birthDate2, gender2);

            // 分析配對
            // 呼叫分析控制器來做配對分析
            AnalysisController.AnalysisResult analysisResult =
                    analysisController.doAnalysis(couple);

            // 檢查分析是否成功
            if (!analysisResult.isSuccess()) {
                // 如果失敗，設定 success 為 false
                response.put("success", false);
                // 把錯誤訊息放進去
                response.put("error", analysisResult.getError());
            } else {
                // 如果成功，把結果轉成結構化的格式
                Map<String, Object> structuredResult = analysisResult.toStructuredResult();
                // 設定 success 為 true
                response.put("success", true);
                // 把所有的分析結果都放進 response
                response.putAll(structuredResult);
                // 把兩個人的資訊也放進去
                response.put("couple", couple.toString());
            }

        } catch (Exception e) {
            // 如果發生任何例外，就回傳錯誤訊息
            response.put("success", false);
            response.put("error", "分析失敗，請稍後再試: " + e.getMessage());
        }

        // 回傳 HTTP 200 狀態碼和 response 資料
        return ResponseEntity.ok(response);
    }

    // 星座介紹頁面
    // 當使用者訪問 /introduction 路徑時會執行這個方法
    @GetMapping("/introduction")
    public String introductionPage(Model model) {
        // 把所有星座名稱放到 model 裡面，這樣前端頁面就可以用
        model.addAttribute("zodiacs", zodiacIntro.getAllZodiacNames());
        // 回傳 "introduction" 表示要顯示 introduction.html
        return "introduction";
    }

    // 取得星座介紹
    // 這個是 API，用來取得特定星座的介紹資料
    // {zodiac} 是路徑變數，會從 URL 裡面取得
    @GetMapping("/api/introduction/{zodiac}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getIntroduction(@PathVariable String zodiac) {
        // 建立回傳用的 Map
        Map<String, Object> response = new HashMap<>();

        try {
            // 從服務層取得星座介紹
            String introduction = zodiacIntro.getIntroduction(zodiac);
            // 設定成功標記
            response.put("success", true);
            // 把介紹內容放進去
            response.put("introduction", introduction);
        } catch (Exception e) {
            // 如果出錯就設定失敗標記和錯誤訊息
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        // 回傳結果
        return ResponseEntity.ok(response);
    }

    // 個性分析頁面
    // 當使用者訪問 /personality 路徑時會執行這個方法
    @GetMapping("/personality")
    public String personalityPage() {
        // 回傳 "personality" 表示要顯示 personality.html
        return "personality";
    }

    // 取得個性分析
    // 這個 API 用來根據日期和性別取得個性分析
    @PostMapping("/api/personality")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPersonalityByDate(
            // 生日日期，從請求參數取得
            @RequestParam String date,
            // 性別，從請求參數取得
            @RequestParam String gender) {

        // 建立回傳用的 Map
        Map<String, Object> response = new HashMap<>();

        try {
            // 根據日期和性別取得個性分析文字
            String personality = zodiacIntro.getPersonalityByDate(date, gender);
            // 根據日期計算出星座
            String zodiac = ZodiacCalculator.calculateZodiacFromString(date);

            // 設定成功標記
            response.put("success", true);
            // 把個性分析放進去
            response.put("personality", personality);
            // 把星座名稱放進去
            response.put("zodiac", zodiac);
            // 把日期也放進去，方便前端使用
            response.put("date", date);
            // 把性別也放進去
            response.put("gender", gender);

        } catch (Exception e) {
            // 如果出錯就設定失敗標記和錯誤訊息
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        // 回傳結果
        return ResponseEntity.ok(response);
    }

    // 取得所有星座
    // 這個 API 用來取得所有星座的名稱列表
    @GetMapping("/api/zodiacs")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllZodiacs() {
        // 建立回傳用的 Map
        Map<String, Object> response = new HashMap<>();

        try {
            // 取得所有星座名稱的陣列
            String[] zodiacs = zodiacIntro.getAllZodiacNames();
            // 設定成功標記
            response.put("success", true);
            // 把星座陣列放進去
            response.put("zodiacs", zodiacs);
        } catch (Exception e) {
            // 如果出錯就設定失敗標記和錯誤訊息
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        // 回傳結果
        return ResponseEntity.ok(response);
    }
}

