package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルとコースIDテーブルに紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * すべての受講生の基本情報一覧を検索する 検索条件として論理削除の情報を使用する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生リストもしくはfalseの受講生リストを返す
   */
  List<Student> searchStudentList(boolean deleted);

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  List<StudentCourse> searchAllStudentCourseList(boolean deleted);

  /**
   * 指定の受講生のコース情報一覧を検索する
   *
   * @param studentId 検索する受講生のコース情報を受け取る
   * @return 受け取ったコース情報で検索したコース情報を返す
   */
  List<StudentCourse> searchStudentCourseList(String studentId);

  /**
   * 単一の受講生コース情報を検索する
   *
   * @param studentCourse 検索する受講生コース情報を受け取る
   * @return 検索した受講生コース情報を返す
   */
  StudentCourse searchStudentCourse(StudentCourse studentCourse);

  /**
   * 指定のコースIDとコース名情報を取得する
   *
   * @param studentCourse 検索するコース情報を受け取る
   * @return 検索したコース情報を返す
   */
  StudentCourse searchCourseName(StudentCourse studentCourse);

  /**
   * 受講生の基本情報を登録する
   *
   * @param student 登録する受講生の基本情報を受け取る
   */
  void insertStudent(Student student);

  /**
   * 受講生のコース情報を登録する
   *
   * @param studentCourse 登録する受講生のコース情報を受け取る
   */
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生の基本情報を更新する
   *
   * @param student 更新する受講生の基本情報を受け取る
   */
  void updateStudent(Student student);

  /**
   * 受講生のコース情報を更新する
   *
   * @param studentCourse 更新する受講生のコース情報を受け取る
   */
  void updateStudentCourse(StudentCourse studentCourse);

}
