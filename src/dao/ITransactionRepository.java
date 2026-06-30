package dao;

public interface ITransactionRepository {
    void saveTransaction(int accountId, int categoryId, double amount, String type, String dateStr, String note, String attachmentPath);
    void printAllTransactions();
}
