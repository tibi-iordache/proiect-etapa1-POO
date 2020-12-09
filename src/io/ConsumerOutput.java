package io;

public final class ConsumerOutput {
    private int id;

    private boolean isBankrupt;

    private double budget;

    public ConsumerOutput() {
    }

    public ConsumerOutput(final int id,
                          final boolean isBankrupt,
                          final double budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(final double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "ConsumerOutput{" +
                "id=" + id +
                ", isBankrupt=" + isBankrupt +
                ", budget=" + budget +
                '}';
    }
}
