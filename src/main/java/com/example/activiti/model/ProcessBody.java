package com.example.activiti.model;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessBody {

    String processName;
    String processDefId;
    Map<String, String> properties;

}
