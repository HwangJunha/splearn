package tobyspring.splearn.application.member;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.exception.LoginFailedException;
import tobyspring.splearn.application.member.provided.MemberAuthenticator;
import tobyspring.splearn.application.member.provided.dto.MemberLoginRequest;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.PasswordEncoder;
import tobyspring.splearn.domain.shared.Email;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberAuthenticationService implements MemberAuthenticator {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(MemberLoginRequest request) throws LoginFailedException {
        Member member = memberRepository.findByEmail(new Email(request.email())).orElseThrow(LoginFailedException::new);

        if(!member.isActive()){
            throw new LoginFailedException();
        }

        if(!member.verifyPassword(request.password(), passwordEncoder)){
            throw new LoginFailedException();
        }

        return member;
    }
}
