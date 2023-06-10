package hello.golong.domain.ocr.application;

import hello.golong.domain.donation.dto.DonationDto;
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
        //clovaOCRService.requestClovaOCR(file);
        return clovaOCRService.requestClovaOCR(str);
    }

    public OcrDto certifyPreReceipt(MultipartFile file) {
        return clovaOCRService.requestClovaOCR(file);
    }

}
