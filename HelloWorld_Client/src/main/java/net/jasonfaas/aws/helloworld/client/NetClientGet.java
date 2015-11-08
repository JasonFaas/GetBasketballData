package net.jasonfaas.aws.helloworld.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientGet {
    public String getClientCall(String spec) {

        try {

            URL url = new URL(spec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            StringBuilder stringBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
//                System.out.println(output);
                stringBuilder.append(output + "\n");
            }

            conn.disconnect();
            return stringBuilder.toString();
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }
}