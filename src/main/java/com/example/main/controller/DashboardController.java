package com.example.main.controller;

import com.example.main.payload.DashboardCountDto;
import com.example.main.service.DashboardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardServiceImpl dashboardService;

    public DashboardController(DashboardServiceImpl dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/count")
    public ResponseEntity<DashboardCountDto> getDashboardCount() {

        DashboardCountDto dashboardCount =
                dashboardService.getDashboardCount();

        return ResponseEntity.ok(dashboardCount);
    }
}