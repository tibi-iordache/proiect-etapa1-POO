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
import java.util.Iterator;
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
            // do the updates
            if (i != 0) {
                MonthlyUpdatesInput monthUpdate = monthlyUpdatesInputs.get(i - 1);

                // add new consumers
                for (ConsumerInput newConsumer : monthUpdate.getNewConsumers()) {
                    consumers.add((Consumer) FactorySingleton.getInstance()
                                        .createEntity(CONSUMER, newConsumer.getId(),
                                                newConsumer.getInitialBudget(),
                                                newConsumer.getMonthlyIncome(),
                                                0,
                                                0,
                                                0));
                }

                // change the prices
                for (CostChangesInput costChanges : monthUpdate.getCostsChanges()) {
                    if (costChanges != null && !distributors.get(costChanges.getId()).isBankrupt()) {
                        // set the new infrastructure cost
                        distributors.get(costChanges.getId())
                                .setInfrastructureCost(Math.round(costChanges
                                        .getInfrastructureCost()));

                        // set the new production cost
                        distributors.get(costChanges.getId())
                                .setProductionCost(Math.round(costChanges
                                        .getProductionCost()));
                    }
                }
            }

            // compute the contract price for each distributor
            if (i == 0) {
                // initial round, no consumers
                for (Distributor it : distributors) {
                    if (!it.isBankrupt()) {
                        it.setContractPrice(Math.round(computeContractCost(it, 0)));
                    }
                }
            } else {
                // the rest of the rounds
                for (Distributor it : distributors) {
                    if (!it.isBankrupt()) {
                        if (it.getContractList() == null) {
                            it.setContractPrice(Math.round(computeContractCost(it, 0)));
                        } else {
                            it.setContractPrice(Math.round(computeContractCost(it, it.getContractList().size())));
                        }
                    }
                }
            }

            Distributor bestDistr = null;

            if (distributors.stream().min(Comparator.comparing(Distributor::getContractPrice))
                                                                        .isPresent()) {
                bestDistr = distributors.stream()
                        .filter(distributor -> !distributor.isBankrupt()).min(Comparator
                                                .comparing(Distributor::getContractPrice)).get();
            }

            // each consumer should now choose a contract
            for (Consumer consumer : consumers) {
                if (!consumer.isBankrupt()) {
                    consumer.setBudget(Math.round(consumer.getBudget() + consumer.getMonthlyIncome()));

                    if (consumer.getContract() != null) {
                        if (consumer.getContract().getRemainedContractMonths() == 0) {
                            // requires new contract

                            // first remove the old contract
                            distributors.get(consumer.getContract().getDistributorId())
                                    .getContractList().remove(consumer.getContract());

                            if (consumer.getBudget() >= bestDistr.getContractPrice()) {
                                // sign the new contract
                                Contract newContract = new Contract(consumer.getId(),
                                        bestDistr.getId(),
                                        bestDistr.getContractPrice(),
                                        bestDistr.getContractLength() - 1);

                                consumer.setContract(newContract);

                                bestDistr.getContractList().add(newContract);

                                consumer.setBudget(Math.round(consumer.getBudget()
                                        - bestDistr.getContractPrice()));

                                bestDistr.setBudget(Math.round(bestDistr.getBudget()
                                        + bestDistr.getContractPrice()));
                            } else if (!consumer.isInDebt()) {
                                // he will be put in debt
                                double debtPrice = Math.round(Math
                                        .floor(1.2 * consumer.getContract().getPrice()))
                                        + consumer.getContract().getPrice();

                                consumer.getContract().setPrice(Math.round(debtPrice));

                                consumer.setInDebt(true);
                            } else {
                                // consumer is in debt already => bankrupt

                                // remove any contract at the end
                                consumer.setBankrupt(true);
                            }
                        } else if (consumer.getBudget() >= consumer.getContract().getPrice()) {
                            // he can pay the current month no problem

                            consumer.setBudget(Math.round(consumer.getBudget()
                                    - consumer.getContract().getPrice()));

                            consumer.getContract()
                                    .setRemainedContractMonths(consumer
                                            .getContract().getRemainedContractMonths() - 1);

                            int distributorId = consumer.getContract().getDistributorId();
                            distributors.get(distributorId)
                                    .setBudget(Math.round(distributors.get(distributorId).getBudget()
                                            + consumer.getContract().getPrice()));

                        } else if (consumer.isInDebt()) {
                            consumer.setBankrupt(true);
                        } else {
                            // debt
                            double debtPrice = Math.round(Math
                                    .floor(1.2 * consumer.getContract().getPrice()))
                                    + consumer.getContract().getPrice();

                            consumer.getContract().setPrice(Math.round(debtPrice));

                            consumer.setInDebt(true);
                        }
                    } else {
                        // consumer requires a new contract

                        if (consumer.getBudget() >= bestDistr.getContractPrice()) {
                            // sign the new contract
                            Contract newContract = new Contract(consumer.getId(),
                                    bestDistr.getId(),
                                    bestDistr.getContractPrice(),
                                    bestDistr.getContractLength() - 1);

                            consumer.setContract(newContract);

                            bestDistr.getContractList().add(newContract);

                            consumer.setBudget(Math.round(consumer.getBudget()
                                    - bestDistr.getContractPrice()));

                            bestDistr.setBudget(Math.round(bestDistr.getBudget()
                                    + bestDistr.getContractPrice()));
                        } else {
                            // he will be put in debt
                            double debtPrice = Math.round(Math
                                    .floor(1.2 * bestDistr.getContractPrice()))
                                    + bestDistr.getContractPrice();

                            Contract newContract = new Contract(consumer.getId(),
                                    bestDistr.getId(),
                                    debtPrice,
                                    bestDistr.getContractLength() - 1);

                            consumer.setContract(newContract);
                            consumer.setInDebt(true);

                            bestDistr.getContractList().add(newContract);
                        }
                    }
                }

            }

            // compute total cost for distr
            for (Distributor it : distributors) {
                if (!it.isBankrupt()) {
                    double totalCost = 0d;

                    if (it.getContractList() == null) {
                        totalCost = it.getInfrastructureCost();
                    } else {
                        totalCost = it.getInfrastructureCost()
                                + it.getProductionCost() * it.getContractList().size();
                    }

                    // new budget
                    it.setBudget(Math.round(it.getBudget() - totalCost));

                    if (it.getBudget() < 0) {
                        it.setBankrupt(true);
                    }

                    Iterator<Contract> contractsIterator = it.getContractList().iterator();

                    while (contractsIterator.hasNext()) {
                        Contract current = contractsIterator.next();

                        if (consumers.get(current.getConsumerId()).isBankrupt()) {
                            contractsIterator.remove();
                        }
                    }

                    // the consumers from bankrupt distr should look for new dist
                    if (it.isBankrupt()) {
                        for (Contract c : it.getContractList()) {
                            consumers.get(c.getConsumerId()).setContract(null);
                        }
                    }
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
