public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEM SMARTEXPENSE (30% IMPLEMENTATION) ===\n");

        // 1. Tes Pembuatan Akun
        Account dompet = new Account(101, 1, "Dompet Utama", "CASH", "IDR");
        dompet.deposit(5000000); // Saldo awal Rp 5.000.000
        System.out.println("Saldo Awal Akun: Rp " + dompet.getBalance());

        // 2. Tes Kategori
        Category catGaji = new Category(1, "Gaji Bulanan", "INCOME");
        Category catMakan = new Category(2, "Makan Siang", "EXPENSE");

        // 3. Tes Transaksi (Polymorphism)
        System.out.println("\n--- Memproses Transaksi ---");
        Transaction gajiMaret = new IncomeTransaction(1001, 101, catGaji.hashCode(), 4500000, "Gaji bulan Maret", "Perusahaan IT");
        gajiMaret.save(); 
        dompet.deposit(4500000); // Update saldo

        Transaction makanSiang = new ExpenseTransaction(1002, 101, catMakan.hashCode(), 35000, "Makan bareng teman", "Warung Nasi");
        makanSiang.save();
        
        try {
            dompet.withdraw(35000); // Update saldo
        } catch (Exception e) {
            System.out.println("Error Transaksi: " + e.getMessage());
        }

        System.out.println("\nSaldo Akhir Akun: Rp " + dompet.getBalance());

        // 4. Tes Design Pattern (Singleton Currency Converter)
        System.out.println("\n--- Mengetes Singleton Design Pattern ---");
        CurrencyConverter converter1 = CurrencyConverter.getInstance();
        double saldoUSD = converter1.convertIdrToUsd(dompet.getBalance());
        System.out.printf("Saldo dalam USD: $%.2f\n", saldoUSD);

        // Membuktikan bahwa Singleton berhasil (objek tidak dibuat dua kali)
        CurrencyConverter converter2 = CurrencyConverter.getInstance();
        System.out.println("Apakah converter1 dan converter2 adalah objek yang sama? " + (converter1 == converter2));
    }
}