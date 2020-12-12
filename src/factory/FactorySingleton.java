package factory;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;

public final class FactorySingleton {
    /*
    * The singleton instance of Factory
    * */
    private static FactorySingleton instance = null;

    /*
    * Private constructor to respect the singleton pattern
    * */
    private  FactorySingleton() {
    }

    /**
     * The method used to get a single instance of Factory.
     *
     * @return The Factory instance
     */
    public static FactorySingleton getInstance() {
        if (instance == null) {
            instance = new FactorySingleton();
        }

        return instance;
    }

    /**
     * Create a new entity based on the given type.
     *
     * @param entityType The entity type to be created
     * @param id The entity id
     * @param initialBudget The entity initial budget
     * @param monthlyIncome The monthlyIncome for the consumer type
     * @param contractLength The contract length for the distributor type
     * @param initialInfrastructureCost The initial infrastructure cost for the distributor type
     * @param initialProductionCost The initial production cost for the distributor type
     * @return The new Entity
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
