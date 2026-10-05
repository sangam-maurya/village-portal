package com.example.main.service;

import com.example.main.payload.DashboardCountDto;
import com.example.main.reposetry.VillageProblemRepository;
import com.example.main.reposetry.VillageProjectRepository;
import com.example.main.reposetry.VillageUserSignupRepository;
import com.example.main.service.Interface.VillageUserSignupService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl {

 private final VillageProjectServiceImpl villageUserSignupService;
 private final VillageProjectServiceImpl villageProjectService;
 private final VillageProblemServiceImpl villageProblemService;

    public DashboardServiceImpl(VillageProjectServiceImpl villageUserSignupService, VillageProjectServiceImpl villageProjectService, VillageProblemServiceImpl villageProblemService) {
        this.villageUserSignupService = villageUserSignupService;
        this.villageProjectService = villageProjectService;
        this.villageProblemService = villageProblemService;
    }


    public DashboardCountDto getDashboardCount() {
        long projectCount = villageProjectService.getProjectCount();
        long usercount = villageUserSignupService.getProjectCount();
        long totalProblems = villageProblemService.getTotalProblems();
        long pending = villageProblemService.getPendingProblems("PENDING");
        long resolved = villageProblemService.getResolvedProblems("RESOLVED");

        return new DashboardCountDto(
                projectCount,
                usercount,
                totalProblems,
                pending,
                resolved
        );

    }
}