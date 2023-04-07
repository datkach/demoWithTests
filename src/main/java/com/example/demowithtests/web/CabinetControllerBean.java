package com.example.demowithtests.web;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetRequest;
import com.example.demowithtests.dto.CabinetResponse;
import com.example.demowithtests.service.CabinetService;
import com.example.demowithtests.util.config.CabinetMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinetControllerBean implements CabinetController {

    private final CabinetService cabinetService;

    @Override
    @PostMapping("/cabinets")
    @ResponseStatus(HttpStatus.CREATED)
    public CabinetResponse createCabinet(@RequestBody CabinetRequest cabinet) {
        return CabinetMapper.INSTANCE
                .toResponse(cabinetService
                        .addCabinet(CabinetMapper.INSTANCE.fromRequest(cabinet)));
    }

    @Override
    @GetMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CabinetResponse readCabinet(@PathVariable Integer id) {
        return CabinetMapper.INSTANCE.toResponse(cabinetService.getCabinet(id));
    }

    @Override
    @PutMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CabinetResponse updateCabinet(@PathVariable Integer id, @RequestBody CabinetRequest cabinet) {
        return CabinetMapper.INSTANCE
                .toResponse(cabinetService
                        .updateCabinet(id, CabinetMapper.INSTANCE.fromRequest(cabinet)));
    }

    @Override
    @DeleteMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCabinet(@PathVariable Integer id) {
        cabinetService.removeCabinet(id);
    }
}
