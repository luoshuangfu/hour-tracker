package org.mahotao.hourtracker.entity;

import java.time.LocalDate;

public class Block {
    private int id;
    private int userId;
    private LocalDate date;
    private Integer hour;

    private String expectedGoal;
    private String actualResult;

    private Integer score;
    private String review;
}
