package services;

import com.example.pocfirestoregcp.pojo.ConfigurationPojo;

import java.util.concurrent.ExecutionException;

public interface ConfiguratorService {
    ConfigurationPojo insert(final ConfigurationPojo configurationPojo) throws ExecutionException, InterruptedException;
}
