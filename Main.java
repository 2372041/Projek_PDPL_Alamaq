public class Main {
    public static void main(String[] args) {
        // Mengetes Class User
        User userBaru = new User(1, "Alamaq User", "user@mail.com", "hash123", "1234");
        System.out.println("User Berhasil Dibuat: " + userBaru.login("user@mail.com", "hash123"));

        // Mengetes Class Account
        Account dompet = new Account(101, 1, "Dompet Utama", "CASH", "IDR");
        dompet.deposit(50000);
        System.out.println("Saldo awal: " + dompet.getBalance());

        try {
            dompet.withdraw(20000);
            System.out.println("Saldo setelah tarik: " + dompet.getBalance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}