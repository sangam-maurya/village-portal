package com.example.main.service;

import com.example.main.payload.DashboardCountDto;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl {

 private final VillageProjectServiceImpl villageProjectService;
 private final VillageUserSignupImpl villageUserSignup;
 private final VillageProblemServiceImpl villageProblemService;

    public DashboardServiceImpl(VillageProjectServiceImpl villageProjectService, VillageUserSignupImpl villageUserSignup, VillageProblemServiceImpl villageProblemService) {
        this.villageProjectService = villageProjectService;
        this.villageUserSignup = villageUserSignup;
        this.villageProblemService = villageProblemService;
    }


    public DashboardCountDto getDashboardCount() {
        long userCount = villageUserSignup.getCount();
        long projectCount = villageProjectService.getProjectCount();
        long totalProblems = villageProblemService.getTotalProblems();
        long pending = villageProblemService.getPendingProblems("PENDING");
        long resolved = villageProblemService.getResolvedProblems("RESOLVED");

        return new DashboardCountDto(
                userCount,
                totalProblems,
                pending,
                resolved,
                projectCount
        );
    }
}