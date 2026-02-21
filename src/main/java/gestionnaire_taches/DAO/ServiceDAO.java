package DAO;

import model.Service;
import java.util.List;

public interface ServiceDAO{
    void create(Service service);

    Service finfById(int id);
    List<Service> findAll();

    void update(Service service);
    void delete(int id);
}