package utils;

import interfaces.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates a report of all information from that day
 * Run on program exit
 * @author Cameron Hunt
 */
public class GenerateReportFileWriter implements FileManager<Object, HashMap<String, Double>> {
    private final String fileName;

    GenerateReportFileWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Read the file (this method is not supported yet)
     *
     * @return an Object, as expected by the interface
     */
    @Override
    public Collection<Object> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Write the end of program report to the given file
     * @param items all item information to be written to the file
     */
    @Override
    public void writeToFile(HashMap<String, Double> items) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Map.Entry<String, Double> item : items.entrySet()) {
                writer.write(item.getKey() + "\t" + item.getValue() + "\n");
            }
        }
    }
}