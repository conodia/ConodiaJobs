package fr.pandaguerrier.newjob.managers;

import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.jobs.*;

import java.util.ArrayList;

public class JobsManager {
    public ArrayList<JobsAbstract> jobs = new ArrayList<>();

    public JobsManager() {
        jobs.add(new Hunter());
        jobs.add(new Miner());
        jobs.add(new Constructor());
        jobs.add(new Farmer());
        jobs.add(new Lumberjack());
    }

    public ArrayList<JobsAbstract> getJobs() {
        return jobs;
    }
}
