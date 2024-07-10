package com.example.service;

import com.example.domain.Bug;
import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.repository.interfaces.IBugRepository;
import com.example.repository.interfaces.IProgrammerRepository;
import com.example.repository.interfaces.ITesterRepository;

public class Service {

    private ITesterRepository testerRepository;
    private IProgrammerRepository programmerRepository;
    private IBugRepository bugRepository;

    public Service(ITesterRepository testerRepository, IProgrammerRepository programmerRepository, IBugRepository bugRepository) {
        this.testerRepository = testerRepository;
        this.programmerRepository = programmerRepository;
        this.bugRepository = bugRepository;
    }

    public Tester addTester(Tester tester) {
        return testerRepository.save(tester).orElseThrow(() -> new RuntimeException("Error adding tester"));
    }

    public Tester deleteTester(Long id) {
        return testerRepository.delete(id).orElseThrow(() -> new RuntimeException("Error deleting tester"));
    }

    public Tester updateTester(Tester tester) {
        return testerRepository.update(tester, tester.getId()).orElseThrow(() -> new RuntimeException("Error updating tester"));
    }

    public Iterable<Tester> getAllTesters() {
        return testerRepository.findAll();
    }

    public Tester searchTesterById(Long id) {
        return testerRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error getting tester"));
    }

    public Tester searchTesterByUsername(String username) {
        Iterable<Tester> testers = testerRepository.findAll();
        for (Tester tester: testers) {
            if (tester.getUsername().equals(username))
                return tester;
        }
        return null;
    }

    public Programmer addProgrammer(Programmer programmer) {
        return programmerRepository.save(programmer).orElseThrow(() -> new RuntimeException("Error adding programmer"));
    }

    public Programmer deleteProgrammer(Long id) {
        return programmerRepository.delete(id).orElseThrow(() -> new RuntimeException("Error deleting programmer"));
    }

    public Programmer updateProgrammer(Programmer programmer) {
        return programmerRepository.update(programmer, programmer.getId()).orElseThrow(() -> new RuntimeException("Error updating programmer"));
    }

    public Iterable<Programmer> getAllProgrammers() {
        return programmerRepository.findAll();
    }

    public Programmer searchProgrammerById(Long id) {
        return programmerRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error getting programmer"));
    }

    public Programmer searchProgrammerByUsername(String username) {
        Iterable<Programmer> programmers = programmerRepository.findAll();
        for (Programmer programmer: programmers) {
            if (programmer.getUsername().equals(username))
                return programmer;
        }
        return null;
    }

    public void addBug(String name, String description, Long programmerId, Long testerId) {
        Programmer programmer = programmerRepository.findOne(programmerId).orElseThrow(() -> new RuntimeException("Error getting programmer"));
        Tester tester = testerRepository.findOne(testerId).orElseThrow(() -> new RuntimeException("Error getting tester"));
        Bug bug = new Bug(name, description, tester, programmer);
        bugRepository.save(bug).orElseThrow(() -> new RuntimeException("Error adding bug"));
    }

    public Bug deleteBug(Long id) {
        return bugRepository.delete(id).orElseThrow(() -> new RuntimeException("Error deleting bug"));
    }

    public Bug updateBug(Bug bug) {
        return bugRepository.update(bug, bug.getId()).orElseThrow(() -> new RuntimeException("Error updating bug"));
    }

    public Iterable<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public Bug searchBugById(Long id) {
        return bugRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error getting bug"));
    }
}
