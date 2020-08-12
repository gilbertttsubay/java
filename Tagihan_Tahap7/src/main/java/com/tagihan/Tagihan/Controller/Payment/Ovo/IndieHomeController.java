package com.tagihan.Tagihan.Controller.Payment.Ovo;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer.OvoProducer;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndieHomeController {

    @Autowired
    OvoProducer ovoProducer;

    Login loginDariRabbit = null;
    @RabbitListener(queues = "qlogininternet.ovo")
    public void getLoginDariRabbit(Login login){
        loginDariRabbit = login;
    }

    IndieHome indieHomeInternetDariRabbit = new IndieHome();
    @RabbitListener(queues = "qinternetovo.controller")
    public void getIndieHomeDariRabbit(IndieHome indieHome){
        indieHomeInternetDariRabbit = indieHome;
    }

    int danaTagihanInternetDariRabbit = 0;
    @RabbitListener(queues = "qdanatagihaninternet")
    public void getDanaTagihanInternetDariRabbit (int danaTagihan){
        danaTagihanInternetDariRabbit = danaTagihan;
    }

    @PutMapping("/ovo/bayarinternet")
    public IndieHome pembayaranInternetOvo(@RequestBody User userReq){
        ovoProducer.sendUserIndieHomeInternet(userReq);
        String userName = userReq.getUserName();
        String userNameLogin = loginDariRabbit.getUserName();

        if (userName.equals(userNameLogin)){
            int danaTransfer = userReq.getOvo();
            if (danaTransfer >= danaTagihanInternetDariRabbit){
                return indieHomeInternetDariRabbit;
            }
        }
        return null;
    }

    Login loginTvDariRabbit = null;

    @RabbitListener(queues = "qlogintv")
    public void getLoginTvDariRabbit(Login login){
        loginTvDariRabbit= login;
    }

    IndieHome indieHomeTvDariRabbit = new IndieHome();
    @RabbitListener(queues = "qtvrabbitovo.controller")
    public void getIndieHomeTvDariRabbit(IndieHome indieHome){
        indieHomeTvDariRabbit = indieHome;
    }

    int danaTagihanTvDariRabbit;
    @RabbitListener(queues = "qdanatagihantv")
    public void getDanaTagihanTvDariRabbit (int danaTagihan){
        danaTagihanTvDariRabbit = danaTagihan;
    }


    @PutMapping("/ovo/bayartv")
    public IndieHome pembayaranTvOvo(@RequestBody User userReq){
        ovoProducer.sendUserIndieHomeTv(userReq);
        String userName = userReq.getUserName();
        String userNameLogin = loginTvDariRabbit.getUserName();

        if (userName.equals(userNameLogin)){
            int danaTransfer = userReq.getOvo();
            if (danaTransfer >= danaTagihanTvDariRabbit){
                System.out.println(danaTagihanTvDariRabbit);
                return indieHomeTvDariRabbit;
            }
        }
        return null;
    }
}
