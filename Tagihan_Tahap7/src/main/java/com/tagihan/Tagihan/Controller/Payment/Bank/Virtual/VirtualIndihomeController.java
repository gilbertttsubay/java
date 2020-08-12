package com.tagihan.Tagihan.Controller.Payment.Bank.Virtual;

import com.tagihan.Tagihan.Model.Bank;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Repository.Indihome.IndihomeRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/virtualIndihome")
public class VirtualIndihomeController {


    @Autowired
    LoginRepository loginRepository;

    @Autowired
    IndihomeRepository indihomeRepository;

    private String rekVirtual = "";

    @GetMapping("/generateCode")
    public String getRekVirtual(){
        int rek= (int) (1234567890 + (Math.random()*10));
        rekVirtual = String.valueOf(rek);
        return rekVirtual;
    }

    @PostMapping("/bayarvirtualinternetbank")
    public Bank bayarInternetBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        IndieHome indieHome = indihomeRepository.findById(bank.getIdDituju());
        int danaTagihan = indieHome.getJumlah_tagihan_internet();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekVirtual)){

            indieHome.setJumlah_tagihan_internet(0);
            indieHome.setStatus_tagihan_internet(true);
            indihomeRepository.editIndiHome(indieHome);
            return bank;
        }
        return null;
    }

    @PostMapping("/bayarvirtualtvbank")
    public Bank bayarTvBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        IndieHome indieHome = indihomeRepository.findById(bank.getIdDituju());
        int danaTagihan = indieHome.getJumlah_tagihan_internet();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekVirtual)){

            indieHome.setJumlah_tagihan_tv(0);
            indieHome.setStatus_tagihan_tv(true);
            indihomeRepository.editIndiHome(indieHome);
            return bank;
        }
        return null;
    }
}
