package dao;

import models.Driver;
import models.Auto;
import java.util.List;

public interface DriverDAO {

    Driver findById(int id);

    void save(Driver driver);

    void update(Driver driver);

    void delete(Driver driver);

    Auto findAutoById(int id);

    List<Driver> findAll();

    List<Auto> findAllAuto();

    List<Driver> findByName(String name);
}
