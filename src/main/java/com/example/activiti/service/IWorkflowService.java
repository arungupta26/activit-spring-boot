package com.example.activiti.service;

import com.example.activiti.model.ProcessBody;
import com.example.activiti.model.TaskDTO;

import java.util.List;

public interface IWorkflowService {

    void deployProcess(String processName);

    String startProcess(String processName, ProcessBody processBody);

    TaskDTO getTask(String category, String callerUsername);

    List<TaskDTO> unassigneAll();

    String markTask(String taskId, String status);

}
