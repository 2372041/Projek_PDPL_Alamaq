package factory;
// CODE-CITE:
//   Title: [AI-Generated Code]
//   Type: [ai]
//   Value: [Isi Link/Sumber]
//   Notes: [Menggunakan Factory Method Pattern untuk membuat instansi objek IncomeTransaction dan ExpenseTransaction. Struktur dan logika dasar dihasilkan oleh AI.]
//   Lines Range: 14

import models.Transaction;
import models.IncomeTransaction;
import models.ExpenseTransaction;
import java.util.Date;

public class TransactionFactory {
    public static Transaction createTransaction(String type, int transactionId, int accountId, int categoryId, double amount, Date date, String note, String attachmentPath) {
        if (type.equalsIgnoreCase("INCOME")) {
            return new IncomeTransaction(transactionId, accountId, categoryId, amount, date, note, attachmentPath);
        } else if (type.equalsIgnoreCase("EXPENSE")) {
            return new ExpenseTransaction(transactionId, accountId, categoryId, amount, date, note, attachmentPath);
        }
        throw new IllegalArgumentException("Tipe transaksi '" + type + "' tidak valid.");
    }
}