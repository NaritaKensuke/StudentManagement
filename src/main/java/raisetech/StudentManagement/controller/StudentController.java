package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるController
 */
@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  /**
   * すべての受講生の基本情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生リストもしくはfalseの受講生リストを返す
   */
  @GetMapping("/studentList")
  public List<Student> getStudentList(@RequestParam("deleted") boolean deleted) {
    return service.searchStudentList(deleted);
  }

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  @GetMapping("/allStudentCourseList")
  public List<StudentCourse> getAllStudentsCourseList(
      @RequestParam("deleted") boolean deleted){
    return service.searchStudentsCoursesList(deleted);
  }

  /**
   * 特定の受講生のコース情報を検索する
   *
   * @param studentId 受講生IDの情報を受け取る
   * @return 受け取った受講生IDを持つ受講生コース情報を返す
   */
  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList(
      @RequestParam("studentId") String studentId){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(studentId);

    return service.searchStudentCourses(studentCourse);
  }

  /**
   * 受講生情報を登録する
   *
   * @param studentDetail 登録する受講生情報を受け取る
   * @return 正常に処理された場合、論理削除がfalseの受講生情報リストを返す
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<List> registerStudent(
      @RequestBody StudentDetail studentDetail){
    //新規受講生情報を登録する
    service.registerStudentDetail(studentDetail);
    //受講生コース情報を登録する
    service.registerStudentCourseDetail(studentDetail);
    return ResponseEntity.ok(service.searchStudentList(false));
  }

  /**
   * 受講生の基本情報を更新する
   *
   * @param studentDetail 更新する受講生の基本情報を受け取る
   * @return 正常に処理された場合、更新した受講生の基本情報を返す
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<Student> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail.getStudent());
  }

  /**
   * 受講生のコース情報を更新する
   *
   * @param studentDetail 更新する受講生のコース情報を受け取る
   * @return 正常に処理された場合、更新した受講生のコース情報を受け取る
   */
  @PostMapping("/updateStudentCourse")
  public StudentCourse updateStudentCourse(@RequestBody StudentDetail studentDetail){
    service.updateStudentCourse(studentDetail);
    return service.searchStudentCourse(
        studentDetail.getStudentCourse().getCourseDetailId());
  }
}
