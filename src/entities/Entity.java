package entities;

public class Entity {
    /*
    * Entity's id
    * */
    private int id;

    /*
    * Entity's budget
    * */
    private double budget;

    public Entity() {
    }

    public Entity(final int id,
                  final double budget) {
        this.id = id;
        this.budget = budget;
    }

    /**
     * Returns the entity id, as an int.
     *
     * @return The entity id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the entity id(requires to be int).
     *
     * @param id The entity id that we want to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returns the entity budget, as a double.
     *
     * @return The entity budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the entity budget(requires to be double).
     *
     * @param budget The entity budget that we want to set
     */
    public void setBudget(final double budget) {
        this.budget = budget;
    }
}
