package hello.golong.domain.ocr.service;

import hello.golong.domain.ocr.dto.OcrDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OcrService {

    public OcrDto certifyPreReceipt(MultipartFile file) {

        Long price = 0L;
        
        return OcrDto.builder().totalPrice(price).build();

    }
}
