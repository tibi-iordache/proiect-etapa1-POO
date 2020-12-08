package io;

import entities.Consumer;

import java.util.List;

public final class MonthlyUpdatesInput {
    private List<Consumer> newConsumers;

    private List<CostChangesInput> costsChanges;

    public MonthlyUpdatesInput() {

    }

    public MonthlyUpdatesInput(final List<Consumer> newConsumers,
                                      final List<CostChangesInput> costsChanges) {
        this.newConsumers = newConsumers;
        this.costsChanges = costsChanges;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChangesInput> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostChangesInput> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
