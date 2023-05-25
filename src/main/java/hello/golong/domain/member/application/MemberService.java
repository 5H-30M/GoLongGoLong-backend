package hello.golong.domain.member.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.golong.domain.member.dao.MemberRepository;
import hello.golong.domain.member.domain.Member;
import hello.golong.domain.member.dto.KakaoProfile;
import hello.golong.domain.member.dto.MemberDto;
import hello.golong.domain.member.dto.OauthToken;
import hello.golong.domain.member.dto.WalletDto;
import hello.golong.domain.post.application.PostService;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import static hello.golong.domain.member.config.SecurityConfig.FRONT_URL;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
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

    @Transactional
    public MemberDto updateWalletInformation(Long member_id, WalletDto walletDto) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.updateWalletInformation(walletDto.getWalletAddress(), walletDto.getPrivateKey());
        return this.findMember(member_id);
    }
/*
    public MemberDto findMemberBySnsEmail(String sns_email) {

        return getMemberDto(memberRepository.findBySnsEmail(sns_email));

        //return getMemberDto(memberRepository.findBySnsEmail(sns_email)
          //      .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다.")));

    }*/




    @Transactional
    public MemberDto updateMember(Long member_id, MemberDto memberDto) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(memberDto.getName() != null) member.updateName(memberDto.getName());
        if(memberDto.getNickName() != null) member.updateNickName(memberDto.getNickName());
        if(memberDto.getRegion() != null) member.updateRegion(memberDto.getRegion());
        if(memberDto.getGOLtokens() != null) member.updateGOLtokens(memberDto.getGOLtokens());
        if(memberDto.getIsVerified() != null) member.updateIsVerified(memberDto.getIsVerified());
        if(memberDto.getProfileImgUrl() != null) member.updateProfileImgUrl(memberDto.getProfileImgUrl());

        return getMemberDto(member);
    }

    @Transactional
    public void updateGOLtokens(Long id, Long amount) {
        memberRepository.findById(id).ifPresent(member -> {
            member.updateGOLtokens(member.getGOLtokens()-amount);
        });
    }

    public void deleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        memberOptional.ifPresent(member -> {
            memberRepository.deleteById(id);
        });


    }

    public OauthToken getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "02ab37b785bc6922a662afba8e61b2cc");
        params.add("redirect_uri", "http://localhost:8080/member/oauth/token");
        params.add("code", code);
        params.add("client_secret", "8HoFo5PqPHCDXEUPvELibHfeim4Vs5XK");

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        System.out.println(oauthToken);
        return oauthToken;

    }

    public KakaoProfile findProfile(String token) {
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;

    }
//    public Member getUser(HttpServletRequest request){
//        Long userCode=(Long)request.getAttribute("userCode");
//        Member member=memberRepository.findByUserCode(userCode);
//
//        return member;
//    }

    public String SaveUserAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);

        //TODO : member 저장시 walletAddress 수정
        Member member = memberRepository.findBySnsEmail(profile.getKakao_account().getEmail());
        if (member == null) {
            member = member.builder()
                    .id(profile.getId())//kakaoid -> member별로 unique한 값
                    .GOLtokens(0L)
                    //.walletUrl()
                    //.privateKey()
                    .isVerified(false)
                    .createdAt(LocalDateTime.now())
                    .profileImgUrl(profile.getKakao_account().getProfile().getProfile_image_url())
                    .name(profile.getKakao_account().getProfile().getNickname())
                    .snsEmail(profile.getKakao_account().getEmail())
                    .nickName(this.getNickName(profile.getKakao_account().getEmail()))
                    .userRole("ROLE_USER").build();

            memberRepository.save(member);
        }

        return token;
    }

    //example@kakao.com -> 초기 닉네임 -> example
    public String getNickName(String email) {
        return email.split("@")[0];
    }


        public MemberDto getMemberDto(Member member) {
            return MemberDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .GOLtokens(member.getGOLtokens())
                    .walletAddress(member.getWalletAddress())
                    .privateKey(member.getPrivateKey())
                    .isVerified(member.getIsVerified())
                    .createdAt(member.getCreatedAt())
                    .profileImgUrl(member.getProfileImgUrl())
                    .snsEmail(member.getSnsEmail())
                    .snsType(member.getSnsType())
                    .snsProfile(member.getSnsProfile())
                    .postsByMember(postService.findPostByUploaderId(member.getId()))
                    .nickName(member.getNickName())
                    .region(member.getRegion())
                    .build();

        }


    }
