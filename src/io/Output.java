package io;

import java.util.List;

public final class Output {
    private List<ConsumerOutput> consumers;

    private List<DistributorOutput> distributors;

    public Output() {
    }

    public Output(final List<ConsumerOutput> consumers,
                  final List<DistributorOutput> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerOutput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorOutput> distributors) {
        this.distributors = distributors;
    }
}
