import com.fasterxml.jackson.databind.ObjectMapper;
import io.Reader;

import java.io.File;

public class Main {
    /**
     * ceva
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = objectMapper.readValue(new File(args[0]), Reader.class);
    }
}
