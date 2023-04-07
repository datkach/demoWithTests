package com.example.demowithtests.web;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetRequest;
import com.example.demowithtests.dto.CabinetResponse;

public interface CabinetController {

    CabinetResponse createCabinet(CabinetRequest cabinet);

    CabinetResponse readCabinet(Integer id);

    CabinetResponse updateCabinet(Integer id, CabinetRequest cabinet);

    void deleteCabinet(Integer id);
}
