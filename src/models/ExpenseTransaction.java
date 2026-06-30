package models;
import java.util.Date;

public class ExpenseTransaction extends Transaction {
    private boolean isRecurring;
    private int recurringIntervalDays;
    private Date nextDueDate;

    public ExpenseTransaction(int transactionId, int accountId, int categoryId, double amount, Date date, String note, String attachmentPath) {
        super(transactionId, accountId, categoryId, amount, "EXPENSE", date, note, attachmentPath);
    }

    public boolean isRecurring() { return isRecurring; }
    public void setRecurring(boolean recurring) { isRecurring = recurring; }
    public int getRecurringIntervalDays() { return recurringIntervalDays; }
    public void setRecurringIntervalDays(int recurringIntervalDays) { this.recurringIntervalDays = recurringIntervalDays; }
    public Date getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(Date nextDueDate) { this.nextDueDate = nextDueDate; }

    public void scheduleNext() {}
    public boolean isOverdue() { return false; }
    public void cancel() {}
    public double getRemainingBudget() { return 0; }
    public void markAsPaid() {}

    @Override
    public String getSummary() {
        return "Pengeluaran sebesar Rp " + getAmount() + " (" + getNote() + ")";
    }

    @Override
    public void save() {
        System.out.println("[Model] Memproses transaksi pengeluaran lokal untuk catatan: " + note);
    }
}