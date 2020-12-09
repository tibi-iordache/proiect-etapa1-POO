package io;

public final class ContractOutput {
    private int consumerId;

    private double price;

    private int remainedContractMonths;

    public ContractOutput() {
    }

    public ContractOutput(final int consumerId,
                          final double price,
                          final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    @Override
    public String toString() {
        return "ContractOutput{" +
                "consumerId=" + consumerId +
                ", price=" + price +
                ", remainedContractMonths=" + remainedContractMonths +
                '}';
    }
}
