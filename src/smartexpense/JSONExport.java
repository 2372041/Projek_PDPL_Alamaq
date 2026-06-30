package smartexpense;

import java.util.List;
import models.Transaction;

public class JSONExport implements ExportStrategy {
    @Override
    public void export(List<Transaction> data) {
        System.out.println("[Export] Mengekspor " + data.size() + " transaksi ke format JSON...");
        System.out.println("[");
        for (int i = 0; i < data.size(); i++) {
            Transaction t = data.get(i);
            System.out.println("  {");
            System.out.println("    \"accountId\": " + t.getAccountId() + ",");
            System.out.println("    \"categoryId\": " + t.getCategoryId() + ",");
            System.out.println("    \"type\": \"" + t.getType() + "\",");
            System.out.println("    \"amount\": " + t.getAmount() + ",");
            System.out.println("    \"note\": \"" + t.getNote() + "\",");
            System.out.println("    \"attachmentPath\": \"" + t.getAttachmentPath() + "\"");
            System.out.print("  }");
            if (i < data.size() - 1) System.out.println(",");
            else System.out.println();
        }
        System.out.println("]");
        System.out.println("[Export] Selesai mengekspor ke JSON.");
    }
}
