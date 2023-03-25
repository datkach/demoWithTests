package com.example.demowithtests.web;

import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;

public interface PassportController {
    PassportResponseDto savePassport(PassportRequestDto requestForSave);

    PassportResponseDto refreshPassport(Integer id, PassportRequestDto requestForSave);

    PassportResponseDto getPassportById(Integer id);

    void removePassportById(Integer id);
}
