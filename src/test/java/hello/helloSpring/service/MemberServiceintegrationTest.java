package hello.helloSpring.service;
//command+shift+t => 자동으로 생성

import hello.helloSpring.domain.Member;
import hello.helloSpring.repository.MemberRepository;
import hello.helloSpring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//@Transactional 을 걸면 테스트를 진행하여도 테스트가 끝난뒤에 실제 db에 반영이 되지 않는다.(각각의 테스트 마다 ,테스트케이스에서만 적용)
class MemberServiceintegrationTest {

    @Autowired MemberService memberService ;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given 무언가 주어지면
        Member member = new Member();
        member.setName("hello15");

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


}