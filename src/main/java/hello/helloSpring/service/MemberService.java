package hello.helloSpring.service;

import hello.helloSpring.domain.Member;
import hello.helloSpring.repository.MemberRepository;
import hello.helloSpring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        //외부에서 생성되 memberRepository를 참조하여 해당 클래스에서 사용되도록 한다.
        this.memberRepository = memberRepository;
    }


    //회원가입

    public Long join(Member member) {
            TestDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();

    }

    private void TestDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }


    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 검색
    public Optional<Member> findOne(long MemberID) {
        return memberRepository.findById(MemberID);
    }
}
