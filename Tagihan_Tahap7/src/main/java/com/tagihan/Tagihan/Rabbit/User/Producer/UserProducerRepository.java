package com.tagihan.Tagihan.Rabbit.User.Producer;

import com.tagihan.Tagihan.Model.User;

import java.util.List;

public interface UserProducerRepository {
    public void addRegistrasi(User user);
    public void getUsername(User user);
    public void getList();
    public void hapusUser(int id);
    public void editUser(User user);
}
