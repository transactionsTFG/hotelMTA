package integration.transaction;

public abstract class TransactionFactory {

    private static TransactionFactory instance;

    protected TransactionFactory() {
    }

    public static synchronized TransactionFactory getInstance() {
        if (instance == null) {
            instance = new TransactionFactoryImp();
        }
        return instance;
    }

    public abstract Transaction getTransaction();
}
