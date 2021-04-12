package services.impl;

import com.example.pocfirestoregcp.dao.ConfiguratorDao;
import com.example.pocfirestoregcp.pojo.ConfigurationPojo;
import services.ConfiguratorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ConfigurationServicesImpl implements ConfiguratorService {
    @Autowired
    ConfiguratorDao configuratorDao;
    public ConfigurationPojo insert(final ConfigurationPojo configuration) throws ExecutionException,
            InterruptedException {
        ConfigurationPojo lastSaveConfiguration = configuratorDao.findByApplicationAndIndentifierIgnoringDeletedOn(
                configuration.getAplication(), configuration.getIdentifier());
        if (lastSaveConfiguration == null) {
            configuration.setVersion(0);
            configuration.setCreatedOn(new Date());
        }else
        {
            Integer newVersion = lastSaveConfiguration.getVersion()+1;
            configuration.setVersion(newVersion);
            configuration.setCreatedOn(new Date());
        }
        return configuratorDao.save(configuration);
    }
}
