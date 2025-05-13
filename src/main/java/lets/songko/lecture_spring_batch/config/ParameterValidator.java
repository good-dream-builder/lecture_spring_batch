package lets.songko.lecture_spring_batch.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class ParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");
        if (!StringUtils.hasText(fileName)) {
            throw new JobParametersInvalidException("Parameter 'fileName' is required");
        } else if (!StringUtils.endsWithIgnoreCase(fileName, ".csv")) {
            throw new JobParametersInvalidException("Parameter 'fileName' is not a .csv file");
        }
    }
}
