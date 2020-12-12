package entities;

import utils.Contract;
import java.util.ArrayList;

public final class Distributor extends Entity {
    /*
    * The length of the distributor's contract
    * */
    private int contractLength;

    /*
    * Distributor's infrastructure cost
    * */
    private double infrastructureCost;

    /*
    * Distributor's production cost
    * */
    private double productionCost;

    /*
    * Distributor's current round contract price
    * */
    private double contractPrice;

    /*
    * List that contains all the distributor's contracts
    * */
    private ArrayList<Contract> contractList;

    /*
    * Distributor's bankrupt status
    * */
    private boolean isBankrupt;

    public Distributor() {
    }

    public Distributor(final int id,
                       final int contractLength,
                       final double initialBudget,
                       final double infrastructureCost,
                       final double productionCost) {
        super(id, initialBudget);

        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        contractList = new ArrayList<Contract>();
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
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

    public double getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(final double contractPrice) {
        this.contractPrice = contractPrice;
    }

    public ArrayList<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(final ArrayList<Contract> contractList) {
        this.contractList = contractList;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }
}
