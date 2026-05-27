public class BudgetAlert implements IBudgetObserver {
    private double limit;

    public BudgetAlert(double limit) {
        this.limit = limit;
    }

    @Override
    public void update(double balance) {
        if (balance < limit) {
            System.out.println("[ALERT SYSTEM] Peringatan: Saldo Anda tinggal Rp " + balance + ". Sudah di bawah batas limit Rp " + limit + "!");
        } else {
            System.out.println("[SISTEM] Saldo masih dalam batas aman.");
        }
    }
}