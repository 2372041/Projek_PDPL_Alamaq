package models;
import java.util.Date;

public abstract class Transaction {
    protected int transactionId;
    protected int accountId;
    protected int categoryId;
    protected double amount;
    protected String type;
    protected Date date; // Sesuai Class Diagram
    protected String note;
    protected String attachmentPath; // Sesuai Class Diagram
    protected Date createdAt;

    // Constructor Utama
    public Transaction(int transactionId, int accountId, int categoryId, double amount, String type, Date date, String note, String attachmentPath) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.note = note;
        this.attachmentPath = attachmentPath;
    }

    public void save() {
        System.out.println("[Transaction] Menyimpan transaksi...");
    }

    public void delete() {
        System.out.println("[Transaction] Menghapus transaksi...");
    }

    public void undo() {
        System.out.println("[Transaction] Undo operasi...");
    }

    public void redo() {
        System.out.println("[Transaction] Redo operasi...");
    }

    public String toCSV() {
        return transactionId + "," + amount + "," + type;
    }

    public String toJSON() {
        return "{ \"id\": " + transactionId + ", \"amount\": " + amount + " }";
    }

    public boolean validate() {
        return amount > 0;
    }

    // Abstract methods
    public abstract String getSummary();

    // ================= GETTER & SETTER =================
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getAttachmentPath() { return attachmentPath; }
    public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}