# ***StarSync 星座配對系統***


## *專案環境需求 Project Environmental Requirements*
- Java JDK 11或以上 
- Maven 3.6或以上


## *如何執行 How to Run*

- ### *使用 IDE 執行*
  1.  開啟專案
  2. 找到`src/main/java/com/example/HoroscopeApplication.java`
  3. 右鍵點擊該檔案並按'Run HoroscopeApplication'

## *存取*
- 打開瀏覽器並前往 `http://localhost:8080`


## *專案架構 Project Structure*
  - `src/main/java` : Java原始碼
    - `com.SAD_Project` : 主應用程式
      - `controller` : 處理HTTP請求
      - `service` : 核心邏輯類別
      - `model` : 資料模型類別
      - `HoroscopeApplication.java` : 主應用程式啟動類別
  - `src/main/resources` : 靜態資源與設定檔
    - `templates` : Thymeleaf HTML 頁面
    - `static` : CSS 樣式表與 JavaScript 檔案
  
