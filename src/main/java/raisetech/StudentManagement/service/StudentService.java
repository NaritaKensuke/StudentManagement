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
    return repository.searchStudents(deleted);
  }

  /**
   * 指定の受講生の基本情報を検索する
   *
   * @param studentId 受講生IDの情報を受け取る
   * @return 受け取った受講生IDを持つ受講生の基本情報を検索する
   */
  public Student searchStudent(String studentId) {
    Student student = new Student();
    student.setStudentId(studentId);
    return repository.searchStudent(student);
  }

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  public List<StudentCourse> searchStudentsCoursesList(boolean deleted) {
    return repository.searchStudentsCourses(deleted);
  }

  /**
   * 指定の受講生のコース情報一覧を検索する
   *
   * @param studentCourse 検索する受講生のコース情報を受け取る
   * @return 受け取ったコース情報で検索したコース情報を返す
   */
  public List<StudentCourse> searchStudentCourses(StudentCourse studentCourse) {
    return repository.searchStudentCourses(studentCourse);
  }

  /**
   * 単一の受講生コース情報を検索する
   *
   * @param courseDetailId 検索する受講生コース情報の固有IDを受け取る
   * @return 検索した受講生コース情報を返す
   */
  public StudentCourse searchStudentCourse(int courseDetailId) {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseDetailId(courseDetailId);
    return repository.searchStudentCourse(studentCourse);
  }

  /**
   * 受講生の基本情報を登録する
   *
   * @param studentDetail 登録する受講生情報を受け取る
   */
  @Transactional
  public void registerStudentDetail(StudentDetail studentDetail) {
    Student studentInfo = studentDetail.getStudent();
    repository.insertStudent(studentInfo);
  }

  /**
   * 受講生のコース情報を登録する
   *
   * 受講生一覧を検索し、最後に登録した受講生の受講生IDを登録するコース情報に登録する
   * 登録するコースのコースIDを検索し登録する
   * コース開始日をコース情報を登録する日付で登録する
   * コース終了日をコース情報を登録する日付の6か月後で登録する
   *
   * @param studentDetail 登録するコース情報を受け取る
   */
  @Transactional
  public void registerStudentCourseDetail(StudentDetail studentDetail) {
    StudentCourse studentCourseDetail = studentDetail.getStudentCourse();

    studentCourseDetail.setStudentId(
        repository.searchStudents(false).getLast().getStudentId());
    studentCourseDetail.setCourseId(
        repository.searchCoursesName(studentCourseDetail).getCourseId());
    studentCourseDetail.setStartedDate(LocalDate.now());
    studentCourseDetail.setFinishDate(LocalDate.now().plusMonths(6));

    repository.insertStudentCourse(studentCourseDetail);
  }

  /**
   * 受講生の基本情報を更新する
   *
   * 更新する受講生情報の論理削除がtureだった場合、該当の受講生の受講生IDをもつコース情報の論理削除をtrueに更新する
   *
   * @param studentDetail 更新する基本情報を受け取る
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent().isDeleted()){
      List<StudentCourse> studentCourseList;
      StudentCourse studentCourseContainer = new StudentCourse();
      studentCourseContainer.setStudentId(studentDetail.getStudent().getStudentId());
      studentCourseList = repository.searchStudentCourses(studentCourseContainer);
      for (StudentCourse studentCourse : studentCourseList){
        studentCourse.setDeleted(true);
        repository.updateStudentCourse(studentCourse);
      }
    }
    repository.updateStudent(studentDetail.getStudent());
  }

  /**
   * 受講生のコース情報を更新する
   *
   * 更新されたコース情報でコースIDを検索し登録する
   *
   * コース開始日と終了日の更新情報によって処理を場合分けする
   *  開始日と終了日の情報がnullで更新された場合
   *    更新前の開始日と終了日で登録する
   *  開始日のみ更新された場合
   *    開始日は更新された情報で登録する
   *    終了日は更新前の情報で登録する
   *  終了日のみ更新された場合
   *    開始日は更新前の情報で登録する
   *    終了日は更新された情報で登録する
   *  開始日と終了日の両方が更新された場合
   *    更新されたの開始日と終了日で登録する
   *
   * @param studentDetail 更新するコース情報を受け取る
   */
  @Transactional
  public void updateStudentCourse(StudentDetail studentDetail) {
    StudentCourse studentCourse = studentDetail.getStudentCourse();
    studentCourse.setCourseId(repository.searchCoursesName(studentCourse).getCourseId());

    if ((studentCourse.getStartedDate() == null) && (studentCourse.getFinishDate() == null)) {
      studentCourse.setStartedDate(
          repository.searchStudentCourse(studentCourse).getStartedDate());
      studentCourse.setFinishDate(
          repository.searchStudentCourse(studentCourse).getFinishDate());
    } else if (studentCourse.getFinishDate() == null) {
      studentCourse.setStartedDate(studentCourse.getStartedDate());
      studentCourse.setFinishDate(studentCourse.getStartedDate().plusMonths(6));
    } else if (studentCourse.getStartedDate() == null) {
      studentCourse.setFinishDate(studentCourse.getFinishDate());
    } else {
      studentCourse.setStartedDate(studentCourse.getStartedDate());
      studentCourse.setFinishDate(studentCourse.getFinishDate());
    }
    repository.updateStudentCourse(studentCourse);
  }
}
