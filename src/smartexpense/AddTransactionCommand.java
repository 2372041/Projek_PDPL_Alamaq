package smartexpense;

import models.Account;
import models.Transaction;

public class AddTransactionCommand implements Command {
    private Account account;
    private Transaction transaction;

    public AddTransactionCommand(Account account, Transaction transaction) {
        this.account = account;
        this.transaction = transaction;
    }

    @Override
    public void execute() {
        if (transaction.getType().equals("INCOME")) {
            account.deposit(transaction.getAmount());
        } else if (transaction.getType().equals("EXPENSE")) {
            account.withdraw(transaction.getAmount());
        }
        transaction.save();
        System.out.println("[Command] Transaksi dieksekusi: " + transaction.getNote());
    }

    @Override
    public void undo() {
        if (transaction.getType().equals("INCOME")) {
            account.withdraw(transaction.getAmount());
        } else if (transaction.getType().equals("EXPENSE")) {
            account.deposit(transaction.getAmount());
        }
        System.out.println("[Command] Transaksi di-undo: " + transaction.getNote());
    }
}
