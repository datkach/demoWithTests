package com.example.demowithtests.dto;

import lombok.ToString;

import java.time.LocalDate;

@ToString
public class PhotoReadDto {
    public LocalDate addedDate;
    public String url;
}
