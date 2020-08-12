package com.tagihan.Tagihan.Rabbit.Payment.Ovo.Consumer;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer.OvoProducer;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class OvoConsumer {

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BpjsRepository bpjsRepository;

   @Autowired
    OvoProducer ovoProducer;

   @Autowired
    RabbitTemplate rabbitTemplate;

   BPJSCLass newBpjsKeRabbit = new BPJSCLass();

    @RabbitListener(queues = "q.bpjs")
    public void receiveUserBpjs(User userReq){
        String userName = userReq.getUserName();
        Login login = loginRepository.findByUserName(userName);
        ovoProducer.sendLogin(login);
        String userNameLogin = login.getUserName();
        BPJSCLass bpjscLass = null;
        if ((userName.equals(userNameLogin))){
            String bpjsId = userReq.getNoBpjs();
            int danaTransfer = userReq.getOvo();
            User user = userRepository.findByBpjsId(bpjsId);

            int danaOvo = 0;
            BPJSCLass BPJSCLass1 = bpjsRepository.findById(bpjsId);
            int danaTagihanBpjs = BPJSCLass1.getJumlahTagihan();
            if (danaTransfer >= danaTagihanBpjs){
                ovoProducer.sendDanaTagihan(danaTagihanBpjs);
                danaOvo = user.getOvo();
                int totalOvo = danaOvo - danaTransfer;
                user.setOvo(totalOvo);
                userRepository.editUser(user);
                BPJSCLass1.setJumlahTagihan(0);
                BPJSCLass1.setStatusTagihan(true);
                bpjsRepository.editBpjs(BPJSCLass1);
                newBpjsKeRabbit = BPJSCLass1;

                rabbitTemplate.convertAndSend("xbpjs.controller","",newBpjsKeRabbit);


            }

        }

    }



    @Autowired
    PLNRepository plnRepository;

    @RabbitListener(queues = "q.pln")
    public void receiveUserPln(User userReq){
        String userName = userReq.getUserName();
        Login login = loginRepository.findByUserName(userName);
        String userNameLogin = login.getUserName();
        if (userName.equals(userNameLogin)){
//            ovoProducer.sendLogin(login);
            String plnId = userReq.getNoPln();
            int danaTransfer = userReq.getOvo();
            User user = userRepository.findByPlnId(plnId);

            int danaOvo = 0;
            PLN pln = plnRepository.findById(plnId);
            int danaTagihan = pln.getJumlahTagihan();
            if (danaTransfer >= pln.getJumlahTagihan()){
                ovoProducer.sendDanaTagihan(danaTagihan);
                danaOvo = user.getOvo();
                int totalOvo = danaOvo - danaTransfer;
                user.setOvo(totalOvo);
                userRepository.editUser(user);
                pln.setJumlahTagihan(0);
                pln.setStatusTagihan(true);
                plnRepository.editPLN(pln);

                rabbitTemplate.convertAndSend("xpln.controller","",pln);

            }

        }
    }
}
