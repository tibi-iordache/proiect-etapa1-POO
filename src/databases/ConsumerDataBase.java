package databases;

import entities.Consumer;

import java.util.ArrayList;
import java.util.List;

public final class ConsumerDataBase {
    private List<Consumer> consumerList;

    public ConsumerDataBase() {
        consumerList = new ArrayList<Consumer>();
    }

    public ConsumerDataBase(final List<Consumer> consumerList) {
        this.consumerList = consumerList;
    }

    public List<Consumer> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(final List<Consumer> consumerList) {
        this.consumerList = consumerList;
    }
}
