package tobyspring.learningtest.instancio;

import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Test;

import static org.instancio.Select.field;
import static org.assertj.core.api.Assertions.assertThat;

public class InstancioLearningTest {
    @Test
    void user(){
        User user = Instancio.of(User.class)
                .ignore(field(User::getId))
                .generate(field(User::getEmail), get -> get.net().email())
                .set(field(User::getUserStatus), UserStatus.PENDING)
                .create();

        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void userModel(){
        Model<User> model = Instancio.of(User.class)
                .ignore(field(User::getId))
                .generate(field(User::getEmail), get -> get.net().email())
                .set(field(User::getUserStatus), UserStatus.PENDING)
                .toModel();


        for(int i=0; i<100; i++){
            User user = Instancio.of(model)
                    .create();
            System.out.println(user);

            assertThat(user.getId()).isNull();
            assertThat(user.getEmail()).isNotEmpty();
            assertThat(user.getName()).isNotEmpty();
            assertThat(user.getUserStatus()).isEqualTo(UserStatus.PENDING);
        }
    }

    @Test
    void annotation(){
        UserRegisterRequest userRegisterRequest = Instancio.of(UserRegisterRequest.class).create();

        assertThat(userRegisterRequest.email()).isNotEmpty();
        assertThat(userRegisterRequest.nickname()).isNotEmpty();
        assertThat(userRegisterRequest.password()).hasSizeBetween(8, 100);
    }
}
