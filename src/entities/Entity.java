package entities;

public class Entity {
    private int id;

    private double budget;

    public Entity() {

    }

    public Entity(final int id,
                  final double budget) {
        this.id = id;
        this.budget = budget;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public double getBudget() {
        return budget;
    }

    /**
     *
     * @param budget
     */
    public void setBudget(final double budget) {
        this.budget = budget;
    }
}
