package factory;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;

public final class FactorySingleton {
    private static FactorySingleton instance = null;

    private  FactorySingleton() {

    }

    /**
     *
     * @return
     */
    public static FactorySingleton getInstance() {
        if (instance == null) {
            instance = new FactorySingleton();
        }

        return instance;
    }

    /**
     *
     * @param entityType
     * @return
     */
    public Entity createEntity(final String entityType,
                               final int id,
                               final double initialBudget,
                               final double monthlyIncome,
                               final int contractLength,
                               final double initialInfrastructureCost,
                               final double initialProductionCost) {
        return switch (entityType) {
            default -> null;

            case CONSUMER -> new Consumer(id, initialBudget, monthlyIncome);

            case DISTRIBUTOR -> new Distributor(id, contractLength, initialBudget,
                                                initialInfrastructureCost, initialProductionCost);
        };
    }
}
