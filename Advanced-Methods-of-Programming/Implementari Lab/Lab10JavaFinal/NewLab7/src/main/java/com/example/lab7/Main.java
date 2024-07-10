package com.example.lab7;

import com.example.lab7.console.UI;
import com.example.lab7.domain.*;
import com.example.lab7.repository.*;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.Pageable;
import com.example.lab7.repository.paging.PageableImplementation;
import com.example.lab7.repository.paging.PagingRepository;
import com.example.lab7.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        //Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();

        String url = "jdbc:postgresql://localhost:1234/socialnetwork";
        String username = "postgres";
        String password = "2003razvi";

        PagingRepository<Long, Utilizator> userDBPagingRepository = new UserDBPagingRepository(url, username, password);

        //Repository<Long, Utilizator> utilizatorDBRepository = new UserDBRepository(url, username, password);
        Repository<Long, Prietenie> prietenieDBRepository = new FriendshipDBRepository(url, username, password);
        Repository<Long, Message> messageDBRepository = new MessageDBRepository(url, username, password);
        FriendRequestDBRepository friendRequestDBRepository = new FriendRequestDBRepository(url, username, password);
        Service service = new Service(userDBPagingRepository, prietenieDBRepository, messageDBRepository, friendRequestDBRepository);

        Utilizator loggedUser = service.cautaUtilizatorDupaUsername("svr");
        Utilizator otherUser = service.cautaUtilizatorDupaUsername("petre98");

//        service.addFriendRequest(otherUser);
//        displayRequests(service);
//        service.acceptFriendRequest(otherUser);
//        displayRequests(service);
//        service.declineFriendRequest(otherUser);
//        displayRequests(service);
//        service.deleteFriendRequest(otherUser);

//        List<Utilizator> otherUserList = new ArrayList<>();
//        otherUserList.add(otherUser);
//        List<Utilizator> loggedUserList = new ArrayList<>();
//        loggedUserList.add(loggedUser);

//        service.sendMessage(loggedUser, otherUserList, "Nice to meet you!");
//        service.sendMessage(otherUser, loggedUserList, "I am glad I can talk to you!");
//        service.sendMessage(loggedUser, otherUserList, "I like it too!");
//        service.sendMessage(otherUser, loggedUserList, "It is so funny!");
//        service.sendMessage(loggedUser, otherUserList, "Indeed!");
//
//        List<Message> messages = service.getMessagesBetweenUsers(loggedUser, otherUser);
//        System.out.println("Number of messages: " + messages.size());
//        for (Message message : messages) {
//            System.out.println("Message: " + message.getMessage());
//        }

        /* PAGINATION */
//        Pageable pageable = new PageableImplementation(2, 3);
//        Page<Utilizator> pagina = service.getAllUtilizatoriPaginat(pageable);
//        Page<Utilizator> nextPage = service.getNextPage();
//        Page<Utilizator> previousPage = service.getPreviousPage();

//        UI.ui(service);
    }

    private static void displayRequests(Service service) {
        System.out.println("Pending");
        List<Utilizator> usersPending = service.getAllUtilizatoriByStatus("pending");
        usersPending.forEach(System.out::println);
        System.out.println("Approved");
        List<Utilizator> usersApproved = service.getAllUtilizatoriByStatus("approved");
        usersApproved.forEach(System.out::println);
        System.out.println("Declined");
        List<Utilizator> usersDeclined = service.getAllUtilizatoriByStatus("declined");
        usersDeclined.forEach(System.out::println);
        System.out.println();
    }
}