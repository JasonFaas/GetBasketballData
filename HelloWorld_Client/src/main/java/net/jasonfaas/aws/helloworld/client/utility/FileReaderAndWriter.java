package net.jasonfaas.aws.helloworld.client.utility;

import java.io.*;

/**
 * Created by jasonfaas on 11/8/15.
 */
public class FileReaderAndWriter {
    public static StringBuilder readContentFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        return sb;
    }

    public static void writeToFile(String fileName, String content) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(content);
        writer.close();
    }
}
