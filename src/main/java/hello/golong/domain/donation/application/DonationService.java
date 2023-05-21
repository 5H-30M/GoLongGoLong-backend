package hello.golong.domain.donation.application;

import hello.golong.domain.donation.dao.DonationRepository;
import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.donation.dto.DonationDto;
import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.post.application.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final PostService postService;
    private final MemberService memberService;

    private final SmartContractService smartContractService;

    public DonationService(DonationRepository donationRepository, PostService postService, MemberService memberService, SmartContractService smartContractService) {
        this.donationRepository = donationRepository;
        this.postService = postService;
        this.memberService = memberService;
        this.smartContractService = smartContractService;
    }

    @Value("${haeun-wallet-private-key}")
    private String privateKey;//TODO : 테스트를 위해 현재 내 privateKey 직접 넣음
    //TODO : 암호화한 후에는 삭제할 내용

    public DonationDto createDonation(DonationDto donationDto) throws TransactionException, IOException {

        String memberAddress = memberService.findMember(donationDto.getMemberId()).getWalletUrl();
        String postAddress = postService.findPost(donationDto.getPostId()).getWalletUrl();


        //TODO : privateKey 복호화 알고리즘으로 처리하기
        String transactionId = smartContractService.transfer(postAddress, memberAddress, privateKey, donationDto.getAmount());

        donationDto.setTransactionId(transactionId);

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
