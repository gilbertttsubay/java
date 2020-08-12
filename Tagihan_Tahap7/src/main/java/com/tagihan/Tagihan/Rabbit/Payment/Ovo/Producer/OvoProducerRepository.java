package com.tagihan.Tagihan.Rabbit.Payment.Ovo.Producer;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;

public interface OvoProducerRepository {

    public void sendUserBpjs(User user);
    public void sendUserPln(User user);
    public void sendUserIndieHomeInternet(User user);
    public void sendLogin(Login login);
    public void sendDanaTransfer(int danaTransfer);
    public void sendDanaTagihan(int danaTagihan);
    public void sendDanaTagihanInternet(int danaTagihan);
    public void sendDanaTagihanTv(int danaTagihan);

    public void sendLoginTv(Login login);
    public void sendLoginInternet(Login login);

    }
