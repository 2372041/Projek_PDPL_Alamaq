import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Variabel statis untuk menyimpan satu-satunya instance koneksi
    private static Connection connection;
    
    // Sesuaikan nama database, user, dan password dengan MySQL (XAMPP) Anda nanti
    private static final String URL = "jdbc:mysql://localhost:3306/smartexpense";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // 2. Private constructor
    private DatabaseConnection() {}

    // 3. Method public untuk mendapatkan instance
    public static Connection getInstance() {
        if (connection == null) {
            try {
                // Membuka koneksi ke MySQL
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Koneksi ke MySQL Berhasil!");
            } catch (SQLException e) {
                System.out.println("[DB] Peringatan: Database belum tersambung/dibuat. Error: " + e.getMessage());
                // Tidak apa-apa error saat ini, karena XAMPP/MySQL mungkin belum Anda nyalakan
            }
        }
        return connection;
    }
}