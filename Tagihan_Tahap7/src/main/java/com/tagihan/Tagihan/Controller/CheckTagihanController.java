package com.tagihan.Tagihan.Controller;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.CheckTagihan.Producer.CheckTagihanProducer;
import com.tagihan.Tagihan.Rabbit.CheckTagihan.Producer.CheckTagihanProducerRepository;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckTagihanController {

    @Autowired
    BpjsRepository bpjsRepository;

    @Autowired
    PLNRepository plnRepository;

    @Autowired
    IndihomeRepository indihomeRepository;

    @Autowired
    CheckTagihanProducerRepository checkTagihanProducerRepository;
//************************************************
    BPJSCLass bpjscLassDariRabbit = null;

    @RabbitListener(queues = "q.output.bpjs")
    public void getBpjsdariRabbit(BPJSCLass bpjscLass){
        bpjscLassDariRabbit = bpjscLass;
    }


    @GetMapping("/cektagihanbpjs")
    public BPJSCLass cekBpjs(@RequestBody User userReq){
        checkTagihanProducerRepository.addUserBpjs(userReq);

        return bpjscLassDariRabbit;
    }
//**************************************************************
    PLN plnDariRabbit = null;
    @RabbitListener(queues = "q.output.pln")
    public void getPlnDariRabbit(PLN pln){
        plnDariRabbit = pln;
    }

    @GetMapping("/cektagihanpln")
    public PLN cekPln(@RequestBody User userReq){
        checkTagihanProducerRepository.addUserPln(userReq);


        return plnDariRabbit;
    }

    //*********************************************************

    IndieHome indieHomeDariRabbit = null;

    @RabbitListener(queues = "q.output.indiehome")
    public void getIndiehomedarirabbit(IndieHome indieHome){
        indieHomeDariRabbit = indieHome;
    }

    @GetMapping("/cektagihanindihome")
    public IndieHome cekIndieHome(@RequestBody User userReq){
        checkTagihanProducerRepository.addUserIndihome(userReq);

        return indieHomeDariRabbit;
    }

}
