package entities;

import utils.Contract;

public final class Consumer extends Entity {
    private double monthlyIncome;

    private Contract contract;

    private boolean isInDebt;

    private boolean isBankrupt;

    public Consumer() {

    }

    public Consumer(final int id,
                    final double initialBudget,
                    final double monthlyIncome) {
        super(id, initialBudget);

        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public boolean isInDebt() {
        return isInDebt;
    }

    public void setInDebt(final boolean inDebt) {
        isInDebt = inDebt;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }
}
