// 星座配對功能 - StarSync 設計
document.getElementById('matchForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // 獲取輸入值 (月、日分開獲取)
    const yourMonth = document.getElementById('yourMonth').value;
    const yourDay = document.getElementById('yourDay').value;
    const yourGender = document.getElementById('yourGender').value;

    const theirMonth = document.getElementById('theirMonth').value;
    const theirDay = document.getElementById('theirDay').value;
    const theirGender = document.getElementById('theirGender').value;

    // 基本檢查
    if (!yourMonth || !yourDay || !yourGender || !theirMonth || !theirDay || !theirGender) {
        alert('請填寫完整資料！');
        return;
    }

    if (yourGender === theirGender) {
        alert("目前僅支援一男一女分析，請選擇不同性別！");
        return;
    }

    // 時間驗證邏輯
    if (!isValidDate(yourMonth, yourDay)) {
        alert(`你的生日日期不正確：${yourMonth}月${yourDay}日`);
        return;
    }
    if (!isValidDate(theirMonth, theirDay)) {
        alert(`對方的生日日期不正確：${theirMonth}月${theirDay}日`);
        return;
    }

    // 組合成後端需要的日期字串格式 (MM/dd)
    const yourDate = `${yourMonth}/${yourDay}`;
    const theirDate = `${theirMonth}/${theirDay}`;

    try {
        const formData = new URLSearchParams();
        formData.append('birthDate1', yourDate);
        formData.append('gender1', yourGender);
        formData.append('birthDate2', theirDate);
        formData.append('gender2', theirGender);

        const response = await fetch('/api/match', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: formData
        });

        if (!response.ok) {
            throw new Error(`伺服器回應錯誤: ${response.status}`);
        }

        const data = await response.json();
        const resultDiv = document.getElementById('result');

        if (data.success) {
            resultDiv.style.display = 'block';
            resultDiv.innerHTML = generateResultHTML(data);
            resultDiv.scrollIntoView({ behavior: 'smooth' });
        } else {
            alert('錯誤：' + data.error);
        }
    } catch (error) {
        alert('發生錯誤：' + error.message);
    }
});

// 驗證日期是否合法
function isValidDate(monthStr, dayStr) {
    const month = parseInt(monthStr, 10);
    const day = parseInt(dayStr, 10);

    if (isNaN(month) || isNaN(day)) return false;

    // 驗證月份 1-12
    if (month < 1 || month > 12) return false;

    // 驗證日期 1-31，並檢查該月份的最大天數
    // 使用 2024 (閏年) 來允許 2/29，因為星座計算通常不看年份
    const daysInMonth = new Date(2024, month, 0).getDate();

    if (day < 1 || day > daysInMonth) {
        return false;
    }

    return true;
}

// 生成結果頁面 HTML
function generateResultHTML(data) {
    const stars = data.stars || 0;
    const score = data.score || 0;
    const positive = data.positive || '';
    const negative = data.negative || '';
    const comment = data.comment || '';

    // 生成星級顯示
    let starsHTML = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= stars) {
            starsHTML += '<span class="star-filled">★</span>';
        } else {
            starsHTML += '<span class="star-outline">★</span>';
        }
    }

    return `
        <div class="match-result-page">
            <div class="result-header">
                <div class="percentage-bar">
                    <span class="percentage-label">Percentage 適配度: ${score}%</span>
                    <div class="stars-rating">${starsHTML}</div>
                </div>
            </div>
            
            <div class="result-sections">
                <div class="result-section section-positive">
                    <h3 class="section-title">Positive 優點</h3>
                    <div class="section-content">${positive.replace(/\n/g, '<br>')}</div>
                </div>
                
                <div class="result-section section-negative">
                    <h3 class="section-title">Negative 缺點</h3>
                    <div class="section-content">${negative.replace(/\n/g, '<br>')}</div>
                </div>
            </div>
            
            <div class="result-section section-comment">
                <h3 class="section-title">Comment 評語</h3>
                <div class="section-content">${comment.replace(/\n/g, '<br>')}</div>
            </div>
            
            <div class="result-footer">
                <a href="/" class="home-btn">
                    <span>Home</span>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                    </svg>
                </a>
            </div>
        </div>
    `;
}

