package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public List<Student> getStudentList(@RequestParam("delete") boolean delete) {
    return service.searchStudentList(delete);
  }

  //コース情報表示
  @GetMapping("/allStudentCourseList")
  public List<StudentCourse> getAllStudentsCourseList(Model model){
    return service.searchStudentsCoursesList();
  }

  //選択した受講生のコース情報表示
  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList(
      @RequestParam("studentId") String studentId){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(studentId);

    return service.searchStudentCourses(studentCourse);
  }

  //受講生情報登録画面
  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  //受講生情報、コース情報登録
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail,
      BindingResult result){
    if(result.hasErrors()){
      return "typeError";
    }
    //新規受講生情報を登録する
    service.registerStudentDetail(studentDetail);
    //受講生コース情報を登録する
    service.registerStudentCourseDetail(studentDetail);
    return "redirect:/studentList";
  }

  //受講生情報更新
  @GetMapping("/renewalStudent")
  public String showStudentUpdateView(@RequestParam("studentId") String studentId, Model model){
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(service.searchStudent(studentId));
    studentDetail.getStudent().setStudentId(studentId);

    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  //受講生情報更新
  @PostMapping("/updateStudent")
  public ResponseEntity<Student> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail.getStudent());
  }
  

  //コース情報更新
  @GetMapping("/renewalStudentCourse")
  public String showStudentCourseUpdateView(@RequestParam("studentId") String studentId,
      @RequestParam("courseDetailId") int courseDetailId, Model model){
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourse(service.searchStudentCourse(courseDetailId));

    model.addAttribute("studentDetail", studentDetail);
    return "updateStudentCourse";
  }

  //コース情報更新
  @PostMapping("/updateStudentCourse")
  public StudentCourse updateStudentCourse(@RequestBody StudentDetail studentDetail){
    service.updateStudentCourse(studentDetail);
    return service.searchStudentCourse(
        studentDetail.getStudentCourse().getCourseDetailId());
  }
}
