package lets.songko.lecture_spring_batch.ch03;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class LoggingStepStartListener implements StepExecutionListener {
    private static String START_MESSAGE = "▶ %s is beginning execution";
    private static String END_MESSAGE = "▶ %s has completed with the status %s";

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(String.format(START_MESSAGE, stepExecution.getStepName()));
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(String.format(END_MESSAGE, stepExecution.getStepName(), stepExecution.getStatus()));

        return stepExecution.getExitStatus();
    }
}
