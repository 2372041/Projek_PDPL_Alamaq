package models;
import java.util.Date;

public class IncomeTransaction extends Transaction {
    private String source;
    private String receivedFrom;
    private boolean isTaxable;

    public IncomeTransaction(int transactionId, int accountId, int categoryId, double amount, Date date, String note, String attachmentPath) {
        super(transactionId, accountId, categoryId, amount, "INCOME", date, note, attachmentPath);
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getReceivedFrom() { return receivedFrom; }
    public void setReceivedFrom(String receivedFrom) { this.receivedFrom = receivedFrom; }
    public boolean isTaxable() { return isTaxable; }
    public void setTaxable(boolean isTaxable) { this.isTaxable = isTaxable; }

    public void categorizeIncome() {}
    public void calculateTax() {}
    public void generateReceipt() {}
    public boolean isValidSource() { return true; }

    @Override
    public void save() {
        System.out.println("[Model] Memproses transaksi pemasukan lokal untuk catatan: " + note);
    }

    @Override
    public String getSummary() {
        return "Pemasukan sebesar Rp " + getAmount() + " (" + getNote() + ")";
    }
}