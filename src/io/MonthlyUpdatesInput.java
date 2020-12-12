package io;

import java.util.List;

public final class MonthlyUpdatesInput {
    /*
    * New consumers given for each round
    * */
    private List<ConsumerInput> newConsumers;

    /*
    * Cost changes given for each round
    * */
    private List<CostChangesInput> costsChanges;

    public MonthlyUpdatesInput() {
    }

    public MonthlyUpdatesInput(final List<ConsumerInput> newConsumers,
                                      final List<CostChangesInput> costsChanges) {
        this.newConsumers = newConsumers;
        this.costsChanges = costsChanges;
    }

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChangesInput> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostChangesInput> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
