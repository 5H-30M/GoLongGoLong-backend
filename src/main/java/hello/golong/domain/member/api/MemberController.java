package hello.golong.domain.member.api;

import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.member.dto.*;

import hello.golong.domain.post.dto.PostDto;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.web3j.crypto.Wallet;


import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    //TODO : 컨트롤러 작성 및 컨트롤러 테스트


    @Autowired

    private final MemberService memberService;


    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

/*    @GetMapping("/{sns_email}")
    public ResponseEntity<MemberDto> getMemberBySnsEmail(@PathVariable("sns_email") String sns_email) {
        return ResponseEntity.ok().body(memberService.findMemberBySnsEmail(sns_email));
    }*/

    @GetMapping("/{member_id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("member_id") Long memberId) {
        return ResponseEntity.ok().body(memberService.findMember(memberId));
    }

    @GetMapping("/oauth/token")
    public ResponseEntity getLogin(@RequestParam("code")String code){
        System.out.println(code);
        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = memberService.getAccessToken(code);
        System.out.println(oauthToken);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String jwtToken = memberService.SaveUserAndGetToken(oauthToken.getAccess_token());

        KakaoProfile profile = memberService.findProfile(oauthToken.getAccess_token());
        Long userId=profile.getId();
        //System.out.println(profile.id);

        HttpHeaders headers = new HttpHeaders();
     //   headers.add("UserId", userId.toString());
//        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken)
        return ResponseEntity.ok().headers(headers).body(userId);
    }

    @PatchMapping("/wallet/{member_id}")
    public ResponseEntity<MemberDto> updateWalletInformation(@PathVariable("member_id") Long member_id, @RequestBody WalletDto walletDto) {
        return ResponseEntity.ok().body(memberService.updateWalletInformation(member_id, walletDto));
    }

    @PatchMapping("/update/{member_id}")
    public ResponseEntity<MemberDto> updateMemberInformation(@PathVariable("member_id") Long member_id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok().body(memberService.updateMember(member_id, memberDto));
    }

    @PatchMapping("/region/{member_id}")
    public ResponseEntity<MemberDto> updateMemberRegion(@PathVariable("member_id") Long member_id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok().body(memberService.updateMember(member_id, memberDto));
    }







}

