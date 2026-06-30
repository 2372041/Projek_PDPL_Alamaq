package smartexpense;

import java.util.List;
import models.Transaction;

public class ExportManager {
    private ExportStrategy strategy;

    public void setStrategy(ExportStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeExport(List<Transaction> data) {
        if (strategy == null) {
            System.out.println("[Export] Strategi ekspor belum diatur!");
            return;
        }
        strategy.export(data);
    }
}
