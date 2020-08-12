package com.tagihan.Tagihan.Controller;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Login.Producer.LoginProducer;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    LoginProducer loginProducer;

//    ***************************************************
    User userDariRabbit = null;

    @RabbitListener(queues = "q.adduser.login")
    public void addUserDariRabbit(User user){
        userDariRabbit =user;
    }

    Login loginDariRabbit = new Login();
    @RabbitListener(queues = "qlogin.controller")
    public void getLoginDariRabbit(Login login){
        loginDariRabbit = login;
    }
    @PostMapping("/login")
    public Login login(@RequestBody Login login){
        loginProducer.addLogin(login);
        if (userDariRabbit!= null){
            return loginDariRabbit;
        }
        return null;
    }

    //************************************************************
//    @GetMapping("/semualogin")
//    public List<Login> logins(){
//
//        return loginRepository.findAllLogin();
//    }

    @DeleteMapping("/logout")
    public String logout(@RequestBody Login login){
        loginProducer.logout(login);
        return "Sedang logout";
    }
}
