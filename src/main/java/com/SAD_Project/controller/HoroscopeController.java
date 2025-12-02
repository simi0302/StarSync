package com.SAD_Project.controller;

import com.SAD_Project.model.Couple;
import com.SAD_Project.service.ZodiacIntroduction;
import com.SAD_Project.util.ZodiacCalculator;

import org.springframework.beans.factory.annotation.Autowired; // Spring 的自動注入註解，會自動幫我們建立物件
import org.springframework.http.ResponseEntity; // 用來回傳 HTTP 回應的類別
import org.springframework.stereotype.Controller; // 標記這是控制器類別
import org.springframework.ui.Model; // Model 用來傳資料給前端頁面
import org.springframework.web.bind.annotation.*; // 引入所有 Spring 的網頁請求相關註解

import java.util.HashMap;
import java.util.Map;

// 這個類別負責處理所有http的請求
@Controller
public class HoroscopeController {

    // 自動注入分析控制器，用來做配對分析
    @Autowired
    private AnalysisController analysisController;

    // 自動注入星座介紹服務，用來取得星座資料
    @Autowired
    private ZodiacIntroduction zodiacIntro;

    // 首頁, 當使用者訪問根路徑時會執行這個方法
    @GetMapping("/")
    public String index() {
        return "index"; // 回傳 "index" 表示要顯示 index.html 這個頁面
    }

    // 配對頁面, 當使用者訪問 /match 路徑時會執行這個方法
    @GetMapping("/match")
    public String matchPage() {
        return "match"; // 回傳 "match" 表示要顯示 match.html 這個頁面
    }

    // 這個是處理配對請求的 API，用 POST 方法
    // @ResponseBody 表示回傳的是 JSON 資料，不是網頁
    @PostMapping("/api/match")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> doMatch(
            // 生日性別，從請求參數取得
            @RequestParam String birthDate1,
            @RequestParam String gender1,
            @RequestParam String birthDate2,
            @RequestParam String gender2) {

        Map<String, Object> response = new HashMap<>(); // 建立一個 Map 來存回傳的資料

        try {
            Couple couple = new Couple(birthDate1, gender1, birthDate2, gender2); // 建立一個 Couple 物件來存兩個人的資料

            // 呼叫AnalysisController來做配對分析
            AnalysisController.AnalysisResult analysisResult =
                    analysisController.doAnalysis(couple);

            // 檢查分析是否成功
            if (!analysisResult.isSuccess()) {
                response.put("success", false);
                response.put("error", analysisResult.getError());
            } else {
                Map<String, Object> structuredResult = analysisResult.toStructuredResult(); // 如果成功，把結果轉成結構化的格式
                response.put("success", true);
                response.putAll(structuredResult);

                response.put("couple", couple.toString()); // 把兩個人的資訊放進去
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "分析失敗，請稍後再試: " + e.getMessage());
        }

        return ResponseEntity.ok(response); // 回傳 HTTP 200 狀態碼和 response 資料
    }

    // 星座介紹頁面
    // 當使用者訪問 /introduction 路徑時會執行這個方法
    @GetMapping("/introduction")
    public String introductionPage(Model model) {
        model.addAttribute("zodiacs", zodiacIntro.getAllZodiacNames()); // 把所有星座名稱放到 model 裡面，這樣前端頁面就可以用
        return "introduction"; // 回傳 "introduction" 表示要顯示 introduction.html
    }

    // 這個是API, 用來取得特定星座的介紹資料
    // {zodiac} 是路徑變數，會從 URL 裡面取得
    @GetMapping("/api/introduction/{zodiac}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getIntroduction(@PathVariable String zodiac) {

        Map<String, Object> response = new HashMap<>();  // 建立回傳用的 Map

        try {
            String introduction = zodiacIntro.getIntroduction(zodiac);

            //若成功就設定成功標記和相關資料
            response.put("success", true);
            response.put("introduction", introduction);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    // 個性分析頁面
    // 當使用者訪問 /personality 路徑時會執行這個方法
    @GetMapping("/personality")
    public String personalityPage() {
        return "personality"; // 回傳 "personality" 表示要顯示 personality.html
    }

    // 這個 API 用來根據日期和性別取得個性分析
    @PostMapping("/api/personality")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPersonalityByDate(
            // 生日日期，性別從請求參數取得
            @RequestParam String date,
            @RequestParam String gender) {

        Map<String, Object> response = new HashMap<>();  // 建立回傳用的 Map

        try {
            String personality = zodiacIntro.getPersonalityByDate(date, gender); // 根據日期和性別取得個性分析文字
            String zodiac = ZodiacCalculator.calculateZodiacFromString(date);  // 根據日期計算出星座

            //若成功就設定成功標記和相關資料
            response.put("success", true);
            response.put("personality", personality);
            response.put("zodiac", zodiac);
            response.put("date", date);
            response.put("gender", gender);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    // 取得所有星座
    // 這個 API 用來取得所有星座的名稱列表
    @GetMapping("/api/zodiacs")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllZodiacs() {

        Map<String, Object> response = new HashMap<>(); // 建立回傳用的 Map

        try {
            String[] zodiacs = zodiacIntro.getAllZodiacNames();

            //若成功就設定成功標記和相關資料
            response.put("success", true);
            response.put("zodiacs", zodiacs);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}

