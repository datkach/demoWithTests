package com.example.demowithtests.web;

import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import com.example.demowithtests.service.PassportService;
import com.example.demowithtests.util.config.PassportMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@Tag(name = "Employee", description = "Employee API")
public class PassportControllerBean implements PassportController {
    private final PassportService passportService;

    @Override
    @PostMapping("/passports")
    @ResponseStatus(HttpStatus.CREATED)
    public PassportResponseDto savePassport(@RequestBody PassportRequestDto requestForSave) {
        return PassportMapper.INSTANCE
                .toDto(passportService
                        .create(PassportMapper.INSTANCE.fromDto(requestForSave)));
    }

    @Override
    @PutMapping("/passports/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseDto refreshPassport(@PathVariable Integer id,@RequestBody PassportRequestDto requestForSave) {
        return PassportMapper.INSTANCE
                .toDto(passportService
                        .updateById(id, PassportMapper.INSTANCE.fromDto(requestForSave)));
    }

    @Override
    @GetMapping("/passports/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseDto getPassportById(@PathVariable Integer id) {
        return PassportMapper.INSTANCE
                .toDto(passportService
                        .getById(id));
    }

    @Override
    @PatchMapping("/passports/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePassportById(@PathVariable Integer id) {
        passportService.removeById(id);
    }
}
