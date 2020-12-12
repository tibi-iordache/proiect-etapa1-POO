import actions.Simulator;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import io.Output;
import io.Loader;
import io.Input;
import java.io.File;
import java.util.ArrayList;

import static utils.Constants.FIRST_ARG;
import static utils.Constants.SECOND_ARG;


public final class Main {
    /*
    * for coding style
    * */
    private Main() {
    }

    /**
     * The entry point of the project
     * @param args The paths to the input and output files
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        // read from the input file and compute the Input file
        ObjectMapper objectMapper = new ObjectMapper();

        Input input = objectMapper.readValue(new File(args[FIRST_ARG]), Input.class);

        // compute the consumers and distributors
        ArrayList<Consumer> consumers = Loader.loadInputConsumers(input);

        ArrayList<Distributor> distributors = Loader.loadInputDistributors(input);

        // start the simulation
        Simulator.startSimulation(input.getNumberOfTurns(),
                                     consumers,
                                     distributors,
                                     input.getMonthlyUpdates());

        // compute the Output class
        Output out = Loader.loadOutput(consumers, distributors);

        // write the results of the simulation in the output file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[SECOND_ARG]), out);
    }
}
