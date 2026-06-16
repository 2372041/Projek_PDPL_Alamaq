const form = document.getElementById('transaction-form');
const tbody = document.getElementById('transaction-tbody');
const balanceDisplay = document.querySelector('.balance-amount');

// Fungsi untuk menghitung dan menampilkan saldo
function updateSaldo() {
    let riwayat = JSON.parse(localStorage.getItem('riwayat') || '[]');
    let saldoAwal = 1000000; // Saldo awal Anda

    let total = riwayat.reduce((acc, t) => {
        let nominal = parseFloat(t.amount);
        return t.type === 'income' ? acc + nominal : acc - nominal;
    }, saldoAwal);

    balanceDisplay.innerText = new Intl.NumberFormat('id-ID', { style: 'currency', currency: 'IDR' }).format(total);
}

// Fungsi untuk menambah baris ke tabel
function tambahBarisKeTabel(type, amount, note) {
    const formattedAmount = new Intl.NumberFormat('id-ID', { style: 'currency', currency: 'IDR' }).format(amount);
    let displayAmount = type === 'expense' ? `- ${formattedAmount}` : `+ ${formattedAmount}`;
    let textClass = type === 'expense' ? 'text-expense' : 'text-income';
    let badgeClass = type === 'expense' ? 'badge-expense' : 'badge-income';
    let categoryText = type === 'expense' ? 'Pengeluaran' : 'Pemasukan';

    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td><span class="badge ${badgeClass}">Baru saja</span></td>
        <td>
            <strong>${note}</strong><br>
            <small class="text-muted">${categoryText}</small>
        </td>
        <td class="text-right ${textClass}">${displayAmount}</td>
    `;
    
    tbody.insertBefore(newRow, tbody.firstChild);
}

// Menangani submit form
form.addEventListener('submit', async function(e) {
    e.preventDefault(); 

    const type = document.getElementById('type').value;
    const amount = document.getElementById('amount').value;
    const note = document.getElementById('note').value;
    
    // 1. Simpan data ke LocalStorage
    const transaksiBaru = { type, amount, note };
    let riwayat = JSON.parse(localStorage.getItem('riwayat') || '[]');
    riwayat.push(transaksiBaru);
    localStorage.setItem('riwayat', JSON.stringify(riwayat));

    // 2. Update UI (Tabel & Saldo)
    tambahBarisKeTabel(type, amount, note);
    updateSaldo();
    form.reset();

    // 3. Kirim ke Backend Java (Tanpa mengganggu tampilan)
    try {
        await fetch('/api/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `type=${type}&amount=${amount}&note=${note}`
        });
    } catch (err) { console.log("Backend offline, mode demo aktif."); }
});

// Memuat data saat halaman dibuka
window.addEventListener('load', function() {
    let riwayat = JSON.parse(localStorage.getItem('riwayat') || '[]');
    riwayat.forEach(t => tambahBarisKeTabel(t.type, t.amount, t.note));
    updateSaldo(); // Hitung saldo awal
    
    if (localStorage.getItem('isLoggedIn') === 'true') {
        document.getElementById('auth-overlay').style.display = 'none';
    }
});