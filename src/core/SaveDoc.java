package core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveDoc {
//    private String filename;
    public SaveDoc(String filename,String line) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
            bw.append(line);
            bw.append("\n");
            bw.close();
            System.out.println(":: " + line + " => " + filename);

        } catch (IOException e) {
            System.out.println("ERROR!!! " + e.getMessage());
        }
    }


}
