# SmartExpense - Financial Management App (Tugas Besar)

SmartExpense adalah aplikasi pencatat keuangan pribadi yang dirancang untuk membantu pengguna dalam memantau arus kas harian mereka secara terstruktur.


Proyek ini disusun oleh **Kelompok Alamaq** untuk memenuhi laporan dan kriteria Tugas Besar hingga mencapai _progress 100%_.

---

## Fitur & Implementasi *Design Pattern*

Aplikasi ini dibagi menjadi dua bagian: **Backend Console (Java)** dan **Frontend UI Mockup (HTML/CSS/JS)**. Keduanya saling mencerminkan fitur-fitur di bawah ini:

### 1. Pencatatan Inti (Core Logging)
- **Factory Method Pattern:** Diimplementasikan pada `TransactionFactory.java` untuk membuat objek transaksi secara dinamis, baik `IncomeTransaction` maupun `ExpenseTransaction`.
- Manajemen kategori, multi-akun, dan pencatatan.

### 2. Keamanan & Kontrol (Security & Control)
- **Observer Pattern:** Diimplementasikan pada `BudgetAlertObserver.java` untuk memberi notifikasi sistematis (atau UI alert merah di web) saat saldo menipis atau melebihi kuota 80% dan 100%.
- **Singleton Pattern:** Diimplementasikan pada koneksi `DatabaseConnection.java` ke MySQL dan pengatur nilai tukar mata uang `CurrencyConverter.java`.

### 3. Fleksibilitas UX (UX Flexibility)
- **Command Pattern (Undo/Redo):** Diimplementasikan melalui `CommandHistory.java` dan `AddTransactionCommand.java` agar transaksi yang baru dimasukkan dapat di-*undo* maupun di-*redo*.
- **Strategy Pattern (Multi-Format Export):** Pengaturan *export* file riwayat transaksi secara fleksibel ke format **CSV** maupun **JSON** menggunakan `ExportManager.java` dan interface `ExportStrategy.java`.

### 4. Analisis & Wawasan
- Tampilan riwayat yang difilter dan pencarian teks secara *real-time*.
- *Premium Dashboard* dengan kapabilitas *Dark Mode* di antarmuka web.

---

## Panduan Penggunaan & Cara Menjalankan (Run)

### A. Menjalankan Backend Java (Core Logic)
Proyek ini menggunakan **MySQL** sebagai database utama.
1. Pastikan Anda telah menghidupkan server database (misal melalui XAMPP).
2. Buat database baru (misal: `smartexpense`) dan sesuaikan pengaturan koneksi pada class `DatabaseConnection`.
3. Buka proyek ini di IDE favorit Anda (IntelliJ IDEA, VS Code, atau NetBeans).
4. Pastikan library *MySQL Connector (JDBC)* sudah terhubung (ter-load) ke dalam konfigurasi proyek Anda.
5. Jalankan (Run) file `Main.java` untuk melihat demonstrasi seluruh *Design Pattern* di dalam Terminal/Console.

### B. Menjalankan Antarmuka Web (UI Frontend)
Antarmuka web ini bersifat *mockup interaktif* yang menstimulasikan alur kerja backend menggunakan penyimpanan lokal peramban (*LocalStorage*).
1. Masuk ke folder `frontend`.
2. Buka file **`auth.html`** menggunakan browser web (Chrome, Edge, atau Firefox). 
3. Lakukan **Registrasi** terlebih dahulu, kemudian gunakan data tersebut untuk **Masuk (Login)**.

---

## Fitur Utama di Dalam Dashboard

Setelah Anda berhasil Login dan masuk ke halaman Dashboard utama (`index.html`), Anda dapat berinteraksi dengan berbagai fitur berikut yang mensimulasikan sistem Java di belakangnya:

1. **Pencatatan Transaksi:** Tambahkan riwayat Pemasukan (Income) atau Pengeluaran (Expense) lengkap dengan rincian kategori dan tanggal.
2. **Undo & Redo (Command Pattern):** Anda dapat membatalkan (Undo) transaksi yang baru saja ditambahkan, atau mengulanginya kembali (Redo) tanpa harus mengetik ulang.
3. **Konversi Mata Uang (Singleton Pattern):** Tekan tombol di samping Total Saldo untuk secara instan mengonversi dan melihat nominal saldo dalam format IDR (Rupiah) atau USD (Dolar).
4. **Peringatan Anggaran (Observer Pattern):** Jika pengeluaran Anda menyentuh persentase batas tertentu dari limit saldo, sebuah pop-up *Alert* merah akan otomatis muncul di bagian atas layar.
5. **Ekspor Data (Strategy Pattern):** Pada pojok kanan bagian riwayat, Anda dapat mengunduh seluruh data tabel menjadi file berektensi `.csv` atau `.json`.
6. **Pencarian Cerdas (Search & Filter):** Temukan transaksi spesifik dengan cepat menggunakan kotak pencarian secara *real-time*, atau filter berdasarkan jenis Pemasukan/Pengeluaran.
7. **Kustomisasi Tema:** Klik ikon 🌙 (Bulan) pada profil untuk mengubah nuansa aplikasi menjadi *Dark Mode* tanpa merusak tema warna mewah (*premium gold/navy*) dari aplikasi.

---
*© 2026 Alamaq Team - Hak Cipta Dilindungi.*
