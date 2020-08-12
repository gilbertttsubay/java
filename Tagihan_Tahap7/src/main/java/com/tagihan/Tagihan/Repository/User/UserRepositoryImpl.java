package com.tagihan.Tagihan.Repository.User;

import com.tagihan.Tagihan.Model.Login;
import com.tagihan.Tagihan.Model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(user);

    }

    @Override
    @Transactional
    public void editUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("delete from User where id=:id");

        theQuery.setParameter("id",id);
        theQuery.executeUpdate();
    }

    @Override
    @Transactional
    public User findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        User user = currentSession.get(User.class,id);

        return user;
    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
       Session currentSession = entityManager.unwrap(Session.class);

       User user = null;
       List<User> userList = findAllUser();
       for (int i = 0; i <= userList.size(); i++){
           user = userList.get(i);
           if (user.getUserName().equals(userName)){
               return user;
           }
       }
       return user;
    }

    @Override
    @Transactional
    public List<User> findAllUser() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User",User.class);

        List<User> userList = theQuery.getResultList();

        return userList;
    }

    @Override
    @Transactional
    public User findByBpjsId(String bpjsId) {
        User user = null;

        List<User> userList = findAllUser();

        for (int i = 0; i <= userList.size(); i++){
            user = userList.get(i);
            if (user.getNoBpjs().equals(bpjsId)){
                return user;
            }
        }
        return user;
    }

    @Override
    @Transactional
    public User findByPlnId(String plnId) {
        User user = null;

        List<User> userList = findAllUser();

        for (int i = 0; i < userList.size(); i++){
            user = userList.get(i);
            if (user.getNoPln().equals(plnId)){
                return user;
            }
        }
        return user;
    }

    @Override
    @Transactional
    public User findByIndihomeId(String indieHomeId) {
        User user = null;

        List<User> userList = findAllUser();

        for (int i = 0; i < userList.size(); i++){
            user = userList.get(i);
            if (user.getNoIndihome().equals(indieHomeId)){
                return user;
            }
        }
        return user;
    }
}
