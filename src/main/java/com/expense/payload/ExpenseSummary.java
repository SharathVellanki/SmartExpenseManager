package com.expense.payload;

public class ExpenseSummary {
    private long count;
    private double total;
    private double average;

    public ExpenseSummary(long count, double total) {
        this.count = count;
        this.total = total;
        this.average = count > 0 ? total / count : 0.0;
    }

    // Getters
    public long getCount() { return count; }
    public double getTotal() { return total; }
    public double getAverage() { return average; }
}
