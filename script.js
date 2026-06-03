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
});