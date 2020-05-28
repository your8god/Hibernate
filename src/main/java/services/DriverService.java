package services;

import dao.DriverDAOImpl;
import models.Driver;
import models.Auto;

import java.util.List;

public class DriverService {

    private DriverDAOImpl driverDao = new DriverDAOImpl();

    public Driver findDriverById(int id) {
        return driverDao.findById(id);
    }

    public void saveDriver(Driver driver) {
        driverDao.save(driver);
    }

    public void deleteDriver(Driver driver) {
        driverDao.delete(driver);
    }

    public void updateDriver(Driver driver) {
        driverDao.update(driver);
    }

    public List<Driver> findAllDriver() {
        return driverDao.findAll();
    }

    public List<Auto> findAllAuto() {return driverDao.findAllAuto();}

    public List<Driver> findDriverByName(String name) {
        return driverDao.findByName(name);
    }

    public Auto findAutoById(int id) {
        return driverDao.findAutoById(id);
    }

    public void closeSessionAfterFind() {
        driverDao.closeSessionAfterFind();
    }
}
