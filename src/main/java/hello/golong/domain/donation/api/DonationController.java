package hello.golong.domain.donation.api;

import hello.golong.domain.donation.application.DonationService;
import hello.golong.domain.donation.dto.DonationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody DonationDto donationDto) {
        donationDto = donationService.createDonation(donationDto);
        return ResponseEntity.ok().body(donationDto);

    }


}
