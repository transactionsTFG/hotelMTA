package integration.transaction;

public abstract class TransactionManager {

    protected TransactionManager() {
    }
    private static TransactionManager instance;

    public static synchronized TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManagerImp();
        }
        return instance;
    }

    public abstract Transaction newTransaccion();

    public abstract Transaction getTransaccion();

    public abstract void removeTransaccion();
}
