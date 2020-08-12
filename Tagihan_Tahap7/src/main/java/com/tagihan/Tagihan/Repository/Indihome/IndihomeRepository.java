package com.tagihan.Tagihan.Repository.Indihome;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.IndieHome;

import java.util.List;

public interface IndihomeRepository {
    public void addIndihome(IndieHome indieHome);

    public void deleteIndiHome(int id);

    public IndieHome findById(String id);

    public IndieHome findByUserName(String userName);

    public List<IndieHome> findAllIndieHome();

    public void editIndiHome(IndieHome indieHome);
}
