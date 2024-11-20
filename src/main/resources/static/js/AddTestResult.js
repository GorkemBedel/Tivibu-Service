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
    const url = `/v1/test/getTestsByType/${testType}`;

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
                : ''}
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


function openResultEntryWithoutSubTests(testId) {
    const resultEntryArea = document.getElementById('resultEntryArea');
    resultEntryArea.style.display = 'block';

    const subTestResultsContainer = document.getElementById('subTestResultsContainer');
    subTestResultsContainer.innerHTML = '';

    const urlForGetTesterIdRequest = '/v1/tester/getTesterId';
    let testerId = null; // Tester ID için değişken tanımlıyoruz

    // Tester ID'yi al
    getTesterId(urlForGetTesterIdRequest).then(data => {
        testerId = data; // testerId'yi güncelle
        console.log('Tester ID:', testerId); // testerId'yi konsola yazdır
    }).catch(error => {
        console.error('Tester ID Hatası:', error); // Hata durumunda hata mesajı yazdır
    });

    const testWithoutSubTestDiv = document.createElement('div');
    testWithoutSubTestDiv.classList.add('no-sub-test');

    testWithoutSubTestDiv.innerHTML = `
        <h4>Test Sonuçları</h4>
        <label for="v1Result">v1 Sonucu:</label>
        <select id="v1Result" required>
            <option value="">Seçiniz</option>
            <option value="doğru">Doğru</option>
            <option value="yanlış">Yanlış</option>
        </select>
        <label for="v1Comment">v1 Yorumu:</label>
        <input type="text" id="v1Comment" placeholder="Yorum girin" required /><br>
        <label for="v2Result">v2 Sonucu:</label>
        <select id="v2Result" required>
            <option value="">Seçiniz</option>
            <option value="doğru">Doğru</option>
            <option value="yanlış">Yanlış</option>
        </select>
        <label for="v2Comment">v2 Yorumu:</label>
        <input type="text" id="v2Comment" placeholder="Yorum girin" required /><br><br>
    `;

    // Sonuçları form alanına ekleme
    subTestResultsContainer.appendChild(testWithoutSubTestDiv);








     // Sonucu kaydetme butonuna tıklama olayını yönetme
        document.getElementById('submitResultButton').onclick = function() {
            const deviceType = document.getElementById('deviceSelect').value; // Kullanıcıdan cihaz Type'ını al  (string)
            const deviceVersion = document.getElementById('tivibuVersion').value; // Kullanıcıdan cihaz version'unu al

            if (!testerId) {
                alert('Tester ID alınamadı. Lütfen tekrar deneyin.'); // Tester ID yoksa uyarı ver
                return;
            }

            // Sonuçları hazırlama
            let v1_result = {};
            let v2_result = {};

            const v1Result = document.getElementById(`v1Result`).value;
            const v1Comment = document.getElementById(`v1Comment`).value;
            const v2Result = document.getElementById(`v2Result`).value;
            const v2Comment = document.getElementById(`v2Comment`).value;


            // v1_result objesini oluşturma
            v1_result = {
                isOk: v1Result === "doğru",
                comment: v1Comment
            };

            // v2_result objesini oluşturma
            v2_result = {
                isOk: v2Result === "doğru",
                comment: v2Comment
            };


            // Sonuçları API'ye gönderme
            const resultData = {
                tivibuVersion: deviceVersion,
                deviceType: deviceType,
                testId: testId,
                testerId: testerId,
                v1_result: v1_result,
                v2_result: v2_result
            };

            // Sonucu kaydetmek için API çağrısı yapın (örnek URL)
            const urlForSubmitResult = '/v1/testResult/addTestResult';
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



function openResultEntryWithSubTests(testId, subTestNumber) {
    const resultEntryArea = document.getElementById('resultEntryArea');
    resultEntryArea.style.display = 'block';

    const subTestResultsContainer = document.getElementById('subTestResultsContainer');
    subTestResultsContainer.innerHTML = '';

    const urlForGetTesterIdRequest = '/v1/tester/getTesterId';
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
            const deviceType = document.getElementById('deviceSelect').value; // Kullanıcıdan cihaz Type'ını al  (string)
            const deviceVersion = document.getElementById('tivibuVersion').value; // Kullanıcıdan cihaz version'unu al
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
            tivibuVersion: deviceVersion,
            deviceType: deviceType,
            testId: testId,
            testerId: testerId,
            subTestsResults: subTestResults
        };

        // Sonucu kaydetmek için API çağrısı yapın (örnek URL)
        const urlForSubmitResult = '/v1/testResult/addTestResultForSubTests';
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
    const url = `/logout`;

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

async function fetchDevices() {
    try {
        const response = await fetch('/v1/device/getAllDeviceTypes');  // Backend'den cihaz verilerini çek
        const devices = await response.json(); // JSON formatında cihaz verilerini al

        const deviceSelect = document.getElementById('deviceSelect'); // select elementini al

        // Cihaz verilerini döngüyle işle ve <option> elementleri oluştur
        devices.forEach(device => {
            const option = document.createElement('option');
            option.value = device; // Cihazın ismini option value olarak kullan
            option.textContent = device; // Cihaz ismini option metni olarak kullan
            deviceSelect.appendChild(option); // <select> elementine option ekle
        });
    } catch (error) {
        console.error('Cihaz verileri çekilemedi:', error);
    }
}




async function fetchTestTypes() {
    try {
        const response = await fetch('/v1/test/getTestTypes');  // Backend'den cihaz verilerini çek
        const testTypes = await response.json(); // JSON formatında cihaz verilerini al

        const testTypeSelect = document.getElementById('testType'); // select elementini al

        // Cihaz verilerini döngüyle işle ve <option> elementleri oluştur
        testTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type; // Cihazın ismini option value olarak kullan
            option.textContent = type; // Cihaz ismini option metni olarak kullan
            testTypeSelect.appendChild(option); // <select> elementine option ekle
        });
    } catch (error) {
        console.error('Cihaz verileri çekilemedi:', error);
    }
}

// Sayfa yüklendiğinde her iki fonksiyonu çalıştır
window.onload = function() {
    fetchDevices();
    fetchTestTypes();
};

