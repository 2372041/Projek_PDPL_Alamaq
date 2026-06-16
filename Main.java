public class Main {
    public static void main(String[] args) {
        System.out.println("=== SMARTEXPENSE ===\n");

        // 1. Inisialisasi Akun & DAO
        Account tabungan = new Account(1, 2072050, "Tabungan Utama", "BANK", "IDR");
        tabungan.deposit(1000000); 
        TransactionDAO dao = new TransactionDAO(); 

        // 2. Menggunakan FACTORY & DAO
        System.out.println("--- Memproses Transaksi ---");
        
        String type1 = "INCOME";
        Transaction t1 = TransactionFactory.createTransaction(type1, 101, 1, 1, 500000, "Gaji Freelance", "Klien B");
        t1.save();
        tabungan.deposit(500000);
        dao.saveTransaction(type1, 1, 1, 500000, "Gaji Freelance", "Klien B");

        String type2 = "EXPENSE";
        Transaction t2 = TransactionFactory.createTransaction(type2, 102, 1, 2, 150000, "Beli Paket Data", "Telkomsel");
        t2.save();
        try {
            tabungan.withdraw(150000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dao.saveTransaction(type2, 1, 2, 150000, "Beli Paket Data", "Telkomsel");

        // 3. Buktikan bahwa data masuk ke Database
        dao.printAllTransactions();
        
        System.out.println("\nSaldo Akhir Sistem: Rp " + tabungan.getBalance());
        
        // 4. Jalankan Web Server
        try {
            SmartExpenseWebServer.jalankanServer();
        } catch (Exception e) {
            System.out.println("Gagal menjalankan Web Server: " + e.getMessage());
        }
    }
}