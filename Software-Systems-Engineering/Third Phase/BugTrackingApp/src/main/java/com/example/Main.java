package com.example;

import com.example.domain.Bug;
import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.repository.database.BugDBRepository;
import com.example.repository.database.ProgrammerDBRepository;
import com.example.repository.database.TesterDBRepository;
import com.example.repository.interfaces.IBugRepository;
import com.example.repository.interfaces.IProgrammerRepository;
import com.example.repository.interfaces.ITesterRepository;
import com.example.service.Service;

public class Main {

    private static Service service;
    private static ITesterRepository testerRepository;
    private static IProgrammerRepository programmerRepository;
    private static IBugRepository bugRepository;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        String url = "jdbc:postgresql://localhost:1234/BugTracking";
        String username = "postgres";
        String password = "2003razvi";

        testerRepository = new TesterDBRepository(url, username, password);
        programmerRepository = new ProgrammerDBRepository(url, username, password);
        bugRepository = new BugDBRepository(url, username, password);
        service = new Service(testerRepository, programmerRepository, bugRepository);

//        System.out.println("\nSearching tester...");
//        Tester foundTester = service.searchTesterById(1L);
//        System.out.println(foundTester);
//
//        System.out.println("\nSearching programmer...");
//        Programmer foundProgrammer = service.searchProgrammerById(1L);
//        System.out.println(foundProgrammer);
//
//        System.out.println("\nSearching bug...");
//        Bug foundBug = service.searchBugById(1L);
//        System.out.println(foundBug);
//
//        System.out.println("\nAdding bug...");
//        service.addBug("newBug", "description", foundTester.getId(), foundProgrammer.getId());
//        System.out.println("Bug added");
//
//        System.out.println("\nFinding all bugs...");
//        Iterable<Bug> bugs = service.getAllBugs();
//        bugs.forEach(System.out::println);

    }
}
