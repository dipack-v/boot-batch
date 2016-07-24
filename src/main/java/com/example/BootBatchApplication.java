package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BootBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootBatchApplication.class, args);
	}
}

@Component
@EnableScheduling
class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    Job job;

    @Scheduled(cron = "*/60 * * * * *")
    public void runJob() throws Exception {
        System.out.println("Job going to start now " + dateFormat.format(new Date()));
        jobLauncher.run(job, new JobParameters());
    }
}