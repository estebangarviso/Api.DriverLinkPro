package com.estebangarviso.driverlinkpro.infrastructure.adapters.smtp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Component
public class SMTPAdapter {

    @Value("${application.elasticemail.apikey}")
    private String apiKey;
    @Value("${application.elasticemail.from}")
    private String from;
    @Value("${application.elasticemail.fromName}")
    private String fromName;
    private final Logger logger = LoggerFactory.getLogger(SMTPAdapter.class);
    public String send(String subject, String body, String to, String isTransactional) {

        try {

            String encoding = "UTF-8";

            String data = "apikey=" + URLEncoder.encode(this.apiKey, encoding);
            data += "&from=" + URLEncoder.encode(from, encoding);
            data += "&fromName=" + URLEncoder.encode(fromName, encoding);
            data += "&subject=" + URLEncoder.encode(subject, encoding);
            data += "&bodyHtml=" + URLEncoder.encode(body, encoding);
            data += "&to=" + URLEncoder.encode(to, encoding);
            data += "&isTransactional=" + URLEncoder.encode(isTransactional, encoding);

            URL url = new URL("https://api.elasticemail.com/v2/email/send");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = rd.readLine();
            wr.close();
            rd.close();

            return result;
        }

        catch(Exception e) {
            logger.error("Error sending email: " + e.getMessage(), e);
            return "error " + e.getMessage();
        }
    }
}