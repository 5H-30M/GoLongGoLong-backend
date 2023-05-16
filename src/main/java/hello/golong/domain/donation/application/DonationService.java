package hello.golong.domain.donation.application;

import hello.golong.domain.donation.dao.DonationRepository;
import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.donation.dto.DonationDto;
import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.post.application.PostService;
import org.springframework.stereotype.Service;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final PostService postService;
    private final MemberService memberService;

    public DonationService(DonationRepository donationRepository, PostService postService, MemberService memberService) {
        this.donationRepository = donationRepository;
        this.postService = postService;
        this.memberService = memberService;
    }

    public DonationDto createDonation(DonationDto donationDto) {
        //web3js 연동 -> smartContractService

        //스마트컨트랙트가 성공적으로 체결됐다면
        Donation donation = this.buildDonation(donationDto);
        donationRepository.save(donation);
        donationDto.setId(donation.getId());

        memberService.updateGOLtokens(donationDto.getMemberId(), donationDto.getAmount());
        postService.updateDonationInformation(donationDto.getPostId(), donationDto.getAmount());

        return donationDto;
    }

    public Donation buildDonation(DonationDto donationDto) {
        return Donation.builder()
                .postId(donationDto.getPostId())
                .memberId(donationDto.getMemberId())
                .transactionId(donationDto.getTransactionId())
                .amount(donationDto.getAmount())
                .build();
    }

}
