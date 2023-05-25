package hello.golong.domain.donation.api;

import hello.golong.domain.donation.application.DonationService;
import hello.golong.domain.donation.dto.DonationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody DonationDto donationDto) throws TransactionException, IOException {
        donationDto = donationService.createDonation(donationDto);
        return ResponseEntity.ok().body(donationDto);

    }

    @GetMapping("/{member_id}")
    public ResponseEntity<List<DonationDto>> getDonations(@PathVariable("member_id") Long member_id) {
        return ResponseEntity.ok().body(donationService.findDonationsByMemberId(member_id));
    }


}
