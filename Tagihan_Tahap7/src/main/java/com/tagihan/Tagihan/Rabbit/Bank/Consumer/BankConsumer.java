package com.tagihan.Tagihan.Rabbit.Bank.Consumer;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Bank;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Rabbit.Bank.Producer.BankProducer;
import com.tagihan.Tagihan.Rabbit.Bank.Producer.BankProducerRepository;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BankConsumer {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    BpjsRepository bpjsRepository;

    @Autowired
    PLNRepository plnRepository;

    @Autowired
    BankProducerRepository bankProducerRepository;

    private String rekBank = "1234567890";

    @RabbitListener(queues = "qrekrequest")
    public void kirimRekBank(String message){
        bankProducerRepository.kirimRekBank(rekBank);
    }


    @RabbitListener(queues = "qbankbpjs.bank")
    public void addBankBpjs(Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        bankProducerRepository.kirimLoginBpjs(login);
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        BPJSCLass bpjscLass = bpjsRepository.findById(bank.getIdDituju());
        int danaTagihan = bpjscLass.getJumlahTagihan();
        bankProducerRepository.kirimDanaTagihanBpjs(danaTagihan);
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekBank)){
            bpjscLass.setJumlahTagihan(0);
            bpjscLass.setStatusTagihan(true);;
            bpjsRepository.editBpjs(bpjscLass);
        } else{
            bpjsRepository.editBpjs(null);
        }

    }

    @RabbitListener(queues = "qbankpln.bank")
    public void addBankPln(Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        bankProducerRepository.kirimLoginPln(login);
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        PLN pln = plnRepository.findById(bank.getIdDituju());
        int danaTagihan = pln.getJumlahTagihan();
        bankProducerRepository.kirimDanaTagihanPln(danaTagihan);

        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekBank)){

            pln.setJumlahTagihan(0);
            pln.setStatusTagihan(true);;
            plnRepository.editPLN(pln);

        } else{
           plnRepository.editPLN(null);
        }

    }
}
