// 個性分析功能
document.getElementById('personalityForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const date = document.getElementById('date').value.trim();
    const gender = document.getElementById('gender').value;
    
    if (!date || !gender) {
        alert('請填寫完整資訊！');
        return;
    }
    
    const formData = new URLSearchParams();
    formData.append('date', date);
    formData.append('gender', gender);
    
    try {
        const response = await fetch('/api/personality', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: formData
        });
        
        const data = await response.json();
        const resultDiv = document.getElementById('result');
        
        if (data.success) {
            resultDiv.style.display = 'block';
            resultDiv.innerHTML = '<pre>' + data.personality + '</pre>';
            resultDiv.scrollIntoView({ behavior: 'smooth' });
        } else {
            alert('錯誤：' + data.error);
        }
    } catch (error) {
        alert('發生錯誤：' + error.message);
    }
});

