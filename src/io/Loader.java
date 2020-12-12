package io;

import entities.Consumer;
import entities.Distributor;
import factory.FactorySingleton;
import utils.Contract;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;

public final class Loader {
    /*
    * for coding style
    * */
    private Loader() {
    }

    /**
     * Loads all the given input for consumers into an ArrayList of Consumers.
     *
     * @param input The input class
     * @return An ArrayList of Consumers
     */
    public static ArrayList<Consumer> loadInputConsumers(final Input input) {
        ArrayList<Consumer> consumers = new ArrayList<Consumer>();

        List<ConsumerInput> consumersInputList = input.getInitialData().getConsumers();

        // iterate through each consumer from the input
        for (ConsumerInput iterator : consumersInputList) {
            int id = iterator.getId();

            double initialBudget = iterator.getInitialBudget();

            double monthlyIncome = iterator.getMonthlyIncome();

            // use the factory to create new consumer instances
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
     * Loads all the given input for distributors into an ArrayList of Distributors.
     *
     * @param input The Input class
     * @return An ArrayList of Distributors
     */
    public static ArrayList<Distributor> loadInputDistributors(final Input input) {
        ArrayList<Distributor> distributors = new ArrayList<Distributor>();

        List<DistributorInput> distributorsInputList = input.getInitialData().getDistributors();

        // iterate through each distributor from the input
        for (DistributorInput iterator : distributorsInputList) {
            int id = iterator.getId();

            double initialBudget = iterator.getInitialBudget();

            int contractLength = iterator.getContractLength();

            double initialInfrastructureCost = iterator.getInitialInfrastructureCost();

            double initialProductionCost = iterator.getInitialProductionCost();

            // use the factory to create new distributor instances
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

    /**
     * Loads the results of the simulation into an Output class.
     *
     * @param consumers An ArrayList of Consumers
     * @param distributors An ArrayList of Distributors
     * @return The Output class
     */
    public static Output loadOutput(final List<Consumer> consumers,
                                    final List<Distributor> distributors) {
        // compute each consumer data after the simulation
        ArrayList<ConsumerOutput> consumersOutput = new ArrayList<ConsumerOutput>();

        for (Consumer iterator : consumers) {
            ConsumerOutput c = new ConsumerOutput(iterator.getId(),
                                                  iterator.isBankrupt(),
                                                  (int) iterator.getBudget());
            consumersOutput.add(c);
        }

        // compute each distributor data after the simulation
        ArrayList<DistributorOutput> distributorsOutput = new ArrayList<DistributorOutput>();

        for (Distributor iterator : distributors) {
            List<ContractOutput> contractsOutput = new ArrayList<ContractOutput>();

            if (iterator.getContractList() != null) {
                for (Contract contractIterator : iterator.getContractList()) {
                    ContractOutput con = new ContractOutput(contractIterator.getConsumerId(),
                                                    (int) contractIterator.getPrice(),
                                                    contractIterator.getRemainedContractMonths());
                    contractsOutput.add(con);
                }
            }

            DistributorOutput d = new DistributorOutput(iterator.getId(),
                                                        (int) iterator.getBudget(),
                                                        iterator.isBankrupt(),
                                                        contractsOutput);
            distributorsOutput.add(d);
        }

        return new Output(consumersOutput, distributorsOutput);
    }
}
