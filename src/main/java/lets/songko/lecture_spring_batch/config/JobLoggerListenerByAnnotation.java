package lets.songko.lecture_spring_batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class JobLoggerListenerByAnnotation {
    private static String START_MESSAGE = "▶ %s is beginning execution";
    private static String END_MESSAGE = "▶ %s has completed with the status %s";

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(String.format(START_MESSAGE, jobExecution.getJobInstance().getJobName()));
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        System.out.println(String.format(END_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
    }
}
