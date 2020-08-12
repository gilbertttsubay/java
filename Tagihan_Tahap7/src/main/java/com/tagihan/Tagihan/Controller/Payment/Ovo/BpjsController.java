package com.tagihan.Tagihan.Controller.Payment.Ovo;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer.OvoProducer;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BpjsController {

    @Autowired
    OvoProducer ovoProducer;
    //pembayaran ovo bpjs
    Login loginDariRabbit = null;
    @RabbitListener(queues = "q.login.ovo")
    public void getLoginDariRabbit(Login login){
        loginDariRabbit = login;
    }

    BPJSCLass bpjscLassDariRabbit = new BPJSCLass();
    @RabbitListener(queues = "qbpjs.controller")
    public void getBpjsDariRabbit(BPJSCLass bpjscLass){
        bpjscLassDariRabbit = bpjscLass;

    }
    int danaTagihanDariRabit = 0;
    @RabbitListener(queues = "qtagihan.ovo")
    public void getDanaTagihanDariRabbit(int danaTagihan){
        danaTagihanDariRabit = danaTagihan;
    }

    @PostMapping("/ovo/bayarbpjs")
    public BPJSCLass pembayaranBpjsOvo(@RequestBody User userReq){
        ovoProducer.sendUserBpjs(userReq);
        String userName = userReq.getUserName();
        String userNameLogin = loginDariRabbit.getUserName();
        System.out.println(userNameLogin);
        if (userName.equals(userNameLogin)){
            int danaTransfer = userReq.getOvo();
            if (danaTransfer >= danaTagihanDariRabit){
                System.out.println(danaTagihanDariRabit);

                return bpjscLassDariRabbit;
            }
        }
        return null ;
        }


    }

