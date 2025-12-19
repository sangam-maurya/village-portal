package com.example.main.service.Interface;

import com.example.main.entity.VillageInfo;

import java.util.List;

public interface VillageInfoService {

    VillageInfo createVillageInfo(VillageInfo villageInfo);
    List<VillageInfo> getAllVillageInfo();
    VillageInfo updateVillageInfo(VillageInfo villageInfo, long id);
    void deleteVillageInfo(long id);
}
