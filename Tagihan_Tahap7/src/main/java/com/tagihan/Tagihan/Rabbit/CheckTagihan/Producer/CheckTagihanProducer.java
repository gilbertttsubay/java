package com.tagihan.Tagihan.Rabbit.CheckTagihan.Producer;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CheckTagihanProducer implements CheckTagihanProducerRepository {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void addUserBpjs(User user){

        rabbitTemplate.convertSendAndReceive("x.input.bpjs.cektagihan","",user);
    }

    public void addBpjs(BPJSCLass bpjscLass){
        rabbitTemplate.convertSendAndReceive("x.output.bpjs","",bpjscLass);
    }

    public void addUserPln(User user){
        rabbitTemplate.convertSendAndReceive("x.input.pln.cektagihan","",user);
    }

    public void addPln(PLN pln){
        rabbitTemplate.convertSendAndReceive("x.output.pln","",pln);
    }


    public void addUserIndihome(User user){
        rabbitTemplate.convertSendAndReceive("x.input.indiehome.cektagihan","",user);
    }

    public void addIndieHome(IndieHome indieHome){
        rabbitTemplate.convertSendAndReceive("x.output.indiehome","",indieHome);
    }


}
