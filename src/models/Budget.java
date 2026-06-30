package models;

public class Budget {
    private int budgetId;
    private int categoryId;
    private int userId;
    private double limitAmount;
    private int month;
    private int year;
    private double currentSpending;

    public Budget(int budgetId, int categoryId, int userId, double limitAmount, int month, int year) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.limitAmount = limitAmount;
        this.month = month;
        this.year = year;
        this.currentSpending = 0.0;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public double getPercentageUsed() {
        return (currentSpending / limitAmount) * 100;
    }

    public boolean isAlertTriggered() {
        return currentSpending >= (limitAmount * 0.8);
    }

    public void reset() {
        this.currentSpending = 0.0;
    }

    public void updateLimit(double newAmount) {
        this.limitAmount = newAmount;
    }
}
