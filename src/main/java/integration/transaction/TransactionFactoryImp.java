package integration.transaction;

public class TransactionFactoryImp extends TransactionFactory {

    @Override
    public Transaction getTransaction() {
        return new TransactionJPA();
    }

}
