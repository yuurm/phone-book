package org.example.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLog {
    int id;
    int userId;
    String activity;
    LocalDateTime logTime;
}
