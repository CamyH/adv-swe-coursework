package item;

import exceptions.InvalidItemIDException;
import interfaces.FileManager;
import java.io.*;
import java.util.ArrayList;

/**
 * Reads Item Data using JavaStream
 * @author Cameron Hunt
 */
public class ItemFileReader implements FileManager<Item, Object> {
    private final String fileName;

    public ItemFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads from a given file
     * @return an instance of type T representing the file content
     */
    @Override
    public ArrayList<Item> readFile() throws IOException {
        File itemFile = new File(fileName);
        StringBuilder stringBuilder = new StringBuilder();

        // Throw exception early if file does not exist
        if (!itemFile.exists()) throw new FileNotFoundException();

        try (FileInputStream fis = new FileInputStream(itemFile)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ingestFileContents(stringBuilder);
    }

    /**
     * Write to a given file
     * @param report all order information to be used for reporting
     */
    @Override
    public void writeToFile(Object report) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Convert StringBuilder file content to a list of item objects
     * @param fileContents the contents of the read from file
     * @return an ArrayList of type item
     */
    private ArrayList<Item> ingestFileContents(StringBuilder fileContents) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            for (String line : fileContents.toString().split("\n")) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] lineData = line.split(",");

                items.add(new Item(lineData[0], ItemCategory.valueOf(lineData[1]), Double.parseDouble(lineData[2]), lineData[3]));
            }
        } catch (InvalidItemIDException e) {
            System.err.println("Unable to add item, skipping " + e.getMessage());
        }

        return items;
    }
}
