package lets.songko.lecture_spring_batch.ch03;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobConfigTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private JobExplorer jobExplorer;

    @Test
    public void test_tasklet() throws Exception {
        JobParametersBuilder builder = new JobParametersBuilder(jobExplorer);
        builder.addString("inputFile", new ClassPathResource("files/input.txt").getFile().getAbsolutePath());
        builder.addString("outputFile", new ClassPathResource("files/output.txt").getFile().getAbsolutePath());

        JobExecution jobExecution = jobLauncher.run(job,
                builder.toJobParameters());

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}
