package Mih.demo.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface AdminMapper {

    void publishAnnotation(@Param("date") Date date, @Param("message")String message);
}
