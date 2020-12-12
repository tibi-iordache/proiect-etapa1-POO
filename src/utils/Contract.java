package utils;

public final class Contract {
    /*
    * The id of the consumer that signed the contract
    * */
    private int consumerId;

    /*
    * The id of the distributor that signed the contract
    * */
    private int distributorId;

    /*
    * The price of the contract
    * */
    private double price;

    /*
    * The length of the contract
    * */
    private int remainedContractMonths;

    public Contract() {
    }

    public Contract(final int consumerId,
                    final int distributorId,
                    final double price,
                    final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(final int distributorId) {
        this.distributorId = distributorId;
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
}
