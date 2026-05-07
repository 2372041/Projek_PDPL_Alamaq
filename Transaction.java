import java.util.Date;

public abstract class Transaction {
    protected int transactionId;
    protected int accountId;
    protected int categoryId;
    protected double amount;
    protected Date date;
    protected String note;
    protected String attachmentPath;
    protected Date createdAt;

    public Transaction(int transactionId, int accountId, int categoryId, double amount, String note) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = new Date(); // Waktu transaksi terjadi
        this.note = note;
        this.createdAt = new Date();
    }

    // Method umum untuk semua jenis transaksi
    public void save() {
        // Logika menyimpan transaksi ke database
        System.out.println("Transaksi berhasil disimpan.");
    }

    public void delete() {
        // Logika menghapus transaksi
    }

    public void undo() {
        // Fitur Undo Input
    }

    public void redo() {
        // Fitur Redo Input
    }

    public String toCSV() {
        return transactionId + "," + accountId + "," + amount + "," + date;
    }

    public String toJSON() {
        return "{ \"transactionId\": " + transactionId + ", \"amount\": " + amount + " }";
    }

    public boolean validate() {
        return this.amount > 0; // Contoh validasi: nominal tidak boleh nol atau negatif
    }
}