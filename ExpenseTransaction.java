public class ExpenseTransaction extends Transaction {
    private String payee; 

    public ExpenseTransaction(int transactionId, int accountId, int categoryId, double amount, String note, String payee) {
        super(transactionId, accountId, categoryId, amount, note);
        this.payee = payee;
    }

    @Override
    public void save() {
        System.out.println("[-] Pengeluaran: Rp " + this.amount + " untuk " + this.payee + " berhasil dicatat. Catatan: " + this.note);
    }
}