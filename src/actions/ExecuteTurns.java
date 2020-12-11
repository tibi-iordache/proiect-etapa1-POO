package actions;

import entities.Consumer;
import entities.Distributor;
import factory.FactorySingleton;
import io.ConsumerInput;
import io.CostChangesInput;
import io.MonthlyUpdatesInput;
import utils.Contract;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static utils.Constants.CONSUMER;
import static utils.Constants.PROFIT_PRECENTAGE;
import static utils.Constants.DEBT_PRECENTAGE;


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
            doUpdate(i, consumers, distributors, monthlyUpdatesInputs);

            computeContractCostList(i, distributors);

            Distributor bestDistr = null;

            if (distributors.stream().filter(distributor -> !distributor.isBankrupt())
                    .min(Comparator.comparing(Distributor::getContractPrice))
                    .isPresent()) {
                bestDistr = distributors.stream()
                        .filter(distributor -> !distributor.isBankrupt()).min(Comparator
                                .comparing(Distributor::getContractPrice)).get();
            }

            chooseContracts(consumers, distributors, bestDistr);

            payTotalCost(consumers, distributors);
        }
    }

    /**
     * todo
     * @param currentTurn
     * @param consumers
     * @param distributors
     * @param monthlyUpdatesInputs
     */
    public static void doUpdate(final int currentTurn,
                                final List<Consumer> consumers,
                                final List<Distributor> distributors,
                                final List<MonthlyUpdatesInput> monthlyUpdatesInputs) {
        if (currentTurn != 0) {
            MonthlyUpdatesInput monthUpdate = monthlyUpdatesInputs.get(currentTurn - 1);

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
    }

    /**
     * todo
     * @param currentTurn
     * @param distributors
     */
    public static void computeContractCostList(final int currentTurn,
                                               final List<Distributor> distributors) {
        // compute the contract price for each distributor
        if (currentTurn == 0) {
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
                        it.setContractPrice(Math
                                .round(computeContractCost(it, 0)));
                    } else {
                        it.setContractPrice(Math
                                .round(computeContractCost(it, it.getContractList().size())));
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

        double profit  = Math.round(Math.floor(PROFIT_PRECENTAGE
                * distributor.getProductionCost()));

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

    /**
     * todo
     * @param consumers
     * @param distributors
     * @param bestDistr
     */
    public static void chooseContracts(final List<Consumer> consumers,
                                       final List<Distributor> distributors,
                                       final Distributor bestDistr) {
        // each consumer should now choose a contract
        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                consumer.setBudget(Math.round(consumer.getBudget()
                        + consumer.getMonthlyIncome()));

                if (consumer.getContract() != null) {
                    if (consumer.getContract().getRemainedContractMonths() == 0) {
                        // requires new contract
                        if (consumer.isInDebt()) {
                            double debtPrice = Math.round(Math
                                    .floor(DEBT_PRECENTAGE * consumer.getContract().getPrice()))
                                    + consumer.getContract().getPrice();

                            int oldDistributorId = consumer.getContract().getDistributorId();

                            if (consumer.getBudget() >= (bestDistr.getContractPrice()
                                    + debtPrice)) {
                                // first remove the old contract
                                distributors.get(consumer.getContract().getDistributorId())
                                        .getContractList().remove(consumer.getContract());

                                signNewContract(consumer, bestDistr, false);

                                // pay previus debt
                                consumer.setBudget(Math.round(consumer.getBudget() - debtPrice));

                                // pay the debt to the old dist
                                distributors.get(oldDistributorId)
                                        .setBudget(distributors.get(oldDistributorId).getBudget()
                                                + debtPrice);

                                consumer.setInDebt(false);
                            } else {
                                consumer.setBankrupt(true);
                            }
                        } else {
                            // he is not in debt, can make new contract
                            // first remove the old contract
                            distributors.get(consumer.getContract().getDistributorId())
                                    .getContractList().remove(consumer.getContract());

                            if (consumer.getBudget() >= bestDistr.getContractPrice()) {
                                // sign the new contract

                                signNewContract(consumer, bestDistr, false);
                            } else {
                                // make contract in debt
                                signNewContract(consumer, bestDistr, true);
                            }
                        }
                    } else if (consumer.isInDebt()) {
                        double debtPrice = Math.round(Math
                                .floor(DEBT_PRECENTAGE * consumer.getContract().getPrice()))
                                + consumer.getContract().getPrice();

                        payCurrentContract(consumer, distributors, true, debtPrice);
                    } else if (consumer.getBudget() >= consumer.getContract().getPrice()) {
                        // he can pay the current month no problem
                        payCurrentContract(consumer, distributors, false, 0);

                    } else {
                        // he doesnt have money, put him in debt
                        consumer.setInDebt(true);

                        consumer.getContract()
                                .setRemainedContractMonths(consumer
                                        .getContract().getRemainedContractMonths() - 1);
                    }
                } else {
                    // consumer requires a new contract
                    if (consumer.getBudget() >= bestDistr.getContractPrice()) {
                        signNewContract(consumer, bestDistr, false);
                    } else {
                        // he will be put in debt
                        signNewContract(consumer, bestDistr, true);
                    }
                }
            }
        }
    }

    /**
     * todo
     * @param consumer
     * @param distributor
     */
    public static void signNewContract(final Consumer consumer,
                                       final Distributor distributor,
                                       final boolean signInDebt) {
        // sign the new contract
        Contract newContract = new Contract(consumer.getId(),
                distributor.getId(),
                distributor.getContractPrice(),
                distributor.getContractLength() - 1);

        consumer.setContract(newContract);

        distributor.getContractList().add(newContract);

        if (signInDebt) {
            consumer.setInDebt(true);
        } else {
            consumer.setBudget(Math.round(consumer.getBudget()
                    - consumer.getContract().getPrice()));

            distributor.setBudget(Math.round(distributor.getBudget()
                    + consumer.getContract().getPrice()));
        }
    }

    /**
     * todo
     * @param consumer
     * @param distributors
     * @param isInDebt
     * @param debtPrice
     */
    public static void payCurrentContract(final Consumer consumer,
                                          final List<Distributor> distributors,
                                          final boolean isInDebt,
                                          final double debtPrice) {
        if (isInDebt) {
            if (consumer.getBudget() >= debtPrice) {
                // pay the debt
                consumer.setBudget(Math.round(consumer.getBudget()
                        - debtPrice));

                consumer.getContract()
                        .setRemainedContractMonths(consumer
                                .getContract().getRemainedContractMonths() - 1);

                int distributorId = consumer.getContract().getDistributorId();
                distributors.get(distributorId)
                        .setBudget(Math.round(distributors
                                .get(distributorId).getBudget()
                                + debtPrice));

                consumer.setInDebt(false);
            } else {
                consumer.setBankrupt(true);
            }
        } else {
            // pay the contract
            consumer.setBudget(Math.round(consumer.getBudget()
                    - consumer.getContract().getPrice()));

            consumer.getContract()
                    .setRemainedContractMonths(consumer
                            .getContract().getRemainedContractMonths() - 1);

            int distributorId = consumer.getContract().getDistributorId();

            distributors.get(distributorId)
                    .setBudget(distributors.get(distributorId).getBudget()
                            + consumer.getContract().getPrice());
        }
    }

    /**
     * todo
     * @param consumers
     * @param distributors
     */
    public static void payTotalCost(final List<Consumer> consumers,
                                    final List<Distributor> distributors) {
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
