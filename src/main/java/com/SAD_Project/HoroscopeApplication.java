// 這個是專案的套件名稱
package com.SAD_Project;

// 引入 Spring Boot 的啟動類別
import org.springframework.boot.SpringApplication;
// 引入 Spring Boot 的自動配置註解
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot啟動類
// 這個類別是整個專案的入口點
@SpringBootApplication
public class HoroscopeApplication {
    // main 方法是程式開始執行的地方
    // args 是命令列參數，雖然我們這裡沒用到
    public static void main(String[] args) {
        // 這行會啟動整個 Spring Boot 應用程式
        // 傳入這個類別和命令列參數
        SpringApplication.run(HoroscopeApplication.class, args);
    }
}

