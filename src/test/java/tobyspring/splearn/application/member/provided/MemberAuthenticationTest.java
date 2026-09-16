package tobyspring.splearn.application.member.provided;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.application.member.provided.dto.MemberLoginRequest;
import tobyspring.splearn.application.member.provided.exception.LoginFailedException;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class MemberAuthenticationTest {
    final MemberAuthenticator memberAuthenticator;
    final MemberRegister memberRegister;

    @Test
    void login(){
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        var member = memberRegister.register(registerRequest);
        member.activate();

        var loggedInMember = memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));
        assertThat(loggedInMember).isEqualTo(member);
    }

    @Test
    void loginFailedNotActive(){
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest);

        Assertions.assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password())))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedEmailNotExist(){
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() ->
                        memberAuthenticator.login(new MemberLoginRequest("notexists@email.com", registerRequest.password())))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedWrongPassword(){
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() ->
                        memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), "wrongpassword")))
                .isInstanceOf(LoginFailedException.class);
    }

}
