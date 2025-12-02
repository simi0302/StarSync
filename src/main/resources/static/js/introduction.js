const resultPanel = document.getElementById('result');
const zodiacCards = document.querySelectorAll('.zodiac-card');

zodiacCards.forEach(card => {
    card.addEventListener('click', () => {
        const zodiac = card.dataset.zodiac;
        setActiveCard(card);
        loadIntroduction(zodiac);
    });
});

function setActiveCard(activeCard) {
    zodiacCards.forEach(card => card.classList.remove('active'));
    if (activeCard) {
        activeCard.classList.add('active');
    }
}

function showLoading() {
    resultPanel.removeAttribute('data-empty');
    resultPanel.innerHTML = `
        <div class="detail-placeholder">
            <p>資料載入中...</p>
        </div>
    `;
}

function renderIntroduction(zodiac, content) {
    resultPanel.removeAttribute('data-empty');
    resultPanel.innerHTML = '';

    const wrapper = document.createElement('div');
    wrapper.className = 'detail-content';

    const title = document.createElement('h3');
    title.textContent = `${zodiac} 星座介紹`;
    title.className = 'detail-title';

    const meta = document.createElement('div');
    meta.className = 'detail-meta';
    meta.innerHTML = '<span>資料來源：1214.org</span>';

    const sections = content.split('===DELIM===');
    const description = sections[0] || '';
    const male = sections[1] || '';
    const female = sections[2] || '';

    const descriptionBlock = document.createElement('div');
    descriptionBlock.className = 'detail-section';
    descriptionBlock.innerHTML = `
        <h4>基本介紹</h4>
        <p class="detail-body">${description}</p>
    `;

    const personalityWrapper = document.createElement('div');
    personalityWrapper.className = 'detail-personality';
    personalityWrapper.innerHTML = `
        <div>
            <h4>男生特質</h4>
            <p class="detail-body">${male.replace(/\n/g, '<br>')}</p>
        </div>
        <div>
            <h4>女生特質</h4>
            <p class="detail-body">${female.replace(/\n/g, '<br>')}</p>
        </div>
    `;

    wrapper.appendChild(title);
    wrapper.appendChild(meta);
    wrapper.appendChild(descriptionBlock);
    wrapper.appendChild(personalityWrapper);
    resultPanel.appendChild(wrapper);
}

async function loadIntroduction(zodiac) {
    try {
        showLoading();
        const response = await fetch('/api/introduction/' + encodeURIComponent(zodiac));
        const data = await response.json();

        if (data.success) {
            renderIntroduction(zodiac, data.introduction);
        } else {
            alert('錯誤：' + data.error);
            resultPanel.setAttribute('data-empty', '');
            resultPanel.innerHTML = '';
        }
    } catch (error) {
        alert('發生錯誤：' + error.message);
        resultPanel.setAttribute('data-empty', '');
        resultPanel.innerHTML = '';
    }
}
