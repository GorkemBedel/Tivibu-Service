function handleError(response) {
    if (response.status === 403) {
        alert('Erişim yasaklandı! Lütfen yetkilerinizin yeterli olduğundan emin olun.');
    } else {
        response.json().then(data => {
            alert('Hata: ' + data.message);  // JSON içindeki "message" alanını ekrana yazdırıyoruz
        }).catch(error => {
            console.error('JSON parse hatası:', error);
            alert('Bir hata oluştu.');
        });
    }
}


let currentTestId = null; // Geçerli test ID'sini saklamak için

document.getElementById("getResultsButton").onclick = function() {
    const testType = document.getElementById("testType").value;
    const url = `http://localhost:8083/v1/test/getTestsByType/${testType}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                handleError(response); // Hata durumunu burada yönet
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // JSON verisini konsola yazdır
            if (Array.isArray(data)) {
                displayResults(data);
            } else {
                console.error('Beklenen veri dizisi gelmedi:', data);
                alert('Veri alınamadı.');
            }
        })
        .catch(error => {
            console.error('Fetch işleminde hata:', error);
            alert('Hata: ' + error.message); // Kullanıcıya hata mesajı göster
        });
};

function getTesterId(url) {
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                handleError(response); // Hata durumunu burada yönet
            }
            return response.json(); // JSON formatında döndür
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}

function displayResults(data) {
    const tableBody = document.getElementById("resultsTable").querySelector("tbody");
    tableBody.innerHTML = ""; // Önceki verileri temizle

    // Her bir test sonucu için tablo satırı oluştur
    data.forEach(test => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${test.id}</td>
            <td>${test.testName}</td>
            <td>
                ${test.subTests.length > 0
                ? test.subTests.map(subTest => `${subTest} <br>`).join('')
                : 'none'}
            </td>
            <td>
                ${test.subTests.length > 0
                ? `<button onclick="openResultEntryWithSubTests('${test.id}', ${test.subTests.length})">Sonuç Girişi</button>`
                : `<button onclick="openResultEntryWithoutSubTests('${test.id}')">Sonuç Girişi</button>`}
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function openResultEntryWithSubTests(testId, subTestNumber) {
    const resultEntryArea = document.getElementById('resultEntryArea');
    resultEntryArea.style.display = 'block';

    const subTestResultsContainer = document.getElementById('subTestResultsContainer');
    subTestResultsContainer.innerHTML = '';

    const urlForGetTesterIdRequest = 'http://localhost:8083/v1/tester/getTesterId';
    let testerId = null; // Tester ID için değişken tanımlıyoruz

    // Tester ID'yi al
    getTesterId(urlForGetTesterIdRequest).then(data => {
        testerId = data; // testerId'yi güncelle
        console.log('Tester ID:', testerId); // testerId'yi konsola yazdır
    }).catch(error => {
        console.error('Tester ID Hatası:', error); // Hata durumunda hata mesajı yazdır
    });

    // Sub testlerin oluşturulması
    for (let i = 1; i <= subTestNumber; i++) {
        const subTestDiv = document.createElement('div');
        subTestDiv.classList.add('sub-test');

        subTestDiv.innerHTML = `
            <h4>Sub Test ${i}</h4>
            <label for="subTest${i}v1Result">v1 Sonucu:</label>
            <select id="subTest${i}v1Result" required>
                <option value="">Seçiniz</option>
                <option value="doğru">Doğru</option>
                <option value="yanlış">Yanlış</option>
            </select>
            <label for="subTest${i}v1Comment">v1 Yorumu:</label>
            <input type="text" id="subTest${i}v1Comment" placeholder="Yorum girin" required /><br>
            <label for="subTest${i}v2Result">v2 Sonucu:</label>
            <select id="subTest${i}v2Result" required>
                <option value="">Seçiniz</option>
                <option value="doğru">Doğru</option>
                <option value="yanlış">Yanlış</option>
            </select>
            <label for="subTest${i}v2Comment">v2 Yorumu:</label>
            <input type="text" id="subTest${i}v2Comment" placeholder="Yorum girin" required /><br><br>
        `;
        subTestResultsContainer.appendChild(subTestDiv);
    }

    // Sonucu kaydetme butonuna tıklama olayını yönetme
    document.getElementById('submitResultButton').onclick = function() {
        const deviceId = document.getElementById('deviceId').value; // Kullanıcıdan cihaz ID'sini al
        if (!testerId) {
            alert('Tester ID alınamadı. Lütfen tekrar deneyin.'); // Tester ID yoksa uyarı ver
            return;
        }

        // Sonuçları hazırlama
        const subTestResults = [];
        for (let i = 1; i <= subTestNumber; i++) {
            const v1Result = document.getElementById(`subTest${i}v1Result`).value;
            const v1Comment = document.getElementById(`subTest${i}v1Comment`).value;
            const v2Result = document.getElementById(`subTest${i}v2Result`).value;
            const v2Comment = document.getElementById(`subTest${i}v2Comment`).value;

            subTestResults.push({
                v1_isOk: v1Result === "doğru",
                v1_comment: v1Comment,
                v2_isOk: v2Result === "doğru",
                v2_comment: v2Comment
            });
        }

        // Sonuçları API'ye gönderme
        const resultData = {
            deviceId: deviceId,
            testId: testId,
            testerId: testerId,
            subTestsResults: subTestResults
        };

        // Sonucu kaydetmek için API çağrısı yapın (örnek URL)
        const urlForSubmitResult = 'http://localhost:8083/v1/testResult/addTestResultForSubTests';
        fetch(urlForSubmitResult, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(resultData)
        })
        .then(response => {
            if (!response.ok) {
                handleError(response); // Hata durumunu burada yönet
                throw new Error('Request failed with status ' + response.status); // Hata durumu yakalamak için hata fırlatıyoruz
            }
            return response.json();
        })
        .then(responseData => {
            console.log('Sonuç başarıyla kaydedildi:', responseData);
            alert('Sonuç başarıyla kaydedildi!');
        })
        .catch(error => {
            console.error('Hata:', error);
            handleError(response); // Hata durumunu burada yönet
        });
    };
}


function logout() {
    const url = `http://localhost:8083/logout`;

    fetch(url, {
        method: 'POST',
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Çıkış yaparken bir hata oluştu');
        }
        alert('Çıkış yapıldı!');
        window.location.href = 'login.html';
    })
    .catch(error => {
        console.error('Çıkış işlemi sırasında bir hata oluştu:', error);
    });
}
