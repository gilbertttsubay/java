package com.tagihan.Tagihan.Repository.BPJS;

import com.tagihan.Tagihan.Model.BPJSCLass;
import com.tagihan.Tagihan.Model.Login;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BpjsRepositoryImpl implements BpjsRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public void addBpjs(BPJSCLass BPJSCLass) {

    }

    @Override
    public void deleteBpjs(int id) {

    }

    @Override
    @Transactional
    public BPJSCLass findById(String id) {
        BPJSCLass bpjscLass = null;

        List<BPJSCLass> bpjscLassList = findAllBpjs();

        for (int i = 0; i <= bpjscLassList.size(); i++){
            bpjscLass = bpjscLassList.get(i);
            if (bpjscLass.getIdBpjs().equals(id)){
                return bpjscLass;
            }
        }
        return bpjscLass;
    }

    @Override
    public BPJSCLass findByUserName(String userName) {
        return null;
    }

    @Override
    @Transactional
    public List<BPJSCLass> findAllBpjs() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<BPJSCLass> theQuery = currentSession.createQuery("from BPJSCLass", BPJSCLass.class);

        List<BPJSCLass> BPJSCLassList = theQuery.getResultList();

        return BPJSCLassList;
    }

    @Override
    @Transactional
    public void editBpjs(BPJSCLass BPJSCLass) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(BPJSCLass);
    }
}
