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
    // for coding style
    private Loader() {
    }

    /**
     *
     * @param input
     * @return
     */
    public static ArrayList<Consumer> loadInputConsumers(final Input input) {
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
    public static ArrayList<Distributor> loadInputDistributors(final Input input) {
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

    /**
     * todo
     * @param consumers
     * @param distributors
     * @return
     */
    public static Output loadOutput(final List<Consumer> consumers,
                                    final List<Distributor> distributors) {
        ArrayList<ConsumerOutput> consumerOutputs = new ArrayList<ConsumerOutput>();

        for (Consumer it : consumers) {
            ConsumerOutput c = new ConsumerOutput(it.getId(),
                    it.isBankrupt(),
                    (int) it.getBudget());

            consumerOutputs.add(c);
        }

        ArrayList<DistributorOutput> distributorOutputs = new ArrayList<DistributorOutput>();

        for (Distributor it : distributors) {
            List<ContractOutput> c = new ArrayList<ContractOutput>();

            if (it.getContractList() != null) {
                for (Contract contract : it.getContractList()) {
                    ContractOutput con = new ContractOutput(contract.getConsumerId(),
                            (int) contract.getPrice(),
                            contract.getRemainedContractMonths());

                    c.add(con);
                }
            }

            DistributorOutput d = new DistributorOutput(it.getId(),
                    (int) it.getBudget(),
                    it.isBankrupt(),
                    c);

            distributorOutputs.add(d);
        }

        Output out = new Output(consumerOutputs, distributorOutputs);

        return out;
    }

}
