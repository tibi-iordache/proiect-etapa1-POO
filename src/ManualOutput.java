//import com.sun.tools.javac.Main;
//import factory.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.Input;

import java.io.File;

public final class ManualOutput {
    public static void main(final String[] args) {
        String[] param = new String[2];
        param[0] = "/home/tibi/Automatica/an2/sem1/POO/teme/proiect1/github/private-proiect-POO-etapa1/checker/resources/in/basic_1.json";
        param[1] = "/home/tibi/Automatica/an2/sem1/POO/teme/proiect1/github/private-proiect-POO-etapa1/my_out/basic_1_out.json";

        try {
            Main.main(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
