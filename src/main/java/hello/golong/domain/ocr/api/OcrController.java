package hello.golong.domain.ocr.api;

import hello.golong.domain.ocr.dto.OcrDto;
import hello.golong.domain.ocr.service.OcrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pre-receipt")
public class OcrController {

    private final OcrService ocrService;

    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }


    @PostMapping
    ResponseEntity<OcrDto> certifyPreReceipt(@RequestParam("file")MultipartFile file) {
        return ResponseEntity.ok(ocrService.certifyPreReceipt(file));
    }
}
