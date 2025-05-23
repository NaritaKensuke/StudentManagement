package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.CourseState;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルとコースIDテーブルに紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * すべての受講生の基本情報一覧を検索する
   *
   * @return 検索した基本情報一覧を返す
   */
  List<Student> searchStudentList();

  /**
   * 論理削除情報で受講生の基本情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除情報で検索した受講生の基本情報を返す
   */
  List<Student> searchStudentListWhereDeleted(boolean deleted);

  /**
   * 受講生IDで受講生の基本情報を検索する
   *
   * @param studentId 受講生IDを受け取る
   * @return 受け取った受講生IDで検索した受講生の基本情報を返す
   */
  List<Student> searchStudentListWhereStudentId(String studentId);

  /**
   * 受講生のふりがなで受講生の基本情報を検索する
   *
   * @param nameReading 名前のふりがなを受け取る
   * @return 受け取った名前のふりがなで検索した受講生の基本情報を返す
   */
  List<Student> searchStudentListWhereNameReading(String nameReading);

  /**
   * 受講生の性別で受講生の基本情報を検索する
   *
   * @param gender 性別を受け取る
   * @return 受け取った性別で検索した受講生の基本情報を返す
   */
  List<Student> searchStudentListWhereGender(String gender);

  /**
   * 受講生の年齢で受講生の基本情報を検索する
   *
   * @param age 受講生IDを受け取る
   * @return 受け取った年齢で検索した受講生の基本情報を返す
   */
  List<Student> searchStudentListWhereAge(int age);

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  List<StudentCourse> searchAllStudentCourseList(boolean deleted);

  List<StudentCourse> searchAllStudentCourseListWhereCourseId(String courseId);

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
   * すべてのコースの申込状況を検索する
   *
   * @return 検索した申込状況リストを返す
   */
  List<CourseState> searchCourseStateList();

  /**
   * 指定のコースの申込状況を検索する
   *
   * @param courseDetailId コース情報固有IDを受け取る
   * @return 検索した申込状況を返す
   */
  CourseState searchCourseState(String courseDetailId);

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
   * コースの申込状況を登録する
   *
   * @param courseDetailId 登録する申込状況を受け取る
   */
  void insertCourseState(String courseDetailId);

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

  /**
   * コースの申込状況を更新する
   *
   * @param courseState 更新する申込状況を受け取る
   */
  void updateCourseState(CourseState courseState);

}
