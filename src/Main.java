import actions.ExecuteTurns;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import io.*;
import utils.Contract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Main {
    // for coding style
    private Main() {
    }

    /**
     * ceva
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        ArrayList<Consumer> consumers = InputLoader.loadConsumers(input);

        ArrayList<Distributor> distributors = InputLoader.loadDistribuitors(input);

        ExecuteTurns.startSimulation(input.getNumberOfTurns(),
                                    consumers,
                                    distributors,
                                    input.getMonthlyUpdates());

        ArrayList<ConsumerOutput> consumerOutputs = new ArrayList<ConsumerOutput>();

        for (Consumer it : consumers) {
            ConsumerOutput c = new ConsumerOutput(it.getId(),
                                                it.isBankrupt(),
                                                (int) it.getBudget());

            consumerOutputs.add(c);
        }

        ArrayList<DistributorOutput> distributorOutputs = new ArrayList<DistributorOutput>();

        for (Distributor it : distributors) {
            List<ContractOutput> c = new ArrayList<ContractOutput>();

            if (it.getContractList() != null) {
                for (Contract contract : it.getContractList()) {
                    ContractOutput con = new ContractOutput(contract.getConsumerId(),
                                                            (int) contract.getPrice(),
                                                            contract.getRemainedContractMonths());

                    c.add(con);
                }
            }

            DistributorOutput d = new DistributorOutput(it.getId(),
                                                        (int) it.getBudget(),
                                                        it.isBankrupt(),
                                                        c);

            distributorOutputs.add(d);
        }

//        System.out.println(args[0]);

        Output out = new Output(consumerOutputs, distributorOutputs);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]), out);
    }
}
