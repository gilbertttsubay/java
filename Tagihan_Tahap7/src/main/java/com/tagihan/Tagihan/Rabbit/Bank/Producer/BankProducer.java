package com.tagihan.Tagihan.Rabbit.Bank.Producer;

import com.tagihan.Tagihan.Model.Bank;
import com.tagihan.Tagihan.Model.Login;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BankProducer implements BankProducerRepository {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void addBankBpjs(Bank bank){
        rabbitTemplate.convertSendAndReceive("xbankbpjs.bank","",bank);
    }

    public void addBankPln(Bank bank){
        rabbitTemplate.convertSendAndReceive("xbankpln.bank","",bank);
    }

    public void kirimDanaTagihanBpjs(int danaTagihan){
        rabbitTemplate.convertSendAndReceive("xdanatagihanbpjs.bank","",danaTagihan);
    }

    public void kirimDanaTagihanPln(int danaTagihan){
        rabbitTemplate.convertSendAndReceive("xdanatagihanpln.bank","",danaTagihan);
    }

    public void kirimLoginBpjs(Login login){
        rabbitTemplate.convertSendAndReceive("xloginbpjs.bank","",login);
    }

    public void kirimLoginPln(Login login){
        rabbitTemplate.convertSendAndReceive("xloginpln.bank","",login);
    }

    public void kirimRekBank(String rekBank){
        rabbitTemplate.convertSendAndReceive("xrek.bank","",rekBank);
    }
}
