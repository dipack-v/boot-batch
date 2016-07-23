package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/*@Value("${send.from.email}")
    private String fromEmail;

    @Value("${send.to.email}")
    private String toEmail;

	@Autowired
	private MailSender mailSender;

	private SimpleMailMessage templateMessage;*/

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", new RowMapper<Person>() {
				@Override
				public Person mapRow(ResultSet rs, int row) throws SQLException {
					return new Person(rs.getString(1), rs.getString(2));
				}
			});

			for (Person person : results) {
				log.info("Found <" + person + "> in the database.");
			}
			
			
			/*this.templateMessage = new SimpleMailMessage();

	        this.templateMessage.setSubject("Dinner Party");
	        this.templateMessage.setFrom(this.fromEmail);
	        this.templateMessage.setTo(this.toEmail);

	        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	        msg.setText("Please come for dinner tonight.");

	        try{
	            this.mailSender.send(msg);
	        }
	        catch(MailException ex){
	      
	            System.err.println(ex.getMessage());
	        }
	        System.out.println("Finished Send...");*/
	      
		}
	}
}
