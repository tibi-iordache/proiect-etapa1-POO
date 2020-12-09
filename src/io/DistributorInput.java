package io;

public final class DistributorInput {
    private int id;

    private int contractLength;

    private double initialBudget;

    private double initialInfrastructureCost;

    private double initialProductionCost;

    public DistributorInput() {
    }

    public DistributorInput(final int id,
                            final int contractLength,
                            final double initialBudget,
                            final double initialInfrastructureCost,
                            final double initialProductionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.initialProductionCost = initialProductionCost;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final double initialBudget) {
        this.initialBudget = initialBudget;
    }

    public double getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final double initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public double getInitialProductionCost() {
        return initialProductionCost;
    }

    public void setInitialProductionCost(final double initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }
}
