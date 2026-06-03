public class CurrencyConverter {
    // 1. Variabel statis untuk menyimpan satu-satunya instance
    private static CurrencyConverter instance;
    private double usdToIdrRate = 15500.0; // Asumsi kurs tetap untuk simulasi

    // 2. Private constructor agar tidak bisa di-new dari luar
    private CurrencyConverter() {
        System.out.println("CurrencyConverter diinisialisasi...");
    }

    // 3. Method public statis untuk mendapatkan instance
    public static CurrencyConverter getInstance() {
        if (instance == null) {
            instance = new CurrencyConverter();
        }
        return instance;
    }

    public double convertUsdToIdr(double usdAmount) {
        return usdAmount * usdToIdrRate;
    }

    public double convertIdrToUsd(double idrAmount) {
        return idrAmount / usdToIdrRate;
    }
}