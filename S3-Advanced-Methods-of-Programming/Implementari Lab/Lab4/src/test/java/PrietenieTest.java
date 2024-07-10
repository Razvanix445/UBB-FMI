import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.exceptions.IDuriIdenticeException;
import ir.map.g222.exceptions.PrietenieExistentaException;
import ir.map.g222.exceptions.PrietenieInexistentaException;
import ir.map.g222.exceptions.UtilizatorInexistentException;
import ir.map.g222.repository.InMemoryRepository;
import ir.map.g222.repository.Repository;
import ir.map.g222.service.Service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrietenieTest {

    @Test
    public void testServicePrietenie() {
        Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();
        Service service = new Service(utilizatorRepository, prietenieRepository);

        service.adaugaUtilizator("John", "Doe");
        service.adaugaUtilizator("Alice", "Smith");

        service.adaugaPrietenie(1L, 2L);

        assertThrows(PrietenieExistentaException.class, () -> {
           service.adaugaPrietenie(1L, 2L);
        });
        assertThrows(IDuriIdenticeException.class, () -> {
            service.adaugaPrietenie(1L, 1L);
        });

        Utilizator johnDoe = service.cautaUtilizator(1L);
        Utilizator aliceSmith = service.cautaUtilizator(2L);

        assertTrue(johnDoe.getPrieteni().contains(aliceSmith));
        assertTrue(aliceSmith.getPrieteni().contains(johnDoe));

        service.stergePrietenie(1L, 2L);
        assertThrows(PrietenieInexistentaException.class, () -> {
            service.stergePrietenie(1L, 2L);
        });

        assertFalse(johnDoe.getPrieteni().contains(aliceSmith));
        assertFalse(aliceSmith.getPrieteni().contains(johnDoe));
    }
}
