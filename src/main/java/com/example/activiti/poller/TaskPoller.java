package com.example.activiti.poller;


import com.example.activiti.model.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class TaskPoller implements ApplicationRunner {

    String  taskCategory = "VOUCHER_ORDER_TASK";

    String base_url = "http://localhost:8080";


    @Override
    public void run(ApplicationArguments args) {

        while (true) {
            try {
                RestTemplate restTemplate = new RestTemplate();

                ResponseEntity<TaskDTO> response = restTemplate.getForEntity(base_url +
                                "/api/process/task/" + taskCategory,
                        TaskDTO.class);

                if (response.getStatusCode().is2xxSuccessful()) {

                    processTask(response.getBody());

                } else {
                    log.info("No tasks for category {}", taskCategory);
                    Thread.sleep(1*60*1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            break;
        }

    }

    private void processTask(TaskDTO task) {
        try {
            if (task == null) {
                return;
            }

            log.info("Processing task {}", task.getId());
            Thread.sleep(10000);
            log.info("Processing completed for task {}", task.getId());
            completeTask(task);

        }catch (Exception e){
            failTask(task);
        }
    }

    private void failTask(TaskDTO task) {

        log.info("Task failed id: {} ", task.getId());
        markTaskWithStatus(task,"Failed");
    }

    private void completeTask(TaskDTO task) {
        markTaskWithStatus(task , "Complete");
    }

    private void markTaskWithStatus(TaskDTO task, String status) {

        log.info("Completing the task {} ", task.getId());
        RestTemplate template = new RestTemplate();

        String url = base_url + "/api/process/task/status/" + task.getId() + "/" + status;
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Task {}  completed successfully ", task.getId());
        }else {
            log.error("error while completing the task {}", task.getId());
        }

    }
}
