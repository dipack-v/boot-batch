package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
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

	//@Scheduled(cron = "*/60 * * * * *")
	public void runJob() throws Exception {
		System.out.println("Job going to start now " + dateFormat.format(new Date()));
		JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
		jobParameterBuilder.addString("CamelFileName", "sample-data.csv");
		jobLauncher.run(job, jobParameterBuilder.toJobParameters());
	}
}

@Component
class FilePollerRoute extends RouteBuilder {
	public static final String ROUTE_NAME = "TIMER_ROUTE";

	@Override
	public void configure() throws Exception {
		from("file:src/main/resources?include=.*.csv&noop=true").to("spring-batch:importUserJob");
	}
}