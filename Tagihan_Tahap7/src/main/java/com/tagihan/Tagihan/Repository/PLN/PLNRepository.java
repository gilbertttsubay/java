package com.tagihan.Tagihan.Repository.PLN;

import com.tagihan.Tagihan.Model.PLN;

import java.util.List;

public interface PLNRepository {
    public void addPln(PLN pln);

    public void deletePln(int id);

    public PLN findById(String id);

    public PLN findByUserName(String userName);

    public List<PLN> findAllPln();

    public void editPLN(PLN pln);
}
