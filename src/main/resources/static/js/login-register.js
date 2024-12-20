document.getElementById('registerForm').addEventListener('submit', function (e) {
    e.preventDefault(); // Sayfanın yeniden yüklenmesini engelle

    const testerId = document.getElementById('testerId').value;
    const testerName = document.getElementById('testerName').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

//    const url = `/v1/tester/createTesterRequest`;
    const url = `/v1/tester/register`;


    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ testerId, testerName, username, password,email })
    })
    .then(response => {
        if (response.ok) {
            alert('Kayıt başarılı!');
            window.location.href = 'login.html'; // Giriş sayfasına yönlendirin
        } else {
            return response.json().then(err => { throw new Error(err.message); });
        }
    })
    .then(data => {
            console.log(data);
        })
        .catch(error => {
            alert('Hata: ' + error.message); // Hata mesajını göster
            console.error('Error:', error);
        });
});

