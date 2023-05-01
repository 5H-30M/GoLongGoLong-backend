package hello.golong.domain.member.api;

import hello.golong.domain.member.application.MemberService;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    //TODO : 컨트롤러 작성 및 컨트롤러 테스트

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{sns_email}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("sns_email") String sns_email) {
        return ResponseEntity.ok().body(memberService.findMemberBySnsEmail(sns_email));
    }

}
