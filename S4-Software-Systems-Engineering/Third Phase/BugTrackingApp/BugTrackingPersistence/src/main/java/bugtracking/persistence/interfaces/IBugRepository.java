package bugtracking.persistence.interfaces;

import bugtracking.model.Bug;
import bugtracking.persistence.Repository;

public interface IBugRepository extends Repository<Long, Bug> {
}
