package app.persistence;

import app.model.*;
import app.persistence.Repository;

import java.util.Optional;

public interface IConfigurationWordRepository extends Repository<Long, ConfigurationWord> {

    Iterable<Word> findWordsByConfiguration(Configuration configuration);
    Iterable<ConfigurationWord> findConfigurationWordsByConfiguration(Configuration configuration);
}
