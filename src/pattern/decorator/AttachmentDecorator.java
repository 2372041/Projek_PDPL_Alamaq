package pattern.decorator;

import models.Transaction;

public class AttachmentDecorator extends TransactionDecorator {
    private String additionalNotes;

    public AttachmentDecorator(Transaction transaction, String additionalNotes) {
        super(transaction);
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String getSummary() {
        return super.getSummary() + " | [Catatan Tambahan Decorator: " + additionalNotes + "]";
    }
}
