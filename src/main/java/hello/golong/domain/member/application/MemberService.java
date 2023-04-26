package hello.golong.domain.member.application;

import hello.golong.domain.member.dao.MemberRepository;
import hello.golong.domain.member.domain.Member;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.post.application.PostService;
import hello.golong.domain.post.domain.Post;
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

    public List<PostDto> findPostByMemberId(Long uploader_id) {
        return postService.findPostByUploaderId(uploader_id);
    }

    /*public MemberDto createMember(MemberDto memberDto) {

        return memberDto;
    }

    public MemberDto updateMember(MemberDto memberDto) {

    }
*/
/*    public List<PostDto> findDonatedPost(Long id) {

    }*/

    public MemberDto getMemberDto(Member member) {
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .golongs(member.getGolongs())
                .walletUrl(member.getWalletUrl())
                .privateKey(member.getPrivateKey())
                .isVerified(member.isVerified())
                .createdAt(member.getCreatedAt())
                .profileImgUrl(member.getProfileImgUrl())
                .snsEmail(member.getSnsEmail())
                .snsType(member.getSnsType())
                .snsProfile(member.getSnsProfile())
                .accessToken(member.getAccessToken())
                .postsByMember(postService.findPostByUploaderId(member.getId()))
                .build();

        return memberDto;

    }


}
