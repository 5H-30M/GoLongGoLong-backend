package hello.golong.domain.donation.application;

import hello.golong.domain.donation.dao.DonationRepository;
import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.donation.dto.DonationDto;
import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.post.application.PostService;
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

    public DonationDto createDonation(DonationDto donationDto) throws TransactionException, IOException {

        String memberAddress = memberService.findMember(donationDto.getFromId()).getWalletAddress();
        String postAddress = postService.findPost(donationDto.getToId()).getWalletAddress();

        donationDto.setFromAddress(memberAddress);
        donationDto.setToAddress(postAddress);

        //TODO : 이건 내 privateKey 일 뿐임 나중에 삭제하기 복호화 알고리즘으로 처리하면 이거 필요없음
        donationDto.setPrivateKey("210532c54c56fe8f221d55d6d7b462ae3fa831f0059b49769450d8812f21d6fa");

        //String postAddress, String memberAddress, String privateKey, Long amount
        //TODO : privateKey 복호화 알고리즘으로 처리하기
        donationDto = smartContractService.transfer(postAddress, memberAddress, donationDto);

        Donation donation = this.buildDonation(donationDto);
        donationRepository.save(donation);

        memberService.updateGOLtokens(donationDto.getFromId(), donationDto.getAmount());
        postService.updateDonationInformation(donationDto.getToId(), donationDto.getAmount());

        return donationDto;
    }

    public Donation buildDonation(DonationDto donationDto) {
        return Donation.builder()
                .transactionId(donationDto.getTransactionId())
                .amount(donationDto.getAmount())
                .createdAt(donationDto.getTransactionCreatedAt())
                .fromAddress(donationDto.getFromAddress())
                .toAddress(donationDto.getToAddress())
                .build();
    }

}
