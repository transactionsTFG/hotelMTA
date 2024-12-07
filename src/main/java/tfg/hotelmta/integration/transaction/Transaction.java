package tfg.hotelmta.integration.transaction;

public interface Transaction {

    void start();

    void commit();

    void rollback();

    Object getResource();
}
