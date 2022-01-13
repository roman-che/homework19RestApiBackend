package ru.inventorium.qa.restbackend.controller;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.inventorium.qa.restbackend.domain.LoginInfo;
import ru.inventorium.qa.restbackend.domain.UserInfo;
import ru.inventorium.qa.restbackend.exception.InvalidUsernameException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class BankController {

    private Map<String, UserInfo> users = Map.of(
            "Dima", UserInfo.builder().userName("Dima").build(),
            "Olga", UserInfo.builder().userName("Olga").build(),
            "Vasya", UserInfo.builder().userName("Vasya").build(),
            "Petya", UserInfo.builder().userName("Petya").build()
    );

    @PostMapping("/user/login")
    @ApiOperation("Авторизация")
   // @ApiImplicitParams()
    public UserInfo doLogin(@RequestBody(required = false) LoginInfo loginInfo){
        if (loginInfo.getUserName().equals("Dima")){
            return UserInfo.builder()
                    .loginDate(new Date())
                    .userName(loginInfo.getUserName())
                    .build();
        } else {
            throw new InvalidUsernameException();
        }
    }
    @GetMapping("user/getAll")
    @ApiOperation("Получение списка всех пользователей")
    public List<UserInfo> getAllUsersInfo(){
        return users.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}
