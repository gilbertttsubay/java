package com.tagihan.Tagihan.Repository.Login;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LoginRepository {
    public void login(Login login);

    public void deleteLogin(int id);
//
//    public User findById(int id);
//
    public Login findByUserName(String userName);
//
    public List<Login> findAllLogin();
//
//    public void editUser(User user);
}
