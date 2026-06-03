public class IncomeTransaction extends Transaction {
    private String source; // Contoh: "Gaji", "Bonus", "Investasi"

    public IncomeTransaction(int transactionId, int accountId, int categoryId, double amount, String note, String source) {
        // Memanggil constructor dari class induk (Transaction)
        super(transactionId, accountId, categoryId, amount, note);
        this.source = source;
    }

    @Override
    public void save() {
        // Implementasi spesifik untuk menyimpan pemasukan
        System.out.println("[+] Pemasukan: Rp " + this.amount + " dari " + this.source + " berhasil dicatat. Catatan: " + this.note);
    }
}