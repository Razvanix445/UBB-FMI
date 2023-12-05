package com.example.lab7;

import com.example.lab7.console.UI;
import com.example.lab7.domain.Prietenie;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.FriendshipDBRepository;
import com.example.lab7.repository.Repository;
import com.example.lab7.repository.UserDBRepository;
import com.example.lab7.service.Service;

public class Main {
    public static void main(String[] args) {
        //Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        //Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();

        String url = "jdbc:postgresql://localhost:1234/socialnetwork";
        String username = "postgres";
        String password = "2003razvi";
        Repository<Long, Utilizator> utilizatorDBRepository = new UserDBRepository(url, username, password);
        Repository<Long, Prietenie> prietenieDBRepository = new FriendshipDBRepository(url, username, password);
        Service service = new Service(utilizatorDBRepository, prietenieDBRepository);
        UI.ui(service);
    }
}