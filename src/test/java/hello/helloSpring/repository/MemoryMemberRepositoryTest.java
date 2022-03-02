package hello.helloSpring.repository;


import hello.helloSpring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //아래의 method들이 실행되고 끝날때마다 해당 method를 실행한다.
    public void afterEach(){
        repository.clearStore();
    }
    //method들의 실행 순서는 우리가 예측할수없기때문에, 순서의 의존관계가 없이 설계해야한다. 따라서 실행 후 store를 비워줘야한다.
    @Test
    public void save() {
        Member member = new Member();
        member.setName("heejae");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("heejae1");
        repository.save(member1);


        Member member2 = new Member();
        member2.setName("heejae2");
        repository.save(member2);

        Member result = repository.findByName("heejae1").get();

        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("heejae1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("heejae2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
