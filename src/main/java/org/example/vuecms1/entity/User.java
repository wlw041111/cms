package org.example.vuecms1.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class User {
    private int id;
    private String username;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String role;
}
