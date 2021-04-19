package com.example.demo.service;

import com.example.demo.dao.ConfigurationDao;

import com.example.demo.pojo.ConfigurationPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ConfigurationService {

    @Autowired
    ObjectMapper mapper;


    ConfigurationDao configurationDao= new ConfigurationDao();
    public ConfigurationPojo insert(final ConfigurationPojo configuration) throws ExecutionException, InterruptedException {
        ConfigurationPojo lastSaveConfiguration = configurationDao.findByApplicationAndIndentifierIgnoringDeletedOn(
                configuration.getApplication(), configuration.getIdentifier());
        if (lastSaveConfiguration == null) {
            configuration.setVersion(0);
            configuration.setCreatedOn(new Date());
        }else
        {
            Integer newVersion = lastSaveConfiguration.getVersion()+1;
            configuration.setVersion(newVersion);
            configuration.setEditedOn(new Date());
        }
        return configurationDao.save(configuration);
    }
    public ConfigurationPojo softDelete(final ConfigurationPojo configuration) throws ExecutionException, InterruptedException {
        ConfigurationPojo lastSaveConfiguration=configurationDao.findByApplicationAndIndentifier(configuration.getApplication(),configuration.getIdentifier());
        // AssertUtils.notNull(lastSaveConfiguration,"Tryting to Delete an unexistent object",HttpStatus.NOT_FOUND);
         if (lastSaveConfiguration !=null)
        {
            lastSaveConfiguration.setDeletedOn(new Date());
            return configurationDao.save(lastSaveConfiguration);
        }
        return null;
    }
    public List<ConfigurationPojo> getConfigurator(final ConfigurationPojo configuration) throws ExecutionException, InterruptedException {
        if ("*".equals(configuration.getIdentifier())) {
            return configurationDao.findByApplication(configuration.getApplication());
        }
        List<ConfigurationPojo> configurations = new ArrayList<>();
        if(configuration.getVersion()!=null) {
            configurations.add(configurationDao.findByApplicationAndIndentifierAndVersion(configuration.getApplication(),
                    configuration.getIdentifier(),
                    configuration.getVersion()));
        }
        else
        {
            configurations.add(configurationDao.findByApplicationAndIndentifier(configuration.getApplication(),
                    configuration.getIdentifier()));
        }
        return configurations;
    }
    public ConfigurationPojo validateSaveConfiguration(String configurationJson) throws JsonProcessingException {
        ConfigurationPojo configuration = this.mapper .readValue(configurationJson, ConfigurationPojo.class);
        return configuration;
    }
    public ConfigurationPojo validateDeleteConfiguration(String configurationJson) throws JsonProcessingException {
        ConfigurationPojo configuration = this.mapper .readValue(configurationJson, ConfigurationPojo.class);
        return configuration;
    }

}
