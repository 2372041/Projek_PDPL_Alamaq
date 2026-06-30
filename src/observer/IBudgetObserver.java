package observer;
public interface IBudgetObserver {
    void update(double currentExpense, double budgetLimit);
}