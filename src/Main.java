import actions.ExecuteTurns;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import io.Output;
import io.Loader;
import io.Input;
import java.io.File;
import java.util.ArrayList;


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

        ArrayList<Consumer> consumers = Loader.loadInputConsumers(input);

        ArrayList<Distributor> distributors = Loader.loadInputDistributors(input);

        ExecuteTurns.startSimulation(input.getNumberOfTurns(),
                                    consumers,
                                    distributors,
                                    input.getMonthlyUpdates());

        Output out = Loader.loadOutput(consumers, distributors);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]), out);
    }
}
