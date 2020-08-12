package com.tagihan.Tagihan.Rabbit.Login.Producer;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class LoginProducer implements LoginProducerRepository {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    LoginRepository loginRepository;

    public void addLogin(Login login){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.input.login","",login);
    }

    public void logout(Login login){
        rabbitTemplate.convertSendAndReceive("x.logout.login","",login);
    }

    public void addUser(User user){
        rabbitTemplate.convertSendAndReceive("x.adduser.login","",user);
    }

}
