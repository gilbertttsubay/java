package com.tagihan.Tagihan.Rabbit.Payment.Ovo.Consumer;

import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer.OvoProducer;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OvoConsumerIndieHome {
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OvoProducer ovoProducer;

    @Autowired
    IndihomeRepository indihomeRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    IndieHome indieHomeInternetKeRabbit = new IndieHome();
    @RabbitListener(queues = "internet")
    public void receiveInternetUser(User userReq){
        String userName = userReq.getUserName();
        Login login = loginRepository.findByUserName(userName);
        String userNameLogin = login.getUserName();
        IndieHome indieHome = null;
        if ((userName.equals(userNameLogin))){
            ovoProducer.sendLoginInternet(login);
            String indihomeId = userReq.getNoIndihome();
            int danaTransfer = userReq.getOvo();
            User user = userRepository.findByIndihomeId(indihomeId);

            int danaOvo = 0;
            IndieHome indieHome1 = indihomeRepository.findById(indihomeId);
            int danaTagihanInternet = indieHome1.getJumlah_tagihan_internet();
            if (danaTransfer >= indieHome1.getJumlah_tagihan_internet()){
                ovoProducer.sendDanaTagihanInternet(danaTagihanInternet);
                danaOvo = user.getOvo();
                int totalOvo = danaOvo - danaTransfer;
                user.setOvo(totalOvo);

                indieHome1.setJumlah_tagihan_internet(0);
                indieHome1.setStatus_tagihan_internet(true);
                indihomeRepository.editIndiHome(indieHome1);
                userRepository.editUser(user);
                indieHomeInternetKeRabbit = indieHome1;
                rabbitTemplate.convertSendAndReceive("xinternetovo.controller","",indieHomeInternetKeRabbit);

            }

        }
    }

    IndieHome indieHomeTvKeRabbit = new IndieHome();

    @RabbitListener(queues = "qtv.ovo")
    public void receiveUserTv(User userReq){
        String userName = userReq.getUserName();
        Login login = loginRepository.findByUserName(userName);
        String userNameLogin = login.getUserName();
        IndieHome indieHome = null;
        if ((userName.equals(userNameLogin))){
            ovoProducer.sendLoginTv(login);
            String indihomeId = userReq.getNoIndihome();
            int danaTransfer = userReq.getOvo();
            User user = userRepository.findByIndihomeId(indihomeId);

            int danaOvo = 0;
            IndieHome indieHome1 = indihomeRepository.findById(indihomeId);
            int danaTagihanTv = indieHome1.getJumlah_tagihan_tv();
            ovoProducer.sendDanaTagihanTv(danaTagihanTv);
            if (danaTransfer >= indieHome1.getJumlah_tagihan_internet()){

                danaOvo = user.getOvo();
                int totalOvo = danaOvo - danaTransfer;
                user.setOvo(totalOvo);
                userRepository.editUser(user);
                indieHome1.setJumlah_tagihan_tv(0);
                indieHome1.setStatus_tagihan_tv(true);
                indihomeRepository.editIndiHome(indieHome1);
                indieHomeTvKeRabbit = indieHome1;
                rabbitTemplate.convertSendAndReceive("xtvrabbit.ovo","",indieHomeTvKeRabbit);

            }

        }
    }
}
