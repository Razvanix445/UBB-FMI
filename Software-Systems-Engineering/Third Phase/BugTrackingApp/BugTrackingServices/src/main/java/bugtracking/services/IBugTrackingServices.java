package bugtracking.services;

import bugtracking.model.*;

import java.util.List;

public interface IBugTrackingServices {
    Tester loginTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException;

    Programmer loginProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException;

    Tester addTester(Tester tester) throws BugTrackingException;

    Tester deleteTester(Tester tester) throws BugTrackingException;

    Tester updateTester(Tester tester) throws BugTrackingException;

    Programmer addProgrammer(Programmer programmer) throws BugTrackingException;

    Programmer deleteProgrammer(Programmer programmer) throws BugTrackingException;

    Programmer updateProgrammer(Programmer programmer) throws BugTrackingException;

    List<Programmer> getAllProgrammers() throws BugTrackingException;

    Bug addBug(Bug bug) throws BugTrackingException;

    Bug deleteBug(Bug bug) throws BugTrackingException;

    Bug updateBug(Bug bug) throws BugTrackingException;

    Tester searchTesterByUsername(String name) throws BugTrackingException;

    Programmer searchProgrammerByUsername(String name) throws BugTrackingException;

    Bug searchBugByNameAndDescription(String name, String description) throws BugTrackingException;

    List<Bug> getBugsByProgrammer(Programmer programmer) throws BugTrackingException;

    List<Bug> getAllBugs() throws BugTrackingException;

    List<UserWithTypeDTO> getAllUsers() throws BugTrackingException;

    Tester logoutTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException;

    Programmer logoutProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException;
}
