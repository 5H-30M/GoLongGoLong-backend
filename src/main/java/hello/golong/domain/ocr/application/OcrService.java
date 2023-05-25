package hello.golong.domain.ocr.application;

import hello.golong.domain.ocr.dto.OcrDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OcrService {

    private final ClovaOCRService clovaOCRService;

    public OcrService(ClovaOCRService clovaOCRService) {
        this.clovaOCRService = clovaOCRService;
    }

    //public void certifyPreReceipt(MultipartFile file)
    public OcrDto certifyPreReceipt(String str) {
        //TODO : 임시 코드
        //clovaOCRService.requestClovaOCR(file);
        return clovaOCRService.requestClovaOCR(str);
    }
}
