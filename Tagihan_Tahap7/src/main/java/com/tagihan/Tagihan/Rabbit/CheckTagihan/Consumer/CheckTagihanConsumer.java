package com.tagihan.Tagihan.Rabbit.CheckTagihan.Consumer;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.CheckTagihan.Producer.CheckTagihanProducerRepository;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckTagihanConsumer {

    @Autowired
    BpjsRepository bpjsRepository;

    @Autowired
    PLNRepository plnRepository;

    @Autowired
    IndihomeRepository indihomeRepository;

    @Autowired
    CheckTagihanProducerRepository checkTagihanProducerRepository;


    @RabbitListener(queues = "q.input.bpjs.cektagihan")
    public void cekBpjs(User userReq){
        String idBpjs = userReq.getNoBpjs();

        BPJSCLass bpjscLass = bpjsRepository.findById(idBpjs);

        checkTagihanProducerRepository.addBpjs(bpjscLass);
    }

    @RabbitListener(queues = "q.input.pln.cektagihan")
    public void cekPln(User userReq){
        String idPln = userReq.getNoPln();

        PLN pln = plnRepository.findById(idPln);
        checkTagihanProducerRepository.addPln(pln);
    }

    @RabbitListener(queues = "q.input.indiehome.cektagihan")
    public void cekIndiehome(User userReq){
        String idIndihome = userReq.getNoIndihome();

        IndieHome indieHome = indihomeRepository.findById(idIndihome);
        checkTagihanProducerRepository.addIndieHome(indieHome);
    }
}
