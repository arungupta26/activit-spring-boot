package com.example.activiti;


import com.example.activiti.model.ProcessBody;
import com.example.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;


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
    @ResponseBody
    public ResponseEntity<Map> start(@PathVariable(name = "processName") String processName ,
                                     @RequestBody ProcessBody processBody) {
        String processId = workflowService.startProcess(processName, processBody);
        return ResponseEntity.ok(Map.of("processId" , processId));
    }


}


