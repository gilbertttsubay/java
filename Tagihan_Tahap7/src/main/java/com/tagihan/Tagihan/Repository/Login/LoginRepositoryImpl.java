package com.tagihan.Tagihan.Repository.Login;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class LoginRepositoryImpl implements LoginRepository{

    @Autowired
    EntityManager entityManager;


    @Override
    @Transactional
    public void login(Login login) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(login);

    }

    @Override
    @Transactional
    public void deleteLogin(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("delete from Login where id=:idLogin");

        theQuery.setParameter("idLogin",id);
        theQuery.executeUpdate();
    }

    @Override
    @Transactional
    public List<Login> findAllLogin() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Login> theQuery = currentSession.createQuery("from Login",Login.class);

        List<Login> loginList = theQuery.getResultList();

        return loginList;

    }

    @Override
    @Transactional
    public Login findByUserName(String userName) {
        Login login = null;

        List<Login> loginList = findAllLogin();

        for (int i = 0; i < loginList.size(); i++){
            login = loginList.get(i);
            if (login.getUserName().equals(userName)){
                return login;
            }
        }
        return login;

    }
}
