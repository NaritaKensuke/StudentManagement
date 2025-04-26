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

  @Select("SELECT * FROM students WHERE is_delete=#{deleted}")
  List<Student> searchStudents(boolean deleted);

  @Select("SELECT * FROM students WHERE student_id=#{studentId}")
  Student searchStudent(Student student);

  @Select("SELECT * FROM students_courses WHERE is_delete=#{deleted} ORDER BY course_id")
  List<StudentCourse> searchStudentsCourses(boolean deleted);

  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCourses(StudentCourse studentCourse);

  @Select("SELECT * FROM students_courses WHERE course_detail_id=#{courseDetailId}")
  StudentCourse searchStudentCourse(StudentCourse studentCourse);

  @Select("SELECT * FROM course_id_name WHERE course_name=#{courseName}")
  StudentCourse searchCoursesName(StudentCourse studentCourse);

  @Insert("INSERT students values(#{studentId},#{name},#{nameReading},#{nickname},"
      + "#{mailAddress},#{city},#{age},#{gender},#{remark},false)")
  void insertStudent(Student student);

  @Insert("INSERT students_courses values(#{courseDetailId}, #{studentId}, #{courseId},"
      + " #{courseName}, #{startedDate}, #{finishDate}, false)")
  void insertStudentCourse(StudentCourse studentCourse);

  @Update("Update students SET name=#{name}, name_reading=#{nameReading},nickname=#{nickname},"
      + "mail_address=#{mailAddress}, city=#{city}, age=#{age}, gender=#{gender},"
      + "remark=#{remark}, is_delete=#{deleted} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_id=#{courseId}, course_name=#{courseName},"
      + " started_date=#{startedDate}, finish_date=#{finishDate}, is_delete=#{deleted}"
      + " WHERE course_detail_id=#{courseDetailId}")
  void updateStudentCourse(StudentCourse studentCourse);

}
