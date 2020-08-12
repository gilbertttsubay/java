package com.tagihan.Tagihan.Controller.Payment.Bank.Virtual;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Bank;
import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Repository.BPJS.BpjsRepository;
import com.tagihan.Tagihan.Repository.Login.LoginRepository;
import com.tagihan.Tagihan.Repository.PLN.PLNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/virtual")
@RestController
public class VirtualController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    BpjsRepository bpjsRepository;

    @Autowired
    PLNRepository plnRepository;

    private String rekVirtual = "";

    @GetMapping("/generateCode")
    public String getRekVirtual(){
        int rek= (int) (1234567890 + (Math.random()*10));
        rekVirtual = String.valueOf(rek);
        return rekVirtual;
    }

    @PostMapping("/bayarbpjsvirtual")
    public Bank bayarBpjsBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        BPJSCLass bpjscLass = bpjsRepository.findById(bank.getIdDituju());
        int danaTagihan = bpjscLass.getJumlahTagihan();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekVirtual)){

            bpjscLass.setJumlahTagihan(0);
            bpjscLass.setStatusTagihan(true);;
            bpjsRepository.editBpjs(bpjscLass);
            return bank;
        }
        return null;
    }

    @PostMapping("/bayarplnvirtual")
    public Bank bayarPlnBank(@RequestBody Bank bank){
        Login login = loginRepository.findByUserName(bank.getUserName());
        String noRekTujuan = String.valueOf(bank.getNoRekTujuan());
        int totalBankDana = bank.getTotalTransfer();
        PLN pln = plnRepository.findById(bank.getIdDituju());
        int danaTagihan = pln.getJumlahTagihan();
        if (totalBankDana >= danaTagihan && (login.getUserName().equals(bank.getUserName())) && noRekTujuan.equals(rekVirtual)){

            pln.setJumlahTagihan(0);
            pln.setStatusTagihan(true);;
            plnRepository.editPLN(pln);
            return bank;
        }
        return null;
    }


}
