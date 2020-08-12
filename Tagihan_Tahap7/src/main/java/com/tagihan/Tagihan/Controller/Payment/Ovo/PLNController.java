package com.tagihan.Tagihan.Controller.Payment.Ovo;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer.OvoProducer;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PLNController {

    @Autowired
    OvoProducer ovoProducer;

    //pembayaran ovo bpjs
    Login loginDariRabbit = null;
    @RabbitListener(queues = "q.output.login.ovo")
    public void getLoginDariRabbit(Login login){
        loginDariRabbit = login;
    }

    PLN plnDariRabbit = new PLN();
    @RabbitListener(queues = "qpln.controller")
    public void getPlnDariRabbit(PLN pln){
        plnDariRabbit = pln;
    }

    int danaTagihanDariRabit = 0;
    @RabbitListener(queues = "q.output.danatagihan.ovo")
    public void getDanaTagihanDariRabbit(int danaTagihan){
        danaTagihanDariRabit = danaTagihan;
    }

    //pembayaran ovo bpjs
    @PutMapping("/ovo/bayarpln")
    public PLN pembayaranPlnOvo(@RequestBody User userReq){
        ovoProducer.sendUserPln(userReq);
        String userName = userReq.getUserName();
        String userNameLogin = "ronal";
        if (userName.equals(userNameLogin)){
            int danaTransfer = userReq.getOvo();
            if (danaTransfer >= danaTagihanDariRabit){
                return plnDariRabbit;
            }
        }
        return null;
    }



}

