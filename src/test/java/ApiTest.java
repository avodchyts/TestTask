import api.service.UserService;
import config.TestConfig;
import models.UserInfo;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ApiTest {
    private static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    private static final String token = String.format("Bearer %s", PROD_DATA.authorizationToken());

    @Test
    public void testReadUsersList() {
        List<UserInfo> userList = Arrays.asList(new UserService(token).getUsersInfo());
        Assertions.assertThat(userList).isNotEmpty();
    }

    @Test
    public void testReadUser() {
        var login = Arrays.stream(new UserService(token).getUsersInfo()).filter(user -> user.role.equals("supervisor")).findFirst().get().login;
        UserInfo readUser = new UserService(token).getUserInfo(login);
        Assertions
                .assertThat(readUser.getLogin())
                .isEqualTo(login);
    }

    @Test
    public void testReadUserNegative() {
        var login = "";
        UserInfo readUser = new UserService(token).getUserInfo(login);
        Assertions
                .assertThat(Objects.isNull(readUser));
    }

    @Test
    public void testCreateUser() {
        var testUser = UserInfo.builder()
                .age("17")
                .editor("editor")
                .gender("male")
                .login(getLogin())
                .password(getPassword())
                .role("user")
                .screenName(getScreenName()).build();
        UserInfo user = new UserService(token).createUser(testUser);

        Assertions
                .assertThat(user.getLogin().equals(testUser.getLogin()));
    }

    @Test
    public void testCreateUserNegative() {
        var testUser = UserInfo.builder()
                .age("17")
                .editor("editor")
                .gender("male")
                .login(getLogin())
                .password(getPassword())
                .screenName(getScreenName())
                .build();
        UserInfo user = new UserService(token).createUser(testUser);

        Assertions
                .assertThat(Objects.isNull(user));
    }

    @Test
    public void testDeleteUser() {
        var userInfo = Arrays.stream(new UserService(token).getUsersInfo()).filter(user -> user.role.equals("user")).findFirst().get();
        var editor = userInfo.login;
        var deletedUser = new UserInfo();
        deletedUser.id=userInfo.id;
        new UserService(token).deleteUser(editor, deletedUser);

        UserInfo readUser = new UserService(token).getUserInfo(editor);
        Assertions
                .assertThat(Objects.isNull(readUser));
    }

    @Test
    public void testDeleteUserNegative() {
        var userInfo = Arrays.stream(new UserService(token).getUsersInfo()).filter(user -> user.role.equals("user")).findFirst().get();
        var editor = "";
        var deletedUser = new UserInfo();
        deletedUser.id=userInfo.id;
        new UserService(token).deleteUser(editor, deletedUser);

        UserInfo readUser = new UserService(token).getUserInfo(userInfo.login);
        Assertions
                .assertThat(Objects.nonNull(readUser));
    }

    @Test
    public void testUpdateUser() {
        var userInfo = Arrays.stream(new UserService(token).getUsersInfo()).filter(user -> user.role.equals("user")).findFirst().get();
        var editor = userInfo.login;
        var userId=userInfo.id.toString();

        UserInfo updateUserData = UserInfo.builder()
                .age("17")
                .editor("editor")
                .gender("female")
                .login(getLogin())
                .password(getPassword())
                .screenName(getScreenName())
                .build();

        UserInfo updatedUser = new UserService(token).updateUser(editor,userId, updateUserData);
        Assertions
                .assertThat(updatedUser.getGender())
                .isEqualTo(updateUserData.gender);
    }

    @Test
    public void testUpdateUserNegative() {
        var userInfo = Arrays.stream(new UserService(token).getUsersInfo()).filter(user -> user.role.equals("user")).findFirst().get();
        var editor = userInfo.login;
        var userId="";

        UserInfo updateUserData = UserInfo.builder()
                .age("17")
                .editor("editor")
                .gender("female")
                .login(getLogin())
                .password(getPassword())
                .screenName(getScreenName())
                .build();

        UserInfo updatedUser = new UserService(token).updateUser(editor,userId, updateUserData);
        Assertions
                .assertThat(Objects.isNull(updatedUser));
    }


    private String getLogin() {
        String uniqueText = RandomStringUtils.randomAlphabetic(3);
        var login = String.format("user_login_%s", uniqueText);
        return login;
    }

    private String getScreenName() {
        String uniqueText = RandomStringUtils.randomAlphabetic(3);
        var screenName = String.format("screenName_%s", uniqueText);
        return screenName;
    }

    private String getPassword() {
        String uniquePart = RandomStringUtils.randomAlphanumeric(5);
        var password = String.format("password_%s", uniquePart);
        return password;
    }

    private UUID getId() {
        UUID uniqueId = UUID.randomUUID();
        return uniqueId;
    }

}

