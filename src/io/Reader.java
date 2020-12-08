package io;

import java.util.List;

public final class Reader {
    private int numberOfTurns;

    private InitialDataInput initialData;

    private List<MonthlyUpdatesInput> monthlyUpdates;

    public Reader() {

    }

    public Reader(final int numberOfTurns,
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

    public void setMonthlyUpdates(List<MonthlyUpdatesInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
