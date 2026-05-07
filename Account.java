import java.util.Date;
import java.util.List;

public class Account implements IAccount {
    private int accountId;
    private int userId; // Referensi ke pemilik akun
    private String name;
    private String type; // CASH, BANK, EWALLET
    private double balance;
    private String currency; // IDR, USD
    private Date createdAt;

    public Account(int accountId, int userId, String name, String type, String currency) {
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.balance = 0.0; // Saldo awal 0
        this.currency = currency;
        this.createdAt = new Date();
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new Exception("Saldo tidak mencukupi atau jumlah tidak valid."); // Error Handling yang Jelas
        }
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public List<Transaction> getTransactions() {
        // Logika mengambil riwayat transaksi akun
        return null;
    }

    public void switchCurrency(String code) {
        this.currency = code;
        // Integrasi dengan CurrencyConverter (Singleton Pattern) akan diletakkan di sini nantinya
    }

    public void delete() {
        // Menghapus akun
    }
}