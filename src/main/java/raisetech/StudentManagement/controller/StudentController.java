package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@Controller
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
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentsCourses = service.searchStudentsCoursesList();

    model.addAttribute("studentList",
        converter.convertStudentDetails(students,studentsCourses));
    return "studentList";
  }

  //コース情報表示
  @GetMapping("/studentCourseList")
  public String getStudentsCourseList(Model model){
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentsCourses = service.searchStudentsCoursesList();

    List<StudentCourse> studentCourseList = converter.convertStudentCourse(
        converter.convertStudentDetails(students, studentsCourses));

    model.addAttribute("studentCourseList", studentCourseList);
    return "studentCourseList";
  }

  //受講生情報登録画面
  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  //受講生情報、コース情報登録
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "typeError";
    }
    //新規受講生情報を登録する
    service.registerStudentDetail(studentDetail);
    //受講生コース情報を登録する
    service.registerStudentCourseDetail(studentDetail);
    return "redirect:/studentList";
  }
}
