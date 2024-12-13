package integration.environment;

public class GlassFishEnv extends PersistenceConfig {

    public static final String NAME_PERSISTENCE = "HotelMTA";

    @Override
    public boolean isJUnitRunning() {
        return false;
    }

    @Override
    public String getPersistenceName() {
        return GlassFishEnv.NAME_PERSISTENCE;
    }

}