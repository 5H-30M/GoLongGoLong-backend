package hello.golong.domain.ocr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClovaOCRService {

    @Value("${clova-ocr-secret-key}")
    private String secretKey;

    @Value("${clova-ocr-api-gateway}")
    private String apiURL;




}
