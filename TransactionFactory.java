public class TransactionFactory {
    
    // Method statis untuk membuat objek transaksi berdasarkan tipe
    public static Transaction createTransaction(String type, int id, int accId, int catId, double amount, String note, String detail) {
        if (type.equalsIgnoreCase("INCOME")) {
            // detail di sini akan menjadi 'source' (sumber pendapatan)
            return new IncomeTransaction(id, accId, catId, amount, note, detail);
        } else if (type.equalsIgnoreCase("EXPENSE")) {
            // detail di sini akan menjadi 'payee' (tujuan pengeluaran)
            return new ExpenseTransaction(id, accId, catId, amount, note, detail);
        }
        throw new IllegalArgumentException("Tipe transaksi tidak dikenali: " + type);
    }
}