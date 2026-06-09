// CODE-CITE:
//   Title: [AI-Generated Code]
//   Type: [ai]
//   Value: [Isi Link/Sumber]
//   Notes: [DOM Manipulation sederhana untuk menangkap input form dan memperbarui baris tabel secara dinamis (tanpa reload).]
//   Lines Range: 37
// Menangkap elemen dari HTML
const form = document.querySelector('form');
const tbody = document.querySelector('table tbody');

// Memberikan aksi ketika tombol "Simpan" ditekan
form.addEventListener('submit', function(e) {
    e.preventDefault(); // Mencegah halaman refresh

    // Mengambil nilai dari inputan
    const amount = form.querySelector('input[type="number"]').value;
    const type = form.querySelector('select').value;
    const note = form.querySelector('input[type="text"]').value;
    
    // Menentukan warna dan tanda minus/plus
    let displayAmount = type === 'expense' ? `- Rp ${amount}` : `+ Rp ${amount}`;
    let color = type === 'expense' ? 'red' : 'green';
    let category = type === 'expense' ? 'Pengeluaran' : 'Pemasukan';

    // Membuat elemen baris tabel (<tr>) baru
    const newRow = document.createElement('tr');
    
    // Format tanggal hari ini
    const today = new Date().toISOString().split('T')[0];

    newRow.innerHTML = `
        <td>${today}</td>
        <td>${category}</td>
        <td>${note}</td>
        <td style="color: ${color}; font-weight: bold;">${displayAmount}</td>
    `;

    // Memasukkan baris baru ke paling atas tabel
    tbody.insertBefore(newRow, tbody.firstChild);

    // Mengosongkan form setelah simpan
    form.reset();
});const form = document.getElementById('transaction-form');
const tbody = document.getElementById('transaction-tbody');

form.addEventListener('submit', function(e) {
    e.preventDefault(); 

    const type = document.getElementById('type').value;
    const amount = document.getElementById('amount').value;
    const note = document.getElementById('note').value;
    
    // Format nominal ke Rupiah
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

    // Animasi sederhana saat menambah baris
    newRow.style.opacity = '0';
    tbody.insertBefore(newRow, tbody.firstChild);
    
    setTimeout(() => {
        newRow.style.opacity = '1';
        newRow.style.transition = 'opacity 0.5s ease-in';
    }, 50);

    form.reset();
});