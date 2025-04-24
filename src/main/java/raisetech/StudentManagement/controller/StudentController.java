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

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //受講生情報表示
  @GetMapping("/studentList")
  public List<Student> getStudentList(@RequestParam("deleted") boolean deleted) {
    return service.searchStudentList(deleted);
  }

  //コース情報表示
  @GetMapping("/allStudentCourseList")
  public List<StudentCourse> getAllStudentsCourseList(
      @RequestParam("deleted") boolean deleted){
    return service.searchStudentsCoursesList(deleted);
  }

  //選択した受講生のコース情報表示
  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList(
      @RequestParam("studentId") String studentId){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(studentId);

    return service.searchStudentCourses(studentCourse);
  }

  //受講生情報、コース情報登録
  @PostMapping("/registerStudent")
  public ResponseEntity<List> registerStudent(
      @RequestBody StudentDetail studentDetail){
    //新規受講生情報を登録する
    service.registerStudentDetail(studentDetail);
    //受講生コース情報を登録する
    service.registerStudentCourseDetail(studentDetail);
    return ResponseEntity.ok(service.searchStudentList(false));
  }

  //受講生情報更新
  @PostMapping("/updateStudent")
  public ResponseEntity<Student> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail.getStudent());
  }

  //コース情報更新
  @PostMapping("/updateStudentCourse")
  public StudentCourse updateStudentCourse(@RequestBody StudentDetail studentDetail){
    service.updateStudentCourse(studentDetail);
    return service.searchStudentCourse(
        studentDetail.getStudentCourse().getCourseDetailId());
  }
}
