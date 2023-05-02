package hello.golong.domain.member.api;

import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.member.dto.OauthToken;
import hello.golong.domain.post.dto.PostDto;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{sns_email}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("sns_email") String sns_email) {
        return ResponseEntity.ok().body(memberService.findMemberBySnsEmail(sns_email));
    }
    @GetMapping("/oauth/token")
    public ResponseEntity getLogin(@RequestParam("code")String code){
        System.out.println(code);
        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = memberService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String jwtToken = memberService.SaveUserAndGetToken(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
//        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }



}