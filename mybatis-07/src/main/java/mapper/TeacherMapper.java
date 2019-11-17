package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Teacher;

import java.util.List;

public interface TeacherMapper {
    //获取所有老师
    List<Teacher> getTeachers();

    //获取指定老师下的所有学生及老师信息
    Teacher getTeacher(@Param("tid") int id);

    //获取指定老师下的所有学生及老师信息
    Teacher getTeacher2(@Param("tid") int id);
}
