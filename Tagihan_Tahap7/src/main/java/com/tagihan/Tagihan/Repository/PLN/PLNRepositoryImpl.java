package com.tagihan.Tagihan.Repository.PLN;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.PLN;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PLNRepositoryImpl implements PLNRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public void addPln(PLN pln) {

    }

    @Override
    public void deletePln(int id) {

    }

    @Override
    @Transactional
    public PLN findById(String id) {
        PLN pln = null;

        List<PLN> plnList = findAllPln();

        for (int i = 0; i < plnList.size(); i++){
            pln = plnList.get(i);
            if (pln.getIdPln().equals(id)){
                return pln;
            }
        }
        return pln;
    }

    @Override
    public PLN findByUserName(String userName) {
        return null;
    }

    @Override
    @Transactional
    public List<PLN> findAllPln() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<PLN> theQuery = currentSession.createQuery("from PLN",PLN.class);

        List<PLN> plnList = theQuery.getResultList();

        return plnList;
    }

    @Override
    @Transactional
    public void editPLN(PLN pln) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(pln);
    }
}
