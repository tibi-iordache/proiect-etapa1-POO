package actions;

import entities.Consumer;
import entities.Distributor;
import factory.FactorySingleton;
import io.ConsumerInput;
import io.CostChangesInput;
import io.MonthlyUpdatesInput;
import utils.Contract;
import static utils.Constants.CONSUMER;
import java.util.Comparator;
import java.util.List;

public final class ExecuteTurns {
    // for coding style
    private ExecuteTurns() {
    }

    /**
     *
     * @param numberOfTurns
     * @param consumers
     * @param distributors
     * @param monthlyUpdatesInputs
     */
    public static void startSimulation(final int numberOfTurns,
                                       final List<Consumer> consumers,
                                       final List<Distributor> distributors,
                                       final List<MonthlyUpdatesInput> monthlyUpdatesInputs) {
        for (int i = 0; i <= numberOfTurns; i++) {
            // read the updates
            if (i != 0) {
                // exclude the initial round

                for (MonthlyUpdatesInput it : monthlyUpdatesInputs) {
                    for (CostChangesInput costChanges : it.getCostsChanges()) {
                        if (costChanges != null) {
                            // set the new infrastructure cost
                            distributors.get(costChanges.getId())
                                    .setInfrastructureCost(costChanges
                                            .getInfrastructureCost());

                            // set the new production cost
                            distributors.get(costChanges.getId())
                                    .setProductionCost(costChanges
                                            .getProductionCost());
                        }
                    }

                    // add new consumers
                    for (ConsumerInput newConsumer : it.getNewConsumers()) {
                        if (newConsumer != null) {
                            int id = newConsumer.getId();

                            double initialBudget = newConsumer.getInitialBudget();

                            double monthlyIncome = newConsumer.getMonthlyIncome();

                            consumers.add((Consumer) FactorySingleton.getInstance()
                                                            .createEntity(CONSUMER,
                                                                    id,
                                                                    initialBudget,
                                                                    monthlyIncome,
                                                                    0,
                                                                    0,
                                                                    0));
                        }
                    }
                }
            }


            // calculate the new contract for each distributor
            for (Distributor it : distributors) {
                double contractPrice;

                if (i == 0) {
                    // initial round
                    contractPrice = computeContractCost(it, 0);
                } else {
                    contractPrice = computeContractCost(it, consumers.size());
                }

                it.setContractPrice(contractPrice);
            }

            distributors.sort(Comparator.comparing(Distributor::getContractPrice));
            Distributor cheapestDistributor = distributors.get(0);

            // each consumer chooses a contract
            for (Consumer it : consumers) {
                // compute each consumer current budget
                double consumerBudget = it.getBudget();
                consumerBudget += it.getMonthlyIncome();

                // check if it already has a contract
                if (!it.isBankrupt() && (it.getContract() == null
                        || it.getContract().getRemainedContractMonths() == 0)) {

                    // check if he can afford the lowest price
                    if (consumerBudget >= cheapestDistributor.getContractPrice()) {
                        // sign the contract
                        Contract contract = new Contract(it.getId(),
                                cheapestDistributor.getId(),
                                cheapestDistributor.getContractPrice(),
                                cheapestDistributor.getContractLength() - 1);

                        cheapestDistributor.getContractList().add(contract);

                        it.setContract(contract);

                        consumerBudget -= cheapestDistributor.getContractPrice();

                        it.setBudget(consumerBudget);
                    } else {
                        // sign the contract in debt
                        double oldPrice = cheapestDistributor.getContractPrice();
                        double debtPrice = Math.round(Math.floor(1.2 * oldPrice)) + oldPrice;

                        Contract contract = new Contract(it.getId(),
                                cheapestDistributor.getId(),
                                debtPrice,
                                cheapestDistributor.getContractLength() - 1);

                        cheapestDistributor.getContractList().add(contract);

                        it.setContract(contract);

                        consumerBudget -= cheapestDistributor.getContractPrice();

                        it.setBudget(consumerBudget);

                        it.setInDebt(true);
                    }

                } else {
                    // the consumer already has a contract

                    // check if he can still aford it
                    if (consumerBudget >= it.getContract().getPrice()) {
                        it.getContract().setRemainedContractMonths(it
                                .getContract().getRemainedContractMonths() - 1);

                        // set the consumer the new budget after paying the contract
                        consumerBudget -= it.getContract().getPrice();

                        it.setBudget(consumerBudget);
                    } else if (it.isInDebt()) {
                        it.setBankrupt(true);
                    } else {
                        // modify the contract
                        double oldPrice = it.getContract().getPrice();
                        it.getContract().setPrice(Math
                                            .round(Math.floor((1.2 * oldPrice)) + oldPrice));

                        it.getContract().setRemainedContractMonths(it
                                .getContract().getRemainedContractMonths() - 1);

                        // set the consumer the new budget after paying the contract
                        consumerBudget -= it.getContract().getPrice();

                        it.setBudget(consumerBudget);

                        it.setInDebt(true);
                    }
                }
            }

            // each distributor pay the monthlyExpenses
            for (Distributor it : distributors) {
                double monthlyExpenses;

                if (it.getContractList() == null) {
                    // it has 0 consumers
                    monthlyExpenses = it.getInfrastructureCost();
                } else {
                    monthlyExpenses = it.getInfrastructureCost()
                            + it.getProductionCost() * it.getContractList().size();
                }

                it.setBudget(it.getBudget() - monthlyExpenses);

                if (it.getBudget() < 0) {
                    it.setBankrupt(true);
                }
            }
        }
    }

    /**
     * Computes the price for the contract of a given Distributor, depending on the numbers of
     * consumers that it requires.
     *
     * @param distributor The distributor witch will make the contract
     * @param numberOfConsumers The consumers that the distributor will want to attract
     * @return  The price of the contract
     */
    public static double computeContractCost(final Distributor distributor,
                                             final int numberOfConsumers) {
        double price;
        double procent = 0.2d;
        double profit  = Math.round(Math.floor(procent * distributor.getProductionCost()));

        if (numberOfConsumers == 0) {
            price = distributor.getInfrastructureCost()
                    + distributor.getProductionCost()
                    + profit;
        } else {
            price = Math.round(Math.floor(distributor
                    .getInfrastructureCost() / numberOfConsumers)
                    + distributor.getProductionCost() + profit);
        }

        return price;
    }
}
