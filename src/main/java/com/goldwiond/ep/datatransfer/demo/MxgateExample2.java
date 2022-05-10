package com.goldwiond.ep.datatransfer.demo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MxgateExample2 {
    public static void main(String[] args) throws Exception {
        MxgateExample2 http = new MxgateExample2();
        http.sendingPostRequest();
    }

    // HTTP Post request
    private void sendingPostRequest() throws Exception {
        // mxgate监听在localhost的8086端口
        String url = "http://10.200.50.142:8086/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");


        String postJsonData = "realtimedata_4\n" +
                "630008|630008017|2022-04-11 13:26:25.199|18.34|4.21|22.89|14.01|11.68|15.49|12.42|16.0|17.5|24.88|19.56|0.44|9.63|26.34|21.45|27.78|7.68" +
                "|6.87|26.42|20.05|21.6|0.69|3.01|8.57|7.1|19.75|13.02|14.11|9.83|20.88|15.33|9.68|9.33|27.0|29.13|16.52|21.19|" +
                "9.88|6.31|10.58|28.25|23.49|23.29|20.86|8.5|21.23|13.65|2.79|9.06|26.43|12.61|8.06|21.82|24.1|24.63|8.13|7.45|" +
                "22.38|25.02|21.84|20.16|24.96|15.47|27.41|25.92|10.84|22.69|6.51|1.83|24.89|7.84|12.88|14.45|21.99|22.78|29.21|18.83|" +
                "9.17|17.33|25.33|9.56|23.96|9.28|16.52|8.64|22.89|16.46|24.28|3.79|2.37|17.18|2.19|13.67|4.46|12.15|7.11|14.77|" +
                "22.42|27.58|1.02|16.76|15.77|15.06|20.55|20.95|1.3|10.04|10.72|8.06|15.64|15.87|25.48|8.05|6.19|5.89|21.67|6.61|" +
                "10.58|0.86|23.32|18.24|10.64|3.57|0|0|test|1\n";


        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        // 数据有中文时，可以通过postJsonData.getBytes("UTF-8")编码
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        System.out.println(response);
    }
}
