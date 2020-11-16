package Mih.demo.Dao;

import Mih.demo.Dao.Services.AdminService;
import Mih.demo.Mappers.AdminMapper;
import Mih.demo.Modules.Annotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AdminImp implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public void publishAnnotation(Annotation annotation) {
        Date date = annotation.getDate();
        String message = annotation.getDetials();
        adminMapper.publishAnnotation(date, message);
    }
}
