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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Slf4j
@EnableBatchProcessing
@Configuration
@Import(DataSourceConfig.class)
public class JobConfig {
    // TODO 4장 53, p143
    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        System.out.println("▶ Job bean 생성");

        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        System.out.println("▶ Step bean 생성");

        return new StepBuilder("step", jobRepository)
                .<String, String>chunk(10, transactionManager)  // 커밋 간격 : 10
                .reader(itemReader(null))
                .writer(itemWriter(null))
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<String> itemReader(
            @Value("#{jobParameters['inputFile']}") String inputFile
    ) {
        return new FlatFileItemReaderBuilder<String>()
                .name("itemReader")
                .resource(new FileSystemResource(inputFile))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<String> itemWriter(
            @Value("#{jobParameters['outputFile']}") String outputFile
    ) {
        return new FlatFileItemWriterBuilder<String>()
                .name("itemWriter")
                .resource(new FileSystemResource(outputFile))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }



}