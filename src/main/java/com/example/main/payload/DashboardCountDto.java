package com.example.main.payload;

public class DashboardCountDto {

    private long totalUsers;
    private long totalProblems;
    private long pendingProblems;
    private long resolvedProblems;
    private long villageProjectCount;

    public DashboardCountDto(long totalUsers,
                             long totalProblems,
                             long pendingProblems,
                             long resolvedProblems, long villageProjectCount) {
        this.totalUsers = totalUsers;
        this.totalProblems = totalProblems;
        this.pendingProblems = pendingProblems;
        this.resolvedProblems = resolvedProblems;
        this.villageProjectCount = villageProjectCount;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalProblems() {
        return totalProblems;
    }

    public long getPendingProblems() {
        return pendingProblems;
    }

    public long getResolvedProblems() {
        return resolvedProblems;
    }

    public long getVillageProjectCount() {
        return villageProjectCount;
    }

    public void setVillageProjectCount(long villageProjectCount) {
        this.villageProjectCount = villageProjectCount;
    }
}