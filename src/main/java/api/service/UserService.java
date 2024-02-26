package api.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import fixtures.GoRestEndpoint;
import models.UserInfo;

import java.util.Collections;
import java.util.UUID;

public class UserService {
    private final String token;

    public UserService(String token) {
        this.token = token;
    }
    public UserInfo getUserInfo(String login) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .resourceLink(GoRestEndpoint.READ_USER.getEndPoint())
                .pathParams(Collections.singletonMap("editor", login))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo[] getUsersInfo() {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .resourceLink(GoRestEndpoint.READ_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo[].class);
    }

    public UserInfo createUser(UserInfo userInfo) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.CREATE_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.POST
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo updateUser(String editor, String userId, UserInfo userInfo) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .pathParams(Collections.singletonMap("editor", userId))
                .pathParams(Collections.singletonMap("id", userId))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.UPDATE_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.PATCH
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public void deleteUser(String editor, UserInfo userInfo) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .pathParams(Collections.singletonMap("editor", editor))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.DELETE_USERS.getEndPoint())
                .build();
        RestAssuredApiClient.DELETE.apply(requestDto);
    }
}
