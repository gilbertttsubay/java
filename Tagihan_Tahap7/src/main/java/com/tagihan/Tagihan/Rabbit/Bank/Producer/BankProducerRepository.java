package com.tagihan.Tagihan.Rabbit.Bank.Producer;

import com.tagihan.Tagihan.Model.Bank;
import com.tagihan.Tagihan.Model.Login;

public interface BankProducerRepository {
    public void addBankBpjs(Bank bank);
    public void addBankPln(Bank bank);
    public void kirimDanaTagihanBpjs(int danaTagihan);
    public void kirimDanaTagihanPln(int danaTagihan);
    public void kirimLoginBpjs(Login login);
    public void kirimLoginPln(Login login);
    public void kirimRekBank(String rekBank);
}
