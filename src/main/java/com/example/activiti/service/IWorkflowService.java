package com.example.activiti.service;

import com.example.activiti.model.ProcessBody;

public interface IWorkflowService {

    void deployProcess(String processName);

    String startProcess(String processName, ProcessBody processBody);
}
