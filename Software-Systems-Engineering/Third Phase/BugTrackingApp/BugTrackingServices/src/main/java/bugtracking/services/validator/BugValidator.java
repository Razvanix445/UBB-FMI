package bugtracking.services.validator;

import bugtracking.model.Bug;
import bugtracking.model.BugStatus;

import java.util.regex.Pattern;

public class BugValidator implements Validator<Bug> {

    @Override
    public void validate(Bug entity) throws ValidationException {
        if (!Pattern.matches("^[A-Za-z ]{2,50}$", entity.getName())) {
            throw new ValidationException("Bug's name is incorrect!");
        }
        if (!Pattern.matches("^[A-Za-z0-9 ]{2,50}$", entity.getDescription())) {
            throw new ValidationException("Bug's description is incorrect!");
        }
        if (entity.getReportedBy() == null) {
            throw new ValidationException("Bug's reported_by_id is incorrect!");
        }
        if (entity.getAssignedTo() == null) {
            throw new ValidationException("Bug's assigned_to_id is incorrect!");
        }
        if (entity.getStatus() != BugStatus.NEW && entity.getStatus() != BugStatus.IN_PROGRESS && entity.getStatus() != BugStatus.SOLVED) {
            throw new ValidationException("Bug's status is incorrect!");
        }
        if (entity.getTimestamp() == null) {
            throw new ValidationException("Bug's timestamp is incorrect!");
        }
    }
}
