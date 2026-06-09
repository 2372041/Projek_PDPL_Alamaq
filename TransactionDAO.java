import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {
    private Connection conn;

    public TransactionDAO() {
        // Mengambil koneksi Singleton yang dibuat pada target 60%
        this.conn = DatabaseConnection.getInstance();
    }

    // Method (C)RUD: Create / Menyimpan data ke MySQL
    public void saveTransaction(String type, int accountId, int categoryId, double amount, String note, String detail) {
        String sql = "INSERT INTO transactions (account_id, category_id, amount, note, type, detail) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, categoryId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, note);
            pstmt.setString(5, type);
            pstmt.setString(6, detail);
            
            pstmt.executeUpdate();
            System.out.println("[DAO] Transaksi " + type + " berhasil disimpan ke Database MySQL!");
        } catch (SQLException e) {
            System.out.println("[DAO] Error menyimpan data: " + e.getMessage());
        }
    }

    // Method C(R)UD: Read / Membaca data dari MySQL
    public void printAllTransactions() {
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- RIWAYAT TRANSAKSI DARI DATABASE ---");
            while (rs.next()) {
                String date = rs.getString("created_at");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String note = rs.getString("note");
                
                System.out.printf("[%s] %s: Rp %.2f - (%s)\n", date, type, amount, note);
            }
        } catch (SQLException e) {
            System.out.println("[DAO] Error mengambil data: " + e.getMessage());
        }
    }
}