package io;

public final class CostChangesInput {
    private int id;

    private double infrastructureCost;

    private double productionCost;

    public CostChangesInput() {
    }

    public CostChangesInput(final int id,
                            final double infrastructureCost,
                            final double productionCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public double getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final double infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final double productionCost) {
        this.productionCost = productionCost;
    }
}
