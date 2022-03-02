package hello.helloSpring.service;
//command+shift+t => 자동으로 생성
import hello.helloSpring.domain.Member;

import hello.helloSpring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {

    MemberService memberService ;
    MemoryMemberRepository memberRepository;
    //MemoryMemberRepository 와 다른 객체로 테스트 되고있음 (다른 인스턴스)-> 이점을 수정해야함
    //

    //해당 test가 실행되기전에 memberRepository를 생성 -> MemberService 와 연결
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach //아래의 method들이 실행되고 끝날때마다 해당 method를 실행한다.
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given 무언가 주어지면
        Member member = new Member();
        member.setName("hello");

        //when 그 데이터를 받았을때
        Long saveId = memberService.join(member);

        //then 어떠한 결과를 도출할것인가?
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    //예외가 터지는것도 확인하는것이 중요하다.
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when

        memberService.join(member1);
        //command+option+v => 반환되는 값을 자동으로 생성해줌
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        /*try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}