package tfg.hotelmta.integration.transaction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TransactionManagerImp extends TransactionManager {

    private ConcurrentMap<Thread, Transaction> transactions;

    public TransactionManagerImp() {
        this.transactions = new ConcurrentHashMap<>();
    }

    @Override
    public Transaction newTransaccion() {
        Thread thread = Thread.currentThread();
        if (!this.transactions.containsKey(thread)) {
            Transaction transaction = TransactionFactory.getInstance().getTransaction();
            this.transactions.put(thread, transaction);
        }
        return this.transactions.get(thread);
    }

    @Override
    public Transaction getTransaccion() {
        return this.transactions.get(Thread.currentThread());
    }

    @Override
    public void removeTransaccion() {
        this.transactions.remove(Thread.currentThread());
    }

}
