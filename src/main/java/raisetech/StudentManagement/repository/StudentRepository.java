package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  @Select("SELECT * FROM students_courses ORDER BY course_id")
  List<StudentCourse> searchStudentsCourses();

  @Select("SELECT * FROM course_id_name")
  List<StudentCourse> searchCoursesName();

  @Insert("INSERT students values(#{studentId},#{name},#{nameReading},#{nickname},"
      + "#{mailAddress}, city=#{city},age=#{age},#{gender},#{remark},false)")
  void insertStudent(Student student);

  @Insert("INSERT students_courses values(#{studentDetailCourseId}, #{studentId}, #{courseId},"
      + " #{courseName}, #{startedDate}, #{finishDate})")
  void insertStudentCourse(StudentCourse studentCourse);

  @Update("Update students SET name=#{name}, name_reading=#{nameReading},nickname=#{nickname},"
      + "mail_address=#{mailAddress}, city=#{city}, age=#{age},gender=#{gender},"
      + "remark=#{remark} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_id=#{courseId}, course_name=#{courseName},"
      + " started_date=#{startedDate}, finish_date=#{finishDate}"
      + " WHERE course_detail_id=#{courseDetailId}")
  void updateStudentCourse(StudentCourse studentCourse);

}
