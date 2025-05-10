package org.server.orm.repositories;

import org.server.orm.classes.StationDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationDAO, String> {
    public StationDAO findByName(String name);
    public StationDAO findByNumber(int number);
}
