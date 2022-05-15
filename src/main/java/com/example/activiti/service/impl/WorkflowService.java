package com.example.activiti.service.impl;

import com.example.activiti.model.ProcessBody;
import com.example.activiti.service.IWorkflowService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class WorkflowService implements IWorkflowService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessEngine processEngine;

    @Override
    public void deployProcess(String processName) {
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/"+processName+".bpmn20.xml")
                .deploy();

        log.info(format("DEPLOYMENT ID: %s", deployment.getId()));
        log.info(format("DEPLOYMENT NAME: %s", deployment.getName()));
    }

    @Override
    public String startProcess(String processName, ProcessBody processBody) {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processName);
        log.info(format("PROCESS INSTANCE ID: %s", processInstance.getId()));
        log.info(format("PROCESS INSTANCE DEF ID: %s", processInstance.getProcessDefinitionId()));

        return processInstance.getId();
    }
}
