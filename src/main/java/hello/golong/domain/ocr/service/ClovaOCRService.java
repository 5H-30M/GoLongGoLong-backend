package hello.golong.domain.ocr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ClovaOCRService {

    @Value("${clova-ocr-secret-key}")
    private String secretKey;

    @Value("${clova-ocr-api-gateway}")
    private String apiURL;


    public String requestClovaOCR() throws IOException {

        URL url = new URL(apiURL);

        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        return "TOTALPRICE";
    }
}
