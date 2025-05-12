package lets.songko.lecture_spring_batch.ch01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HelloWorldJobConfigTest {
    @Test
    public void testLaunchJob() throws Exception {
        // given
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldJobConfig.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        // when
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        // then
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}
