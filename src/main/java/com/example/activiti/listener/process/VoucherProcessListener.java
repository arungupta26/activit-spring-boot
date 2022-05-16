package com.example.activiti.listener.process;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("voucherProcessListener")
public class VoucherProcessListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) {

        String processId = execution.getProcessInstanceId();

        String eventName = execution.getEventName();

        log.info("Process id {} and event {}", processId, eventName);
        System.out.println("Process id " +  processId +  " and event "+ processId );
        switch (eventName){

            case "START":
                log.info("[START]process id {} is "+ eventName);
                break;

            case "END":
                log.info("[END]process id {} is "+ eventName);
                break;

            default:
                log.info("[default]process id {} is "+ eventName);
                break;

        }


    }
}
