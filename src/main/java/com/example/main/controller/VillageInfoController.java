package com.example.main.controller;

import com.example.main.entity.VillageInfo;
import com.example.main.service.VillageInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villages/info")
@CrossOrigin(origins = "http://localhost:3000")
public class VillageInfoController {
    private final VillageInfoService service;

    public VillageInfoController(VillageInfoService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<VillageInfo> createVillageInfo(@RequestBody  VillageInfo villageInfo) {
        VillageInfo villageInfo1 = service.createVillageInfo(villageInfo);
        return ResponseEntity.ok(villageInfo1);
    }
    @GetMapping("/all")
    public ResponseEntity<List<VillageInfo>> getAllVillageInfo(String username) {
        List<VillageInfo> allVillageInfo = service.getAllVillageInfo();
        return ResponseEntity.ok(allVillageInfo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VillageInfo> updateVillageInfo(@RequestBody VillageInfo villageInfo,@PathVariable long id) {
        VillageInfo villageInfo1 = service.updateVillageInfo(villageInfo, id);
        return ResponseEntity.ok(villageInfo1);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVillageInfo(@PathVariable long id) {
        service.deleteVillageInfo(id);
        return ResponseEntity.ok("Deleted id " + id);
    }
}
