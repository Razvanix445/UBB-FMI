package ir.map.g222.sem7demo;

import ir.map.g222.sem7demo.domain.Prietenie;
import ir.map.g222.sem7demo.domain.Utilizator;
import ir.map.g222.sem7demo.repository.FriendshipDBRepository;
import ir.map.g222.sem7demo.repository.Repository;
import ir.map.g222.sem7demo.repository.UserDBRepository;
import ir.map.g222.sem7demo.console.UI;
import ir.map.g222.sem7demo.service.Service;

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