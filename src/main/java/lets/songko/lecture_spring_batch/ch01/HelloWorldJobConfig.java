package lets.songko.lecture_spring_batch.ch01;

import lets.songko.lecture_spring_batch.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Slf4j
@EnableBatchProcessing
@Configuration
@Import(DataSourceConfig.class)
public class HelloWorldJobConfig {
    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        System.out.println("▶ Job bean 생성");

        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        System.out.println("▶ Step bean 생성");

        return new StepBuilder("step", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("▶ Hello, World!");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();

    }
}
