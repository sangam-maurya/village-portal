package com.example.main.service;

import com.example.main.entity.VillageInfo;
import com.example.main.reposetry.VillageInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VillageInfoServiceImpl implements VillageInfoService{
    private final VillageInfoRepository repository;

    public VillageInfoServiceImpl(VillageInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public VillageInfo createVillageInfo(VillageInfo villageInfo) {
        VillageInfo save = repository.save(villageInfo);
        return save;
    }

    @Override
    public List<VillageInfo> getAllVillageInfo() {
        List<VillageInfo> all = repository.findAll();
        return all;
    }

    @Override
    public VillageInfo updateVillageInfo(VillageInfo villageInfo, long id) {
        VillageInfo villageInfoNotFound = repository.findById(id).orElseThrow(() -> new RuntimeException("Village Info not found"));
        villageInfoNotFound.setVillageName(villageInfo.getVillageName());
        villageInfoNotFound.setDistrict(villageInfo.getDistrict());
        villageInfoNotFound.setState(villageInfo.getState());
        villageInfoNotFound.setPopulation(villageInfo.getPopulation());
        villageInfoNotFound.setServicesAvailable(villageInfo.getServicesAvailable());
        VillageInfo save = repository.save(villageInfoNotFound);
        return save;

    }

    @Override
    public void deleteVillageInfo(long id) {
        repository.deleteById(id);
    }
}
