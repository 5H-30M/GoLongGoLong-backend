package hello.golong.domain.ocr.api;

import hello.golong.domain.ocr.application.OcrService;
import hello.golong.domain.ocr.dto.OcrDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;

@RestController
@RequestMapping("/pre-receipt")
public class OcrController {

    private final OcrService ocrService;

    //TODO: RequestParam vs RequestBody
    @PostMapping("/{file_name}")
    public ResponseEntity<OcrDto> certifyPreReceipt(@PathVariable("file_name") String file_name) {
        //ocrService.certifyPreReceipt(file);
        return ResponseEntity.ok().body(ocrService.certifyPreReceipt(file_name));
    }



    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }


}
