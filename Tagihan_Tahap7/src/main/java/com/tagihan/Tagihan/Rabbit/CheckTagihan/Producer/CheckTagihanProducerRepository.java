package com.tagihan.Tagihan.Rabbit.CheckTagihan.Producer;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.PLN;
import com.tagihan.Tagihan.Model.User;

public interface CheckTagihanProducerRepository {
    public void addUserBpjs(User user);
    public void addBpjs(BPJSCLass bpjscLass);
    public void addUserPln(User user);
    public void addPln(PLN pln);
    public void addUserIndihome(User user);
    public void addIndieHome(IndieHome indieHome);
}
