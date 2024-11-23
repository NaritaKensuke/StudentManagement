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

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCourses();

  @Insert("INSERT students values(#{studentId},#{name},#{nameReading},#{nickname},"
      + "#{mailAddress},#{city},#{age},#{gender},#{remark},false)")
  void insertStudent(Student student);

  @Insert("INSERT students_courses values(#{uniqueId}, #{studentId}, #{courseId},"
      + " #{courseName}, #{startedDate}, #{finishDate})")
  void insertStudentCourse(StudentCourse studentCourse);

  @Update("UPDATE students SET name=#{name} WHERE student_id=#{studentId}")
  void updateStudentName(Student student);

  @Update("UPDATE students SET name_reading=#{nameReading} WHERE student_id=#{studentId}")
  void updateStudentNameReading(Student student);

  @Update("UPDATE students SET nickname=#{nickname} WHERE student_id=#{studentId}")
  void updateStudentNickName(Student student);

  @Update("UPDATE students SET mail_address=#{mailAddress} WHERE student_id=#{studentId}")
  void updateStudentMailAddress(Student student);

  @Update("UPDATE students SET city=#{city} WHERE student_id=#{studentId}")
  void updateStudentCity(Student student);

  @Update("UPDATE students SET age=#{age} WHERE student_id=#{studentId}")
  void updateStudentAge(Student student);

  @Update("UPDATE students SET gender=#{gender} WHERE student_id=#{studentId}")
  void updateStudentGender(Student student);

  @Update("UPDATE students SET remark=#{remark} WHERE student_id=#{studentId}")
  void updateStudentRemark(Student student);

  @Update("UPDATE students_courses SET (student_id=#{studentId})"
      + " WHERE student_id=#{studentId}")
  void updateStudentCourseStudentId(StudentCourse studentCourse);

  @Update("UPDATE students_courses SET (course_id=#{courseId}, course_name=#{courseName})"
      + " WHERE unique_id=#{uniqueId}")
  void updateStudentCourse(StudentCourse studentCourse);

  @Update("UPDATE students_courses SET started_date=#{startedDate}, "
      + "finish_date=#{finishDate} WHERE unique_id=#{uniqueId}")
  void updateStudentCourseDate(StudentCourse studentCourse);

  @Update("UPDATE students_courses SET finish_date=#{finishDate} WHERE unique_id=#{uniqueId}")
  void updateStudentCourseFinishDate(StudentCourse studentCourse);
}
