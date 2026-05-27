public class Main {
    public static void main(String[] args) {
        System.out.println("=== SMARTEXPENSE (45% - Observer & Budget Alert) ===\n");

        // 1. Inisialisasi Akun
        Account tabungan = new Account(1, 2072050, "Tabungan Utama", "BANK", "IDR");
        tabungan.deposit(1000000); // Isi saldo 1 juta

        // 2. Pasang Observer (Budget Alert) 
        // Set limit di 200.000. Jika saldo di bawah ini, muncul peringatan.
        BudgetAlert alertSistem = new BudgetAlert(200000);
        tabungan.addObserver(alertSistem);

        // 3. Simulasi Transaksi Besar
        System.out.println("Saldo saat ini: " + tabungan.getBalance());
        
        try {
            System.out.println("\n--- Belanja Kebutuhan Bulanan (Rp 850.000) ---");
            tabungan.withdraw(850000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nSaldo Akhir: " + tabungan.getBalance());
    }
}