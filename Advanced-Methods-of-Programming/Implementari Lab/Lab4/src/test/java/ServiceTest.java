import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.exceptions.EntitateNull;
import ir.map.g222.repository.InMemoryRepository;
import ir.map.g222.exceptions.UtilizatorInexistentException;
import ir.map.g222.repository.Repository;
import ir.map.g222.service.Service;
import ir.map.g222.validators.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    @Test
    public void testServiceUtilizator() {
        Repository<Long, Utilizator> utilizatorRepository = new InMemoryRepository<>();
        Repository<Long, Prietenie> prietenieRepository = new InMemoryRepository<>();
        Service service = new Service(utilizatorRepository, prietenieRepository);

        service.adaugaUtilizator("John", "Doe");

        assertThrows(ValidationException.class, () -> {
            service.adaugaUtilizator("R45ter", "Raul");
        });
        assertThrows(EntitateNull.class, () -> {
            utilizatorRepository.save(null);
        });

        assertEquals("John", service.cautaUtilizator(1L).getPrenume());
        assertEquals("Doe", service.cautaUtilizator(1L).getNume());


        service.modificaUtilizator("Jane", "Smith", 1L);
        Utilizator utilizatorModificat = service.cautaUtilizator(1L);

        assertEquals("Jane", utilizatorModificat.getPrenume());
        assertEquals("Smith", utilizatorModificat.getNume());


        service.stergeUtilizator(1L);
        assertThrows(UtilizatorInexistentException.class, () -> {
            service.cautaUtilizator(1L);
        });
    }
}