package ir.map.g222;

import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.repository.InMemoryRepository;
import ir.map.g222.repository.Repository;
import ir.map.g222.service.Service;

import static ir.map.g222.console.UI.ui;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();
        Service service = new Service(utilizatorRepository, prietenieRepository);
        ui(service);
    }
}