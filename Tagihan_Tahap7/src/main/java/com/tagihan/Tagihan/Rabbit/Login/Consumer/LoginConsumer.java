package com.tagihan.Tagihan.Rabbit.Login.Consumer;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Login.Producer.LoginProducer;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    LoginProducer loginProducer;

    @Autowired
    RabbitTemplate rabbitTemplate;

    Login loginKeRabbit = new Login();
    @RabbitListener(queues = "q.input.login")
    public void addLogin(Login login){
        login.setIdLogin(0);
        User user = userRepository.findByUserName(login.getUserName());
        String passwordUser = user.getPassword();
        String loginPassword = login.getPassword();
        Login login1 = loginRepository.findByUserName(login.getUserName());
        if (user != null && passwordUser.equals(loginPassword) && login1 == null){
            login.setStatusLogin(true);
            loginRepository.login(login);
            loginProducer.addUser(user);
            loginKeRabbit = login;
            sendLoginkeRabbit();

        }
        else {
//            login.setUserName(null);
//            login.setIdLogin(0);
//            login.setPassword(null);
//            login.setStatusLogin(false);
            loginNull();
        }

    }

    public void sendLoginkeRabbit(){
        rabbitTemplate.setReplyTimeout(10000);
        rabbitTemplate.convertSendAndReceive("xlogin.controller","",loginKeRabbit);
    }
    public Login loginNull(){
        return null;
    }

    @RabbitListener(queues = "q.logout.login")
    public void logout(Login login){
        Login login1 = loginRepository.findByUserName(login.getUserName());
        loginRepository.deleteLogin(login1.getIdLogin());
    }

}
