package observer;
public class BudgetAlertObserver implements IBudgetObserver {
    private String alertName;

    public BudgetAlertObserver(String alertName) {
        this.alertName = alertName;
    }

    @Override
    public void update(double currentExpense, double budgetLimit) {
        double threshold = budgetLimit * 0.8; // Alert muncul jika sudah pakai 80% kuota
        if (currentExpense >= budgetLimit) {
            System.out.printf("[ALERT - %s] BAHAYA! Pengeluaran (Rp %.2f) telah melebihi batas anggaran (Rp %.2f)!", alertName, currentExpense, budgetLimit);
        } else if (currentExpense >= threshold) {
            System.out.printf("[ALERT - %s] PERINGATAN: Pengeluaran (Rp %.2f) sudah mencapai 80%% dari batas anggaran.", alertName, currentExpense);
        }
    }
}