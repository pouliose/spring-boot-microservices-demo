package com.runner.runs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class RunsApplication {
	private static final Logger LOG = LoggerFactory.getLogger(RunsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RunsApplication.class, args);
	}

	@KafkaListener(topics = "run.upload")
	@Profile("!test")
	public String listens(final String in) {
		LOG.debug("In payload {}", in);
		return in;
	}

}
