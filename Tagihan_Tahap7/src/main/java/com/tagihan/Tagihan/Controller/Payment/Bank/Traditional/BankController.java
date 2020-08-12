package com.tagihan.Tagihan.Controller.Payment.Bank.Traditional;

import com.tagihan.Tagihan.Model.*;
import com.tagihan.Tagihan.Rabbit.Bank.Producer.BankProducerRepository;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    BpjsRepository bpjsRepository;

    @Autowired
    PLNRepository plnRepository;

    @Autowired
    BankProducerRepository bankProducerRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private String rekBankDariRabbit = "";

    @RabbitListener(queues = "qrek.bank")
    public void getRekBan(String rekBank){
        rekBankDariRabbit = rekBank;
    }

    @GetMapping("/daftarBank")
    public String getRekBank(){
        rabbitTemplate.convertSendAndReceive("xrekrequest","","request");
        return rekBankDariRabbit;
    }

    int danaTagihanBpjsDariRabbit;

    @RabbitListener(queues = "qdanatagihanbpjs.bank")
    public void getDanaTagihanBpjsDarirabbit(int danaTagihan){
        danaTagihanBpjsDariRabbit = danaTagihan;
    }


    private Login loginBpjsDariRabbit = new Login();
    @RabbitListener(queues = "bpjs.bank")
    public void getLoginTagihanBpjsDariRabbit(Login login){
        Login login1 = login;
        this.loginBpjsDariRabbit = login1;
    }


    @PostMapping("/bayarbpjsbank")
    public Bank bayarBpjsBank(@RequestBody Bank bank){
        bankProducerRepository.addBankBpjs(bank);
        Login login = this.loginBpjsDariRabbit;
        String loginUsername = login.getUserName();
        String bankUsername = bank.getUserName();
        int danaBank = bank.getTotalTransfer();
        System.out.println(danaTagihanBpjsDariRabbit);
        String rekTujuan = String.valueOf(bank.getNoRekTujuan());
        if (loginUsername.equals(bankUsername) && (danaBank >= danaTagihanBpjsDariRabbit) && (rekTujuan.equals(rekBankDariRabbit))){
            return bank;
        } else {
            return null;
        }
    }

    int danaTagihanPlnDariRabbit;

    @RabbitListener(queues = "qdanatagihanpln.bank")
    public void getDanaTagihanPlnDarirabbit(int danaTagihan){
        danaTagihanPlnDariRabbit = danaTagihan;
    }


    private Login loginPlnDariRabbit = new Login();
    @RabbitListener(queues = "qloginpln.bank")
    public void getLoginTagihanPlnDariRabbit(Login login){
        Login login1 = login;
        this.loginPlnDariRabbit = login1;
    }

    @PostMapping("/bayarplnbank")
    public Bank bayarPlnBank(@RequestBody Bank bank){
        bankProducerRepository.addBankPln(bank);
        Login login = this.loginPlnDariRabbit;
        String loginUsername = login.getUserName();
        String bankUsername = bank.getUserName();
        int danaBank = bank.getTotalTransfer();
        System.out.println(danaTagihanPlnDariRabbit);
        String rekTujuan = String.valueOf(bank.getNoRekTujuan());
        if (loginUsername.equals(bankUsername) && (danaBank >= danaTagihanPlnDariRabbit) && (rekTujuan.equals(rekBankDariRabbit))){
            return bank;
        } else {
            return null;
        }
    }

    @Autowired
    IndihomeRepository indihomeRepository;



    @PostMapping("/bayarinternetbank")
    public Bank bayarInternetBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        IndieHome indieHome = indihomeRepository.findById(bank.getIdDituju());
        int danaTagihan = indieHome.getJumlah_tagihan_internet();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekBankDariRabbit)){

            indieHome.setJumlah_tagihan_internet(0);
            indieHome.setStatus_tagihan_internet(true);
            indihomeRepository.editIndiHome(indieHome);
            return bank;
        }
        return null;
    }

    @PostMapping("/bayartvbank")
    public Bank bayarTvBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        IndieHome indieHome = indihomeRepository.findById(bank.getIdDituju());
        int danaTagihan = indieHome.getJumlah_tagihan_internet();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekBankDariRabbit)){

            indieHome.setJumlah_tagihan_tv(0);
            indieHome.setStatus_tagihan_tv(true);
            indihomeRepository.editIndiHome(indieHome);
            return bank;
        }
        return null;
    }

}
