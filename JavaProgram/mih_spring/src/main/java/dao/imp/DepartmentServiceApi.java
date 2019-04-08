package dao.imp;

import dao.HibernateFactory;
import dao.service.DepartmentService;
import model.Departments;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("departmentService")
public class DepartmentServiceApi implements DepartmentService {

    Session session = HibernateFactory.getSessionFactory().openSession();

    @Override
    public List<Departments> getDepartemnts() {

        String language = "select * from departments;";
        Query query = session.createSQLQuery(language);
        ((NativeQuery) query).addEntity(Departments.class);
        List<Departments> departments = query.list();
        return departments;
    }
}
