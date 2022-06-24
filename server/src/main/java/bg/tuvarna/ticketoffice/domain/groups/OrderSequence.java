package bg.tuvarna.ticketoffice.domain.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Extended.class})
public interface OrderSequence {
}
