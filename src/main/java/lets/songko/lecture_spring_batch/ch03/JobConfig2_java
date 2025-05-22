package lets.songko.lecture_spring_batch.ch03;

import lets.songko.lecture_spring_batch.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.support.JdbcTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableBatchProcessing
@Configuration
@Import(DataSourceConfig.class)
public class JobConfig2 {
    // TODO 4장 60, p150 예제부터 읽으면 됨.
    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        System.out.println("▶ Job bean 생성");

        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public CompletionPolicy completionPolicy() {
        CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
        policy.setPolicies(
                new CompletionPolicy[]{
                        new TimeoutTerminationPolicy(3),
                        new SimpleCompletionPolicy(1000)
                }
        );

        return policy;
    }

    @Bean
    public Step step(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        System.out.println("▶ Step bean 생성");

        return new StepBuilder("step", jobRepository)
                .<String, String>chunk(completionPolicy(), transactionManager)  // 커밋 간격 : 10
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }


    @StepScope
    @Bean
    public ListItemReader<String> itemReader() {
        List<String> items = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            items.add("[item] " + i);
        }

        return new ListItemReader<>(items);
    }

    @StepScope
    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            for (final String item : items) {
                System.out.println(item);
            }
        };
    }


}