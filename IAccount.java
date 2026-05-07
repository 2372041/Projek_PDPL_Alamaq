import java.util.List;

// Antarmuka untuk abstraksi akun
public interface IAccount {
    void deposit(double amount);
    void withdraw(double amount) throws Exception;
    double getBalance();
    List<Transaction> getTransactions();
}