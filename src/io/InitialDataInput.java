package io;

import entities.Consumer;
import entities.Distributor;

import java.util.List;

public final class InitialDataInput {
    private List<Consumer> consumers;

    private List<Distributor> distributors;

    public InitialDataInput() {

    }

    public InitialDataInput(final List<Consumer> consumers,
                            final List<Distributor> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }
}
