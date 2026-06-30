package smartexpense;

import java.util.List;
import models.Transaction;

public interface ExportStrategy {
    void export(List<Transaction> data);
}
