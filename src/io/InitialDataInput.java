package io;

import java.util.List;

public final class InitialDataInput {
    /*
    * The consumers given in the input
    * */
    private List<ConsumerInput> consumers;

    /*
    * The distributors given in the input
    * */
    private List<DistributorInput> distributors;

    public InitialDataInput() {

    }

    public InitialDataInput(final List<ConsumerInput> consumers,
                            final List<DistributorInput> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public List<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorInput> distributors) {
        this.distributors = distributors;
    }
}
