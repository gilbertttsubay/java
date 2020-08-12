package com.tagihan.Tagihan.Repository.BPJS;

import com.tagihan.Tagihan.Model.BPJSCLass;

import java.util.List;

public interface BpjsRepository {
    public void addBpjs(BPJSCLass BPJSCLass);

    public void deleteBpjs(int id);

    public BPJSCLass findById(String id);

    public BPJSCLass findByUserName(String userName);

    public List<BPJSCLass> findAllBpjs();

    public void editBpjs(BPJSCLass BPJSCLass);
}
