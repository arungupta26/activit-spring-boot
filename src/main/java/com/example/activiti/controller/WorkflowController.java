package com.example.activiti.controller;


import com.example.activiti.exception.InvalidTaskAssigneeException;
import com.example.activiti.model.ProcessBody;
import com.example.activiti.model.TaskDTO;
import com.example.activiti.service.IWorkflowService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RestController
@RequestMapping("/api/process")
public class WorkflowController {

    @Autowired
    private IWorkflowService workflowService;

    @GetMapping(value = "/deploy/{processName}")
    @ResponseStatus(value = OK)
    public void deploy(@PathVariable(name = "processName") String processName) {
        workflowService.deployProcess(processName);
    }


    @PostMapping(value = "/start/{processName}")
    public ResponseEntity<Map> start(@PathVariable(name = "processName") String processName ,
                                     @RequestBody ProcessBody processBody) {
        String processId = workflowService.startProcess(processName, processBody);
        return ResponseEntity.ok(Map.of("processId" , processId));
    }

    @GetMapping(value = "/task/{category}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<TaskDTO> getTask(@PathVariable(name = "category") String category){
        TaskDTO task = workflowService.getTask(category, "system");
        return ResponseEntity.ok(task);
    }

    @GetMapping(value = "/task/unassigneAll", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<TaskDTO>> unclaimAll(){
        List<TaskDTO> tasks = workflowService.unassigneAll();

        System.out.println(tasks);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping(value = "/task/status/{taskId}/{status}")
    public ResponseEntity<Map> markTask(@PathVariable(name = "taskId") String taskId ,@PathVariable(name = "status") String status ) {
        String response = workflowService.markTask(taskId, status);
        return ResponseEntity.ok(Map.of("status" , response));
    }
}


