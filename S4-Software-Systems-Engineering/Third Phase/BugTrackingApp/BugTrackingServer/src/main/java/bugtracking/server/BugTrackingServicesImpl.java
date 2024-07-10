package bugtracking.server;

import bugtracking.model.*;
import bugtracking.persistence.interfaces.IBugRepository;
import bugtracking.persistence.interfaces.IProgrammerRepository;
import bugtracking.persistence.interfaces.ITesterRepository;
import bugtracking.services.BugTrackingException;
import bugtracking.services.IBugTrackingObserver;
import bugtracking.services.IBugTrackingServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BugTrackingServicesImpl implements IBugTrackingServices {

    private ITesterRepository testerRepository;
    private IProgrammerRepository programmerRepository;
    private IBugRepository bugRepository;

    private Map<String, IBugTrackingObserver> loggedClients;

    public BugTrackingServicesImpl(ITesterRepository testerRepository, IProgrammerRepository programmerRepository, IBugRepository bugRepository) {
        this.testerRepository = testerRepository;
        this.programmerRepository = programmerRepository;
        this.bugRepository = bugRepository;
        loggedClients = new ConcurrentHashMap<>();
    }

    public synchronized Tester loginTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException {
        Tester foundTester = searchTesterByUsername(tester.getUsername());
        if (foundTester != null) {
            if (loggedClients.get(tester.getUsername()) != null) {
                throw new RuntimeException("User already logged in.");
            }
            if (tester.getPassword().equals(foundTester.getPassword())) {
                loggedClients.put(tester.getUsername(), client);
            } else {
                throw new RuntimeException("Authentication failed.");
            }
        } else {
            throw new RuntimeException("Authentication failed.");
        }
        return tester;
    }

    public synchronized Programmer loginProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException {
        Programmer foundProgrammer = searchProgrammerByUsername(programmer.getUsername());
        if (foundProgrammer != null) {
            if (loggedClients.get(programmer.getUsername()) != null) {
                throw new RuntimeException("User already logged in.");
            }
            if (programmer.getPassword().equals(foundProgrammer.getPassword())) {
                loggedClients.put(programmer.getUsername(), client);
            } else {
                throw new RuntimeException("Authentication failed.");
            }
        } else {
            throw new RuntimeException("Authentication failed.");
        }
        return programmer;
    }

    public synchronized Tester addTester(Tester tester) throws BugTrackingException {
        return testerRepository.save(tester).orElseThrow(() -> new BugTrackingException("Tester could not be added."));
    }

    public synchronized Tester deleteTester(Tester tester) throws BugTrackingException {
        return testerRepository.delete(tester.getId()).orElseThrow(() -> new BugTrackingException("Tester could not be deleted."));
    }

    public synchronized Tester updateTester(Tester tester) throws BugTrackingException {
        return testerRepository.update(tester, tester.getId()).orElseThrow(() -> new BugTrackingException("Tester could not be updated."));
    }

    public synchronized Programmer addProgrammer(Programmer programmer) throws BugTrackingException {
        return programmerRepository.save(programmer).orElseThrow(() -> new BugTrackingException("Programmer could not be added."));
    }

    public synchronized Programmer deleteProgrammer(Programmer programmer) throws BugTrackingException {
        return programmerRepository.delete(programmer.getId()).orElseThrow(() -> new BugTrackingException("Programmer could not be deleted."));
    }

    public synchronized Programmer updateProgrammer(Programmer programmer) throws BugTrackingException {
        return programmerRepository.update(programmer, programmer.getId()).orElseThrow(() -> new BugTrackingException("Programmer could not be updated."));
    }

    public synchronized List<Programmer> getAllProgrammers() throws BugTrackingException {
        Iterable<Programmer> programmers = programmerRepository.findAll();
        List<Programmer> result = new ArrayList<>();
        programmers.forEach(result::add);
        return result;
    }

    public synchronized Bug addBug(Bug bug) throws BugTrackingException {
        Bug savedBug = bugRepository.save(bug).orElseThrow(() -> new BugTrackingException("Bug could not be added."));
        for (IBugTrackingObserver client: loggedClients.values()) {
            try {
                client.bugAdded(bug);
            } catch (BugTrackingException e) {
                System.err.println("Error notifying client in TransportServicesImpl: " + e);
            }
        }
        return savedBug;
    }

    public synchronized Bug deleteBug(Bug bug) throws BugTrackingException {
        Bug deletedBug = bugRepository.delete(bug.getId()).orElseThrow(() -> new BugTrackingException("Bug could not be deleted."));
        for (IBugTrackingObserver client: loggedClients.values()) {
            try {
                client.bugRemoved(bug);
            } catch (BugTrackingException e) {
                System.err.println("Error notifying client in TransportServicesImpl: " + e);
            }
        }
        return deletedBug;
    }

    public synchronized Bug updateBug(Bug bug) throws BugTrackingException {
        Bug updatedBug = bugRepository.update(bug, bug.getId()).orElseThrow(() -> new BugTrackingException("Bug could not be updated."));
        for (IBugTrackingObserver client: loggedClients.values()) {
            try {
                client.bugRemoved(bug);
            } catch (BugTrackingException e) {
                System.err.println("Error notifying client in TransportServicesImpl: " + e);
            }
        }
        return updatedBug;
    }

    public synchronized Tester searchTesterByUsername(String username) throws BugTrackingException {
        Iterable<Tester> testers = testerRepository.findAll();
        for (Tester tester: testers) {
            if (tester.getUsername().equals(username))
                return tester;
        }
        return null;
    }

    public synchronized Programmer searchProgrammerByUsername(String username) throws BugTrackingException {
        Iterable<Programmer> programmers = programmerRepository.findAll();
        for (Programmer programmer: programmers) {
            if (programmer.getUsername().equals(username))
                return programmer;
        }
        return null;
    }

    public synchronized Bug searchBugByNameAndDescription(String name, String description) throws BugTrackingException {
        Iterable<Bug> bugs = bugRepository.findAll();
        for (Bug bug: bugs) {
            if (bug.getName().equals(name) && bug.getDescription().equals(description))
                return bug;
        }
        return null;
    }

    public synchronized List<Bug> getBugsByProgrammer(Programmer programmer) throws BugTrackingException {
        Iterable<Bug> bugs = bugRepository.findAll();
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugs) {
            if (bug.getAssignedTo().getId().equals(programmer.getId())) {
                result.add(bug);
            }
        }
        return result;
    }

    public synchronized List<Bug> getAllBugs() throws BugTrackingException {
        Iterable<Bug> bugs = bugRepository.findAll();
        List<Bug> result = new ArrayList<>();
        bugs.forEach(result::add);
        return result;
    }

    public synchronized List<UserWithTypeDTO> getAllUsers() throws BugTrackingException {
        List<UserWithTypeDTO> users = new ArrayList<>();
        Iterable<Tester> testers = testerRepository.findAll();
        for (Tester tester : testers) {
            UserWithTypeDTO userDTO = new UserWithTypeDTO(tester.getName(), tester.getUsername(), tester.getPassword(), tester.getEmail(), "Tester");
            users.add(userDTO);
        }
        Iterable<Programmer> programmers = programmerRepository.findAll();
        for (Programmer programmer : programmers) {
            UserWithTypeDTO userDTO = new UserWithTypeDTO(programmer.getName(), programmer.getUsername(), programmer.getPassword(), programmer.getEmail(), "Programmer");
            users.add(userDTO);
        }
        return users;
    }

    public synchronized Tester logoutTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException {
        if (loggedClients.get(tester.getUsername()) != null) {
            loggedClients.remove(tester.getUsername());
        } else {
            throw new RuntimeException("User not logged in.");
        }
        return tester;
    }

    public synchronized Programmer logoutProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException {
        if (loggedClients.get(programmer.getUsername()) != null) {
            loggedClients.remove(programmer.getUsername());
        } else {
            throw new RuntimeException("User not logged in.");
        }
        return programmer;
    }
}
