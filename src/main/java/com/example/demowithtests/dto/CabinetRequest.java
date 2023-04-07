package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CabinetRequest {

    @NotNull
    public String name;
    @NotNull
    public Integer capacity;

    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;
}
