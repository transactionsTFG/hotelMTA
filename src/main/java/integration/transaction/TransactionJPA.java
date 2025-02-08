package integration.transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import integration.SingletonEntityManager;

public class TransactionJPA implements Transaction {

    private final EntityManager entityManager;

    public TransactionJPA() {
        this.entityManager = SingletonEntityManager.getInstance().createEntityManager();
    }

    @Override
    public void start() {
        this.entityManager.getTransaction().begin();
    }

    @Override
    public void commit() {
        final EntityTransaction transaction = this.entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
        this.entityManager.close();
        TransactionManager.getInstance().removeTransaccion();
    }

    @Override
    public void rollback() {
        final EntityTransaction transaction = this.entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.rollback();
        }
        this.entityManager.close();
        TransactionManager.getInstance().removeTransaccion();
    }

    @Override
    public Object getResource() {
        return this.entityManager;
    }

}
