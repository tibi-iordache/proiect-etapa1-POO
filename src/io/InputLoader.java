package io;

import entities.Consumer;
import entities.Distributor;
import factory.FactorySingleton;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;

public final class InputLoader {
    // for coding style
    private InputLoader() {
    }

    /**
     *
     * @param input
     * @return
     */
    public static ArrayList<Consumer> loadConsumers(final Input input) {
        ArrayList<Consumer> consumers = new ArrayList<Consumer>();

        List<ConsumerInput> consumersInputList = input.getInitialData().getConsumers();

        for (ConsumerInput iterator : consumersInputList) {
            int id = iterator.getId();

            double initialBudget = iterator.getInitialBudget();

            double monthlyIncome = iterator.getMonthlyIncome();

            consumers.add((Consumer) FactorySingleton.getInstance()
                                                     .createEntity(CONSUMER,
                                                                    id,
                                                                    initialBudget,
                                                                    monthlyIncome,
                                                                    0,
                                                                    0,
                                                                    0));
        }

        return consumers;
    }

    /**
     *
     * @param input
     * @return
     */
    public static ArrayList<Distributor> loadDistribuitors(final Input input) {
        ArrayList<Distributor> distributors = new ArrayList<Distributor>();

        List<DistributorInput> distributorsInputList = input.getInitialData().getDistributors();

        for (DistributorInput iterator : distributorsInputList) {
            int id = iterator.getId();

            double initialBudget = iterator.getInitialBudget();

            int contractLength = iterator.getContractLength();

            double initialInfrastructureCost = iterator.getInitialInfrastructureCost();

            double initialProductionCost = iterator.getInitialProductionCost();

            distributors.add((Distributor) FactorySingleton.getInstance()
                                                            .createEntity(DISTRIBUTOR,
                                                                        id,
                                                                        initialBudget,
                                                                        0,
                                                                        contractLength,
                                                                        initialInfrastructureCost,
                                                                        initialProductionCost));
        }

        return distributors;
    }

}
