package com.tagihan.Tagihan.Repository.User;

import com.tagihan.Tagihan.Model.User;

import java.util.List;

public interface UserRepository {
    public void addUser(User user);

    public void deleteUser(int id);

    public User findById(int id);

    public User findByBpjsId(String bpjsId);

    public User findByPlnId(String plnId);

    public User findByUserName(String userName);

    public List<User> findAllUser();

    public void editUser(User user);

    public User findByIndihomeId(String indieHomeId);



}
