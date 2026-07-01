package pattern.decorator;

import models.Transaction;

public abstract class TransactionDecorator extends Transaction {
    protected Transaction wrappedTransaction;

    public TransactionDecorator(Transaction transaction) {
        // Meneruskan atribut dari transaksi asli ke superclass
        super(transaction.getTransactionId(), transaction.getAccountId(), 
              transaction.getCategoryId(), transaction.getAmount(), 
              transaction.getType(), transaction.getDate(), 
              transaction.getNote(), transaction.getAttachmentPath());
        this.wrappedTransaction = transaction;
    }

    @Override
    public String getSummary() {
        return wrappedTransaction.getSummary();
    }
    
    @Override
    public void save() {
        wrappedTransaction.save();
    }
}
