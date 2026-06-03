public class Main {
    public static void main(String[] args) {
        System.out.println("=== SMARTEXPENSE (60% - Factory Pattern & DB Setup) ===\n");

        // 1. Coba Panggil Koneksi Database (Persiapan Persistence)
        System.out.println("Mengecek Koneksi Database...");
        DatabaseConnection.getInstance();
        System.out.println();

        // 2. Inisialisasi Akun dan Observer (Dari progres 45%)
        Account tabungan = new Account(1, 2072050, "Tabungan Utama", "BANK", "IDR");
        tabungan.deposit(1000000); 
        BudgetAlert alertSistem = new BudgetAlert(300000); // Limit 300rb
        tabungan.addObserver(alertSistem);

        // 3. Menggunakan FACTORY PATTERN untuk mencatat transaksi
        System.out.println("--- Mencatat Transaksi dengan Factory ---");
        
        // Buat Pemasukan
        Transaction t1 = TransactionFactory.createTransaction("INCOME", 101, 1, 1, 500000, "Bonus Proyek", "Klien A");
        t1.save();
        tabungan.deposit(500000);

        // Buat Pengeluaran
        Transaction t2 = TransactionFactory.createTransaction("EXPENSE", 102, 1, 2, 1300000, "Beli Sparepart Komputer", "Toko Elektronik");
        t2.save();
        
        try {
            tabungan.withdraw(1300000); // Memicu Observer
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nSaldo Akhir: Rp " + tabungan.getBalance());
    }
}