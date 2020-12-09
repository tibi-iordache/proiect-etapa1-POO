package databases;

import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public final class DistributorDataBase {
    private List<Distributor> distributorList;

    public DistributorDataBase() {
        distributorList = new ArrayList<Distributor>();
    }

    public DistributorDataBase(final List<Distributor> distributorList) {
        this.distributorList = distributorList;
    }

    public List<Distributor> getDistributorList() {
        return distributorList;
    }

    public void setDistributorList(final List<Distributor> distributorList) {
        this.distributorList = distributorList;
    }
}
