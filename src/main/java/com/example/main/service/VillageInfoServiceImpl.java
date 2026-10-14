
package com.example.main.service;

import com.example.main.Excepction.ResourceNotFound;
import com.example.main.entity.VillageInfo;
import com.example.main.reposetry.VillageInfoRepository;
import com.example.main.service.Interface.VillageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageInfoServiceImpl implements VillageInfoService {

    private static final Logger log =
            LoggerFactory.getLogger(VillageInfoServiceImpl.class);

    private final VillageInfoRepository repository;

    public VillageInfoServiceImpl(VillageInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public VillageInfo createVillageInfo(VillageInfo villageInfo) {
        VillageInfo saved = repository.save(villageInfo);

        log.info("Village info created successfully with ID {}",
                saved.getId());

        return saved;
    }

    @Override
    public List<VillageInfo> getAllVillageInfo() {
        List<VillageInfo> villageInfos = repository.findAll();

        log.info("Fetched {} village info records", villageInfos.size());

        return villageInfos;
    }

    @Override
    public VillageInfo updateVillageInfo(VillageInfo villageInfo, long id) {
        VillageInfo existingInfo = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Village info not found with ID " + id));

        existingInfo.setVillageName(villageInfo.getVillageName());
        existingInfo.setDistrict(villageInfo.getDistrict());
        existingInfo.setState(villageInfo.getState());
        existingInfo.setPopulation(villageInfo.getPopulation());
        existingInfo.setServicesAvailable(villageInfo.getServicesAvailable());

        VillageInfo saved = repository.save(existingInfo);

        log.info("Village info updated successfully with ID {}",
                saved.getId());

        return saved;
    }

    @Override
    public void deleteVillageInfo(long id) {
        VillageInfo villageInfo = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Village info not found with ID " + id));

        repository.delete(villageInfo);

        log.info("Village info deleted successfully with ID {}", id);
    }
}
