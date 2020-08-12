package com.tagihan.Tagihan.Repository.Indihome;

import com.tagihan.Tagihan.Model.IndieHome;
import com.tagihan.Tagihan.Model.PLN;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class IndiHomeRepositoryImpl implements IndihomeRepository {



    @Autowired
    EntityManager entityManager;

    @Override
    public void addIndihome(IndieHome indieHome) {

    }

    @Override
    public void deleteIndiHome(int id) {

    }



    @Override
    public IndieHome findByUserName(String userName) {
        return null;
    }

    @Override
    @Transactional
    public List<IndieHome> findAllIndieHome() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<IndieHome> theQuery = currentSession.createQuery("from IndieHome",IndieHome.class);

        List<IndieHome> indieHomeList = theQuery.getResultList();

        return indieHomeList;
    }

    @Override
    @Transactional
    public IndieHome findById(String id) {
        List<IndieHome> indieHomeList = findAllIndieHome();

        for (int i = 0; i < indieHomeList.size(); i++){
            IndieHome indieHome = indieHomeList.get(i);
            if (indieHome.getIdIndihome().equals(id)){
                return indieHome;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void editIndiHome(IndieHome indieHome) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(indieHome);
    }
}
