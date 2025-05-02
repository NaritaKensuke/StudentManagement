package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルとコースIDテーブルに紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * すべての受講生の基本情報一覧を検索する
   * 検索条件として論理削除の情報を使用する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生リストもしくはfalseの受講生リストを返す
   */
  @Select("SELECT * FROM students WHERE is_delete=#{deleted}")
  List<Student> searchStudents(boolean deleted);

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  @Select("SELECT * FROM students_courses WHERE is_delete=#{deleted} ORDER BY course_id")
  List<StudentCourse> searchStudentsCourses(boolean deleted);

  /**
   * 指定の受講生のコース情報一覧を検索する
   *
   * @param studentId 検索する受講生のコース情報を受け取る
   * @return 受け取ったコース情報で検索したコース情報を返す
   */
  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCourses(String studentId);

  /**
   * 単一の受講生コース情報を検索する
   *
   * @param studentCourse 検索する受講生コース情報を受け取る
   * @return 検索した受講生コース情報を返す
   */
  @Select("SELECT * FROM students_courses WHERE course_detail_id=#{courseDetailId}")
  StudentCourse searchStudentCourse(StudentCourse studentCourse);

  /**
   * 指定のコースIDとコース名情報を取得する
   *
   * @param studentCourse 検索するコース情報を受け取る
   * @return 検索したコース情報を返す
   */
  @Select("SELECT * FROM course_id_name WHERE course_name=#{courseName}")
  StudentCourse searchCoursesName(StudentCourse studentCourse);

  /**
   * 受講生の基本情報を登録する
   *
   * @param student 登録する受講生の基本情報を受け取る
   */
  @Insert("INSERT students values(#{studentId},#{name},#{nameReading},#{nickname},"
      + "#{mailAddress},#{city},#{age},#{gender},#{remark},false)")
  void insertStudent(Student student);

  /**
   * 受講生のコース情報を登録する
   *
   * @param studentCourse 登録する受講生のコース情報を受け取る
   */
  @Insert("INSERT students_courses values(#{courseDetailId}, #{studentId}, #{courseId},"
      + " #{courseName}, #{startedDate}, #{finishDate}, false)")
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生の基本情報を更新する
   *
   * @param student 更新する受講生の基本情報を受け取る
   */
  @Update("Update students SET name=#{name}, name_reading=#{nameReading},nickname=#{nickname},"
      + "mail_address=#{mailAddress}, city=#{city}, age=#{age}, gender=#{gender},"
      + "remark=#{remark}, is_delete=#{deleted} WHERE student_id=#{studentId}")
  void updateStudent(Student student);

  /**
   * 受講生のコース情報を更新する
   *
   * @param studentCourse 更新する受講生のコース情報を受け取る
   */
  @Update("UPDATE students_courses SET course_id=#{courseId}, course_name=#{courseName},"
      + " started_date=#{startedDate}, finish_date=#{finishDate}, is_delete=#{deleted}"
      + " WHERE course_detail_id=#{courseDetailId}")
  void updateStudentCourse(StudentCourse studentCourse);

}
