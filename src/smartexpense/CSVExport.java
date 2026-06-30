package smartexpense;

import java.util.List;
import models.Transaction;

public class CSVExport implements ExportStrategy {
    @Override
    public void export(List<Transaction> data) {
        System.out.println("[Export] Mengekspor " + data.size() + " transaksi ke format CSV...");
        System.out.println("ID,Kategori,Tipe,Nominal,Catatan,Lampiran");
        for (Transaction t : data) {
            System.out.printf("%d,%d,%s,%.2f,%s,%s\n", 
                t.getAccountId(), t.getCategoryId(), t.getType(), 
                t.getAmount(), t.getNote(), t.getAttachmentPath());
        }
        System.out.println("[Export] Selesai mengekspor ke CSV.");
    }
}
