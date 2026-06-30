package models;
import observer.IBudgetObserver;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountId;
    private int userId;
    private String name;
    private String type; // AccountType
    private double balance;
    private String currency;
    private java.util.Date createdAt;
    private double budgetLimit;
    private double currentExpense;
    private List<IBudgetObserver> observers = new ArrayList<>();

    public Account(int accountId, double initialBalance, double budgetLimit) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.budgetLimit = budgetLimit;
        this.currentExpense = 0;
        this.userId = 1;
        this.name = "Default Account";
        this.type = "Checking";
        this.currency = "IDR";
        this.createdAt = new java.util.Date();
    }

    public void switchCurrency(String code) {
        this.currency = code;
    }

    public void delete() {
        System.out.println("[Account] Menghapus akun...");
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>();
    }

    public void addObserver(IBudgetObserver observer) {
        observers.add(observer);
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("[Sistem] Berhasil menambah dana: +Rp " + amount);
    }

    public void withdraw(double amount) {
        this.balance -= amount;
        this.currentExpense += amount;
        System.out.println("[Sistem] Berhasil mencatat pengeluaran: -Rp " + amount);
        notifyObservers();
    }

    private void notifyObservers() {
        for (IBudgetObserver obs : observers) {
            obs.update(currentExpense, budgetLimit);
        }
    }

    public double getBalance() { return balance; }
    public int getAccountId() { return accountId; }
}