package hello.golong.domain.donation.api;

import hello.golong.domain.donation.application.DonationService;
import hello.golong.domain.donation.dto.DonationDto;
import hello.golong.domain.donation.dto.TrackingDto;
import hello.golong.domain.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.exceptions.TransactionException;
import retrofit2.http.Path;

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
    public ResponseEntity<List<TrackingDto>> getDonations(@PathVariable("member_id") Long member_id) {
        return ResponseEntity.ok().body(donationService.findDonationsByMemberId(member_id));
    }


    @PostMapping("/rescuer")
    public ResponseEntity<DonationDto> giveTokenToRescuer(@RequestBody DonationDto donationDto) throws TransactionException, IOException {
        return ResponseEntity.ok().body(donationService.giveTokensToRescuer(donationDto));
    }

    @PostMapping("/{member_id}")
    public ResponseEntity<Void> giveSEthToMember(@PathVariable Long member_id) throws TransactionException, IOException {
        donationService.giveSEthToMember(member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
