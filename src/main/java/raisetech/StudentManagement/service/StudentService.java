package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うService
 * 受講生の検索や登録・更新処理を行う
 */
@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * すべての受講生の基本情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生リストもしくはfalseの受講生リストを返す
   */
  public List<Student> searchStudentList(boolean deleted) {
    return repository.searchStudentList(deleted);
  }

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  public List<StudentCourse> searchAllStudentCourseList(boolean deleted) {
    return repository.searchAllStudentCourseList(deleted);
  }

  /**
   * 指定の受講生のコース情報一覧を検索する
   *
   * @param studentId 検索する受講生の受講生IDを受け取る
   * @return 受け取ったコース情報で検索したコース情報を返す
   */
  public List<StudentCourse> searchStudentCourseList(String studentId) {
    return repository.searchStudentCourseList(studentId);
  }

  /**
   * 単一の受講生コース情報を検索する
   *
   * @param studentDetail 検索する受講生コース情報の固有IDを受け取る
   * @return 検索した受講生コース情報を返す
   */
  public StudentCourse searchStudentCourse(StudentDetail studentDetail) {
    return repository.searchStudentCourse(studentDetail.getStudentCourseList().getFirst());
  }

  /**
   * 受講生の基本情報とコース情報を登録する
   *
   * @param studentDetail 登録する受講生情報を受け取る
   */
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());

    List<StudentCourse> studentCourseList = studentDetail.getStudentCourseList();

    studentCourseList.forEach(studentCourse -> {
      setCourseDetail(studentCourse);
      repository.insertStudentCourse(studentCourse);
    });
  }

  /**
   * コースID、開始日、終了日を自動で登録する
   * 
   * 受講生一覧を検索し、最後に登録した受講生の受講生IDを登録するコース情報に登録する
   * 登録するコースのコースIDを検索し登録する
   * コース開始日をコース情報を登録する日付で登録する
   * コース終了日をコース情報を登録する日付の6か月後で登録する
   * 
   * @param studentCourse 登録するコース名をもつコース情報を受け取る
   */
  private void setCourseDetail(StudentCourse studentCourse) {
    studentCourse.setStudentId(
        repository.searchStudentList(false).getLast().getStudentId());
    studentCourse.setCourseId(
        repository.searchCourseName(studentCourse).getCourseId());
    studentCourse.setStartedDate(LocalDate.now());
    studentCourse.setFinishDate(LocalDate.now().plusMonths(6));
  }

  /**
   * 受講生の基本情報を更新する
   *
   * 更新する受講生情報の論理削除がtureだった場合、
   * 該当の受講生の受講生IDをもつコース情報の論理削除をtrueに更新する
   *
   * @param studentDetail 更新する基本情報を受け取る
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent().isDeleted()){
      List<StudentCourse> studentCourseList;
      studentCourseList = repository.searchStudentCourseList(
          studentDetail.getStudent().getStudentId());
      studentCourseList.forEach(studentCourse -> {
        studentCourse.setDeleted(true);
        repository.updateStudentCourse(studentCourse);
      });
    }
    repository.updateStudent(studentDetail.getStudent());
  }

  /**
   * 受講生のコース情報を更新する
   *
   * 更新されたコース情報でコースIDを検索し登録する
   *
   * @param studentDetail 更新するコース情報を受け取る
   */
  @Transactional
  public void updateStudentCourse(StudentDetail studentDetail) {
    List<StudentCourse> studentCourseList = studentDetail.getStudentCourseList();
    studentCourseList.forEach(studentCourse -> {
      studentCourse.setCourseId(repository.searchCourseName(studentCourse).getCourseId());
      repository.updateStudentCourse(studentCourse);
    });
  }
}
