package app.persistence;

import app.model.Configuration;
import app.persistence.repository.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface IConfigurationRepository extends Repository<Long, Configuration> {
}
