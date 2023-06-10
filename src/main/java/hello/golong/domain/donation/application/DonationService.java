package hello.golong.domain.donation.application;

import hello.golong.domain.donation.dao.DonationRepository;
import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.donation.dto.DonationDto;
import hello.golong.domain.donation.dto.TrackingDto;
import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.member.domain.Member;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.post.application.PostService;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        MemberDto memberDto = memberService.findMember(donationDto.getFromId());
        String memberAddress = memberDto.getWalletAddress();
        String postAddress = postService.findPost(donationDto.getToId()).getWalletAddress();

        donationDto.setFromAddress(memberAddress);
        donationDto.setToAddress(postAddress);
        donationDto.setType(0L);
        donationDto.setPrivateKey(memberDto.getPrivateKey());

        //String postAddress, String memberAddress, String privateKey, Long amount
        //TODO : privateKey 복호화 알고리즘으로 처리하기
        donationDto = smartContractService.transferGOL(postAddress, memberAddress, donationDto);

        Donation donation = this.buildDonation(donationDto);
        donationRepository.save(donation);

        memberService.updateGOLtokens(donationDto.getFromId(), donationDto.getAmount());
        postService.updateDonationInformation(donationDto.getToId(), donationDto.getAmount());

        return donationDto;
    }

    //post -> member
    public DonationDto giveTokensToRescuer(DonationDto donationDto) throws TransactionException, IOException {

        postService.updateStatus(donationDto.getFromId(), 2);
        PostDto postDto = postService.findPost(donationDto.getFromId());
        String postAddress = postDto.getWalletAddress();
        String memberAddress = memberService.findMember(donationDto.getToId()).getWalletAddress();

        donationDto.setFromAddress(postAddress);
        donationDto.setToAddress(memberAddress);
        donationDto.setType(1L);
        donationDto.setPrivateKey(postDto.getPrivateKey());

        //String postAddress, String memberAddress, String privateKey, Long amount
        //TODO : privateKey 복호화 알고리즘으로 처리하기
        donationDto = smartContractService.transferGOL(memberAddress, postAddress, donationDto);

        Donation donation = this.buildDonation(donationDto);
        donationRepository.save(donation);

        memberService.receiveGOLtokens(donationDto.getToId(), donationDto.getAmount());
        postService.updateGOLTokens(donationDto.getFromId(), donationDto.getAmount());

        return donationDto;

    }

    public void giveSEthToMember(Long member_id) throws TransactionException, IOException {
        smartContractService.transferSEth(memberService.findMember(member_id).getWalletAddress());
    }

    public List<TrackingDto> findDonationsByMemberId(Long member_id) {
        //find member -> post
        List<Donation> donations = donationRepository.findByFromIdAndType(member_id, 0L);
        List<TrackingDto> trackingDtos = new ArrayList<>();
        if(donations == null) donations = new ArrayList<>();
        for(Donation donation :donations) {
            PostDto postDto = postService.findPost(donation.getToId());
            List<Donation> donationList = donationRepository.findByFromIdAndType(postDto.getPost_id(), 1L);

            TrackingDto trackingDto = TrackingDto.builder()
                    .post_id(postDto.getPost_id())
                    .title(postDto.getTitle())
                    .uploader_id(postDto.getUploader_id())
                    .status(postDto.getStatus())
                    .images(postDto.getImages())
                    .myTransaction(this.buildDonationDto(donation))
                    .build();
            if(!donationList.isEmpty())
                trackingDto.setPostTransaction(this.buildDonationDto(donationList.get(0)));
            trackingDtos.add(trackingDto);


        }
        return trackingDtos;

    }

    public Donation buildDonation(DonationDto donationDto) {
        return Donation.builder()
                .transactionId(donationDto.getTransactionId())
                .amount(donationDto.getAmount())
                .createdAt(donationDto.getTransactionCreatedAt())
                .fromAddress(donationDto.getFromAddress())
                .toAddress(donationDto.getToAddress())
                .fromId(donationDto.getFromId())
                .toId(donationDto.getToId())
                .type(donationDto.getType())
                .build();
    }

    public DonationDto buildDonationDto(Donation donation, String privateKey) {
        return DonationDto.builder()
                .transactionId(donation.getTransactionId())
                .amount(donation.getAmount())
                .transactionCreatedAt(donation.getCreatedAt())
                .fromAddress(donation.getFromAddress())
                .toAddress(donation.getToAddress())
                .fromId(donation.getFromId())
                .toId(donation.getToId())
                .type(donation.getType())
                .privateKey(privateKey)
                .build();
    }

    public DonationDto buildDonationDto(Donation donation) {
        return DonationDto.builder()
                .transactionId(donation.getTransactionId())
                .amount(donation.getAmount())
                .transactionCreatedAt(donation.getCreatedAt())
                .fromAddress(donation.getFromAddress())
                .toAddress(donation.getToAddress())
                .fromId(donation.getFromId())
                .toId(donation.getToId())
                .type(donation.getType())
                .build();
    }


}
