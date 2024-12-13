package integration.environment;

public class JUnitEnv extends PersistenceConfig {

    @Override
    public boolean isJUnitRunning() {
        return true;
    }

    @Override
    public String getPersistenceName() {
        return "HotelMTATest";
    }

}