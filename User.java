import java.util.Date;
import java.util.List;

public class User {
    private int userId;
    private String name;
    private String email;
    private String passwordHash;
    private String pinCode;
    private String preferredTheme; // Contoh: "LIGHT" atau "DARK"
    private Date createdAt;

    public User(int userId, String name, String email, String passwordHash, String pinCode) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.pinCode = pinCode;
        this.preferredTheme = "LIGHT"; // Default theme
        this.createdAt = new Date();
    }

    // Method sesuai dengan Class Diagram
    public boolean login(String inputEmail, String inputPassword) {
        // Logika verifikasi email dan password
        return this.email.equals(inputEmail) && verifyPassword(inputPassword);
    }

    public void logout() {
        // Logika menghapus sesi aktif pengguna
        System.out.println("User " + name + " logged out.");
    }

    public void changePin(String newPin) {
        this.pinCode = newPin;
    }

    public void updateTheme(String theme) {
        this.preferredTheme = theme;
    }

    public List<Account> getAccounts() {
        // Logika mengambil akun milik user (akan dihubungkan ke Repository/Database nantinya)
        return null;
    }

    private boolean verifyPassword(String password) {
        // Dummy verifikasi hash
        return true; 
    }
}