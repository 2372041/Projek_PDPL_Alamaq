package models;

import java.util.Date;

public class FinancialGoal {
    private int goalId;
    private int userId;
    private String title;
    private double targetAmount;
    private double currentAmount;
    private Date deadline;
    private String status; // e.g., "Active", "Achieved"

    public FinancialGoal(int goalId, int userId, String title, double targetAmount, Date deadline) {
        this.goalId = goalId;
        this.userId = userId;
        this.title = title;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.deadline = deadline;
        this.status = "Active";
    }

    public void updateProgress(double amount) {
        this.currentAmount += amount;
        if (isAchieved()) {
            this.status = "Achieved";
        }
    }

    public boolean isAchieved() {
        return this.currentAmount >= this.targetAmount;
    }

    public double getRemainingAmount() {
        return this.targetAmount - this.currentAmount;
    }

    public double getProgressPercent() {
        return (this.currentAmount / this.targetAmount) * 100;
    }

    public void extendDeadline(Date newDate) {
        this.deadline = newDate;
    }
}
