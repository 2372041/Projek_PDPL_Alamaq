package dao;
// CODE-CITE:
//   Title: DAO Pattern
//   Type: [ai]
//   Value: [Isi Link/Sumber]
//   Notes: Menggunakan Data Access Object (DAO) Pattern untuk memisahkan logika eksekusi query MySQL dari logika model bisnis utama.
//   Lines Range: 53
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseConnection;

public class TransactionDAO implements ITransactionRepository {
    private Connection conn;

    public TransactionDAO() {
        // Mengambil instance koneksi database Singleton
        this.conn = DatabaseConnection.getInstance();
    }

    /**
     * Menyimpan data transaksi ke MySQL sesuai Atribut ERD baru
     * @param dateStr Format string tanggal wajib "YYYY-MM-DD"
     */
    public void saveTransaction(int accountId, int categoryId, double amount, String type, String dateStr, String note, String attachmentPath) {
        String sql = "INSERT INTO transactions (account_id, category_id, amount, type, date, note, attachment_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, categoryId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, type);
            // Mengonversi string ke tipe java.sql.Date untuk MySQL DATE column
            pstmt.setDate(5, java.sql.Date.valueOf(dateStr)); 
            pstmt.setString(6, note);
            pstmt.setString(7, attachmentPath);
            
            pstmt.executeUpdate();
            System.out.println("[DAO] Data transaksi berhasil disimpan permanen ke database MySQL!");
        } catch (SQLException e) {
            System.out.println("[DAO] Error Database: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[DAO] Error Format Tanggal (wajib YYYY-MM-DD): " + e.getMessage());
        }
    }

    // Membaca dan menampilkan riwayat data berdasarkan struktur tabel ERD terbaru
    public void printAllTransactions() {
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- RIWAYAT TRANSAKSI BERDASARKAN SKEMA ERD BARU ---");
            while (rs.next()) {
                int id = rs.getInt("transaction_id"); // Menggunakan transaction_id sesuai ERD
                String date = rs.getString("date");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String note = rs.getString("note");
                String path = rs.getString("attachment_path");
                
                System.out.printf("ID: %d | Tanggal Nota: %s | %s | Nominal: Rp %.2f | Catatan: %s | Lampiran: %s\n", 
                                  id, date, type, amount, note, (path == null || path.isEmpty() ? "-" : path));
            }
        } catch (SQLException e) {
            System.out.println("[DAO] Error saat menarik data: " + e.getMessage());
        }
    }
}