package hello.golong.domain.member.application;

import hello.golong.domain.member.dao.MemberRepository;
import hello.golong.domain.member.domain.Member;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.post.application.PostService;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostService postService;


    @Autowired
    public MemberService(MemberRepository memberRepository, PostService postService) {
        this.memberRepository = memberRepository;
        this.postService = postService;
    }

    public MemberDto findMember(Long id) {
        return getMemberDto(memberRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다.")));
    }

    public MemberDto findMemberBySnsEmail(String sns_email) {
        return getMemberDto(memberRepository.findBySnsEmail(sns_email)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다.")));
    }
    //sns



    public MemberDto updateMember(MemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(memberDto.getName() != null) member.updateName(memberDto.getName());
        if(memberDto.getGOLtokens() != null) member.updateGOLtokens(memberDto.getGOLtokens());
        if(memberDto.getIsVerified() != null) member.updateIsVerified(memberDto.getIsVerified());
        if(memberDto.getProfileImgUrl() != null) member.updateProfileImgUrl(memberDto.getProfileImgUrl());

        return getMemberDto(member);
    }

    public void deleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        memberOptional.ifPresent(member -> {
            memberRepository.deleteById(id);
        });


    }

    //TODO : 회원 기부내역 조회
/*  public List<PostDto> findDonatedPost(Long id) {

    }

    //TODO : 새로운 회원 추가
    public MemberDto createMember(MemberDto memberDto) {

        return memberDto;
    }
 */

    public MemberDto getMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .GOLtokens(member.getGOLtokens())
                .walletUrl(member.getWalletUrl())
                .privateKey(member.getPrivateKey())
                .isVerified(member.getIsVerified())
                .createdAt(member.getCreatedAt())
                .profileImgUrl(member.getProfileImgUrl())
                .snsEmail(member.getSnsEmail())
                .snsType(member.getSnsType())
                .snsProfile(member.getSnsProfile())
                .accessToken(member.getAccessToken())
                .postsByMember(postService.findPostByUploaderId(member.getId()))
                .build();

    }


}
