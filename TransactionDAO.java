import java.sql.*;

public class TransactionDAO {
    private Connection conn;

    public TransactionDAO() {
        this.conn = DatabaseConnection.getInstance();
    }

    public String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

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
        } catch (SQLException e) {
            System.out.println("[DAO Error] " + e.getMessage());
        }
    }

    // Method yang dicari oleh Main.java
    public void printAllTransactions() {
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- RIWAYAT TRANSAKSI DARI DATABASE ---");
            while (rs.next()) {
                System.out.printf("[%s] %s: Rp %.2f - (%s)\n", 
                    rs.getString("created_at"), rs.getString("type"), 
                    rs.getDouble("amount"), rs.getString("note"));
            }
        } catch (SQLException e) {
            System.out.println("[DAO] Gagal mencetak riwayat: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public double getLiveBalance() {
        String sql = "SELECT type, amount FROM transactions";
        double total = 1000000;
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                if ("income".equalsIgnoreCase(rs.getString("type"))) total += rs.getDouble("amount");
                else total -= rs.getDouble("amount");
            }
        } catch (SQLException e) {}
        return total;
    }

    public String getAllTransactionsAsJSON() {
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        StringBuilder json = new StringBuilder("[");
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{\"type\":\"").append(rs.getString("type")).append("\",")
                    .append("\"amount\":").append(rs.getDouble("amount")).append(",")
                    .append("\"note\":\"").append(rs.getString("note").replace("\"", "\\\"")).append("\",")
                    .append("\"date\":\"").append(rs.getString("created_at")).append("\"}");
                first = false;
            }
        } catch (SQLException e) {}
        return json.append("]").toString();
    }
}