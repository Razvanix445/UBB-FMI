import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.repository.InMemoryRepository;
import ir.map.g222.repository.Repository;
import ir.map.g222.service.Service;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComunitatiTest {

    @Test
    public void testServiceComunitati() {
        Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();
        Service service = new Service(utilizatorRepository, prietenieRepository);

        service.adaugaUtilizator("John", "Doe");
        service.adaugaUtilizator("Alice", "Smith");
        service.adaugaUtilizator("Bob", "Johnson");
        service.adaugaUtilizator("Eve", "Brown");
        service.adaugaUtilizator("Marie", "Patton");

        assertEquals(5, service.getNumarComunitati());

        service.adaugaPrietenie(1L, 2L);
        service.adaugaPrietenie(3L, 4L);

        assertEquals(3, service.getNumarComunitati());

        List<List<Utilizator>> comunitati = service.getAllComunitati();
        assertEquals(3, comunitati.size());
        assertTrue(comunitati.get(0).contains(service.cautaUtilizator(1L)));
        assertTrue(comunitati.get(0).contains(service.cautaUtilizator(2L)));
        assertTrue(comunitati.get(1).contains(service.cautaUtilizator(3L)));
        assertTrue(comunitati.get(1).contains(service.cautaUtilizator(4L)));

        service.adaugaPrietenie(1L, 5L);
        List<Utilizator> ceaMaiSociabilaComunitate = service.ceaMaiSociabilaComunitate();
        assertEquals(3, ceaMaiSociabilaComunitate.size());
        assertTrue(ceaMaiSociabilaComunitate.contains(service.cautaUtilizator(1L)));
        assertTrue(ceaMaiSociabilaComunitate.contains(service.cautaUtilizator(2L)));
        assertTrue(ceaMaiSociabilaComunitate.contains(service.cautaUtilizator(5L)));
    }
}
