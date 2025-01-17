package miruy.dev.frety.service;

import lombok.RequiredArgsConstructor;
import miruy.dev.frety.entity.Member;
import miruy.dev.frety.exception.DataNotFoundException;
import miruy.dev.frety.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);

        member.setPassword(passwordEncoder.encode(password));

        this.memberRepository.save(member);
        return member;
    }

    public Member getUser(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);

        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
