package io;

import java.util.List;

public final class Input {
    /*
    * The numbers of rounds for the simulation
    * */
    private int numberOfTurns;

    /*
    * The initial data given
    * */
    private InitialDataInput initialData;

    /*
    * The updates given for each round
    * */
    private List<MonthlyUpdatesInput> monthlyUpdates;

    public Input() {
    }

    public Input(final int numberOfTurns,
                  final InitialDataInput initialData,
                  final List<MonthlyUpdatesInput> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.initialData = initialData;
        this.monthlyUpdates = monthlyUpdates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialDataInput getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialDataInput initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdatesInput> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdatesInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
