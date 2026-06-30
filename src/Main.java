import db.DatabaseConnection;
import dao.TransactionDAO;
import factory.TransactionFactory;
import models.Account;
import models.Transaction;
import observer.BudgetAlertObserver;
import smartexpense.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   SMARTEXPENSE FINAL DEMO - 100% COMPLETE (ALAMAQ)  ");
        System.out.println("=========================================================\n");

        // --- BAGIAN 75% LAMA ---
        // 1. Inisialisasi Akun Pengguna (Saldo Awal: Rp 2.000.000, Batas Limit Anggaran: Rp 500.000)
        Account dompetUtama = new Account(1, 2000000, 500000);

        // 2. Pasang Fitur Notifikasi Batas Anggaran (Pola Observer)
        BudgetAlertObserver alertSistem = new BudgetAlertObserver("Notifikasi WhatsApp/Sistem");
        dompetUtama.addObserver(alertSistem);

        // 3. Inisialisasi Akses Database MySQL (Pola DAO & Singleton)
        TransactionDAO dao = new TransactionDAO();
        
        List<Transaction> sessionTransactions = new ArrayList<>();

        System.out.println("--- SIMULASI TRANSAKSI LAMA ---");
        Transaction t1 = TransactionFactory.createTransaction("INCOME", 0, dompetUtama.getAccountId(), 1, 1500000, new Date(), "Gaji Project Progres 100%", "uploads/invoice_final.pdf");
        t1.save();
        dompetUtama.deposit(t1.getAmount());
        dao.saveTransaction(t1.getAccountId(), t1.getCategoryId(), t1.getAmount(), t1.getType(), "2026-06-30", t1.getNote(), t1.getAttachmentPath());
        sessionTransactions.add(t1);

        System.out.println("\n=========================================================");
        System.out.println("         DEMO FITUR BARU 25% (PROGRESS 100%)             ");
        System.out.println("=========================================================\n");

        // --- BAGIAN 25% BARU ---

        // A. COMMAND PATTERN (Undo/Redo)
        System.out.println("--- 1. DEMO COMMAND PATTERN (UNDO/REDO) ---");
        CommandHistory history = new CommandHistory();
        
        Transaction t2 = TransactionFactory.createTransaction("EXPENSE", 0, dompetUtama.getAccountId(), 2, 200000, new Date(), "Belanja Bulanan Kos", "uploads/struk_supermarket.png");
        Command cmd1 = new AddTransactionCommand(dompetUtama, t2);
        System.out.println("Mengeksekusi Transaksi Pengeluaran...");
        history.executeCommand(cmd1);
        sessionTransactions.add(t2);
        System.out.println("Saldo saat ini: Rp " + dompetUtama.getBalance());
        
        System.out.println("\nMembatalkan (Undo) Transaksi Terakhir...");
        history.undo();
        System.out.println("Saldo setelah undo: Rp " + dompetUtama.getBalance());
        
        System.out.println("\nMengulangi (Redo) Transaksi Terakhir...");
        history.redo();
        System.out.println("Saldo setelah redo: Rp " + dompetUtama.getBalance());
        
        // Simpan ke DB setelah fix (simulasi)
        dao.saveTransaction(t2.getAccountId(), t2.getCategoryId(), t2.getAmount(), t2.getType(), "2026-06-30", t2.getNote(), t2.getAttachmentPath());

        // B. STRATEGY PATTERN (Export Data)
        System.out.println("\n--- 2. DEMO STRATEGY PATTERN (MULTI-FORMAT EXPORT) ---");
        ExportManager exportManager = new ExportManager();
        
        // Export ke CSV
        exportManager.setStrategy(new CSVExport());
        exportManager.executeExport(sessionTransactions);
        System.out.println();
        
        // Export ke JSON
        exportManager.setStrategy(new JSONExport());
        exportManager.executeExport(sessionTransactions);

        // C. SINGLETON PATTERN TAMBAHAN (Currency Converter)
        System.out.println("\n--- 3. DEMO SINGLETON PATTERN TAMBAHAN (KURS MATA UANG) ---");
        CurrencyConverter converter = CurrencyConverter.getInstance();
        double saldoUSD = converter.convertIdrToUsd(dompetUtama.getBalance());
        System.out.printf("Saldo Akhir Riil (IDR): Rp %.2f\n", dompetUtama.getBalance());
        System.out.printf("Setara dengan (USD): $ %.2f\n", saldoUSD);

        System.out.println("\n=========================================================");
        System.out.println("             PROYEK 100% SUKSES DIJALANKAN               ");
        System.out.println("=========================================================");
    }
}