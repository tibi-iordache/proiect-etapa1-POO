package entities;

public final class Consumer {
//    TODO
    private int id;

    private double initialBudget;

    private double monthlyIncome;

    // place holder
    private String contract;

    public Consumer() {

    }

    public Consumer(final int id,
                    final double initialBudget,
                    final double monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final double initialBudget) {
        this.initialBudget = initialBudget;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", initialBudget=" + initialBudget
                + ", monthlyIncome=" + monthlyIncome
                + ", contract='" + contract + '\'' + '}';
    }
}
