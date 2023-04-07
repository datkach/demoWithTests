package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetRequest;
import com.example.demowithtests.dto.CabinetResponse;

public interface CabinetService {

    Cabinet addCabinet(Cabinet cabinet);

    Cabinet getCabinet(Integer id);

    Cabinet updateCabinet(Integer id, Cabinet cabinet);

    void removeCabinet(Integer id);
}