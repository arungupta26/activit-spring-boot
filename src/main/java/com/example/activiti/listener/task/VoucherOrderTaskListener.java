package com.example.activiti.listener.task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("voucherOrderTaskListener")
public class VoucherOrderTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {


        log.info("Task id {} with event {} and params {}", delegateTask.getId() , delegateTask.getEventName(), delegateTask.getVariables());

    }
}
