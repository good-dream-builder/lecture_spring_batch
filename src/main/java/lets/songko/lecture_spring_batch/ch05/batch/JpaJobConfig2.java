package lets.songko.lecture_spring_batch.ch05.batch;

import jakarta.persistence.EntityManagerFactory;
import lets.songko.lecture_spring_batch.ch05.domain.Customer;
import lets.songko.lecture_spring_batch.ch05.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class JpaJobConfig2 {
    private final JobRepository jobRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;
    private final CustomerRepository customerRepository;

    // TODO: READ COUNT: 4, 8 이렇게 두번 읽더라. 왜 일까?
    // TODO: QueryDSL도 적용되는지 확인해봐야함.
    // TODO: 여러개 존재할 때 어떻게 구성하는게 깔끔한지 확인해야함.

    @Bean
    public Job job() {
        System.out.println("▶ Job");
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        System.out.println("▶ Step");

        return new StepBuilder("step", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)  // 커밋 간격 : 10
                .reader(itemReader(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Customer> itemReader(@Value("#{jobParameters['city']}") String city) {
        return new RepositoryItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .arguments(Collections.singletonList(city))
                .methodName("findByCity")
                .repository(customerRepository)
                .sorts(Collections.singletonMap("lastName", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Customer> itemWriter() {
        System.out.println("▶ itemWriter");
        return items -> {
            for (final Customer item : items) {
                System.out.println(item);
            }
        };
    }
}
