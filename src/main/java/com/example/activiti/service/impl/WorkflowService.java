package com.example.activiti.service.impl;

import com.example.activiti.mapper.TaskDTOMapper;
import com.example.activiti.model.ProcessBody;
import com.example.activiti.model.TaskDTO;
import com.example.activiti.service.IWorkflowService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

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

    @Override
    public TaskDTO getTask(String category, String callerUsername){
        TaskService taskService = processEngine.getTaskService();

        List<Task> tasks = taskService.createTaskQuery()
                .taskUnassigned()
                .taskCategory(category)
                .taskUnassigned()
                .orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc()
                .list();

        if (tasks !=null && tasks.size()>0){
            AtomicReference<Task> getTask = new AtomicReference<>();

            for (Task task : tasks) {
                String taskid = task.getId();
                synchronized (taskid) {
                    Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
                    if (t.getClaimTime() == null) {
                        taskService.claim(taskid, "system_user_" + System.currentTimeMillis());
                        getTask.set(task);
                        break;
                    }
                }
            }
            return TaskDTOMapper.toTaskDTO(getTask.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> unassigneAll() {

        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().list();

        list.stream().forEach(t-> taskService.unclaim(t.getId()));

        return list.stream()
                .map(TaskDTOMapper::toTaskDTO)
                .collect(toList());
    }

    @Override
    public String markTask(String taskId, String status) {

        processEngine.getTaskService().complete(taskId, Map.of("status", status));

        return status;
    }
}
