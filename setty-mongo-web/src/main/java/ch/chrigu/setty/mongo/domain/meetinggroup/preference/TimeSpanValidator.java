package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public class TimeSpanValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        log.info("CALLED!!!");
        return TimeSpan.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        if (o != null && o instanceof TimeSpan) {
            TimeSpan timeSpan = (TimeSpan) o;
            if (timeSpan.getDays() == 0 && timeSpan.getFrom().isAfter(timeSpan.getTo())) {
                errors.rejectValue("from", "from.after.to");
                errors.rejectValue("to", "from.after.to");
            }
        }
    }
}
