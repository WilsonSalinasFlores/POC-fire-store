package com.example.demo.controller;

import com.example.demo.objects.Person;
import com.example.demo.pojo.ConfigurationPojo;
import com.example.demo.service.ConfigurationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class ConfiguratorController {
    @Autowired
    ConfigurationService configurationService;
    @PostMapping(value ="/api/configurator/save", consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> insert(@RequestBody String jsonRequest) throws InterruptedException, ExecutionException, JsonProcessingException {
        ConfigurationPojo configurationPojo= configurationService.validateSaveConfiguration(jsonRequest);
        return new ResponseEntity<>(configurationService.insert(configurationPojo),HttpStatus.OK);
    }
    @PostMapping(value ="/api/configurator/delete", consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> delete(@RequestBody String jsonRequest) throws InterruptedException, ExecutionException, JsonProcessingException {
        ConfigurationPojo configurationPojo= configurationService.validateDeleteConfiguration(jsonRequest);
        return new ResponseEntity<>(configurationService.softDelete(configurationPojo),HttpStatus.OK);
    }
    @PostMapping(value ="/api/configurator/read", consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> read(@RequestBody String jsonRequest) throws InterruptedException, ExecutionException, JsonProcessingException {
        ConfigurationPojo configurationPojo = configurationService.validateDeleteConfiguration(jsonRequest);
        List<ConfigurationPojo> configurations = configurationService.getConfigurator(configurationPojo);
        Object response = (configurations.size() > 1) ? configurations : configurations.get(0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
