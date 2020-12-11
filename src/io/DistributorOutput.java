package io;

import java.util.List;

public final class DistributorOutput {
    private int id;

    private int budget;

    private boolean isBankrupt;

    private List<ContractOutput> contracts;

    public DistributorOutput() {
    }

    public DistributorOutput(final int id,
                             final int budget,
                             final boolean isBankrupt,
                             final List<ContractOutput> contracts) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<ContractOutput> getContracts() {
        return contracts;
    }

    public void setContracts(final List<ContractOutput> contracts) {
        this.contracts = contracts;
    }
}
