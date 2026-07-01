package pattern.decorator;

import models.Transaction;

public class PINLockDecorator extends TransactionDecorator {
    private boolean isUnlocked = false;
    private String pin;

    public PINLockDecorator(Transaction transaction, String pin) {
        super(transaction);
        this.pin = pin;
    }

    public boolean unlock(String inputPin) {
        if (this.pin.equals(inputPin)) {
            isUnlocked = true;
            return true;
        }
        return false;
    }

    @Override
    public String getSummary() {
        if (!isUnlocked) {
            return "[LOCKED] Masukkan PIN untuk melihat detail transaksi ini.";
        }
        return "🔓 [UNLOCKED] " + super.getSummary();
    }
}
