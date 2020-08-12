package com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OvoProducer implements OvoProducerRepository {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendUserBpjs(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.input.bpjs.ovo","",user);
    }

    public void sendUserPln(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.input.pln.ovo","",user);
    }

    public void sendUserIndieHomeInternet(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.internet.ovo","",user);
    }

    public void sendUserIndieHomeTv(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.input.indiehome.tv.ovo","",user);
    }

    public void sendLogin(Login login){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.output.login.ovo","",login);
    }
    public void sendLoginInternet(Login login){
        rabbitTemplate.convertSendAndReceive(
                "x.output.login.internet.ovo","",login);
    }
    public void sendLoginTv(Login login){
        rabbitTemplate.convertSendAndReceive(
                "x.login","",login);
    }

    public void sendDanaTransfer(int danaTransfer){
        rabbitTemplate.convertSendAndReceive("x.output.danatransfer.ovo","",danaTransfer);
    }

    public void sendDanaTagihan(int danaTagihan){
        rabbitTemplate.convertSendAndReceive("x.output.danatagihan.ovo","",danaTagihan);
    }

    @Override
    public void sendDanaTagihanInternet(int danaTagihan) {
        rabbitTemplate.convertSendAndReceive("x.output.danatagihaninternet.ovo","", danaTagihan);
    }

    @Override
    public void sendDanaTagihanTv(int danaTagihan) {
        rabbitTemplate.convertSendAndReceive("xdanatagihantv","",danaTagihan);
    }
}
