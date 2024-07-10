package bugtracking.services;

import bugtracking.model.Bug;

public interface IBugTrackingObserver {
    void bugAdded(Bug bug) throws BugTrackingException;
    void bugRemoved(Bug bug) throws BugTrackingException;
    void bugUpdated(Bug bug) throws BugTrackingException;
}
