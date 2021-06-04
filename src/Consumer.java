import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consumer {

    private static StringBuilder resQ1 = new StringBuilder();
    private static String headerQ1 = "Mese,Regione,#Vaccini";
    private static StringBuilder resQ2 = new StringBuilder();
    private static String headerQ2 = "Giorno,Fascia,Regione,Previsione";

    public static void main(String[] args) {
        String fileQ1_1 = "data/2021-06-04_Q1/part-00000";
        String fileQ1_2 = "data/2021-06-04_Q1/part-00001";
        String fileQ2_1 = "data/2021-06-04_Q2/part-00000";
        String fileQ2_2 = "data/2021-06-04_Q2/part-00001";
        toCsv(fileQ1_1, fileQ1_2, resQ1, headerQ1);
        toCsv(fileQ2_1, fileQ2_2, resQ2, headerQ2);
    }

    public static void toCsv(String path1, String path2, StringBuilder buf, String header) {
        buf.append(header).append("\n");
        try (Stream<String> stream = Files.lines(Paths.get(path1))) {
            String result = stream.map(s -> s.split("[,]"))
                    .map(s -> Arrays.stream(s).collect(Collectors.joining(","))+"\n")
                    .collect(Collectors.joining());
            buf.append(result.replaceAll("[()]", "")).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf.setLength(buf.length() - 1);
        try (Stream<String> stream = Files.lines(Paths.get(path2))) {
            String result = stream.map(s -> s.split("[,]"))
                    .map(s -> Arrays.stream(s).collect(Collectors.joining(","))+"\n")
                    .collect(Collectors.joining());
            buf.append(result.replaceAll("[()]", "")).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf.setLength(buf.length() - 1);

        String filename = path1.split("/")[1];
        try (PrintWriter writer = new PrintWriter("Results/"+filename+".csv")) {
            writer.write(buf.toString());
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
