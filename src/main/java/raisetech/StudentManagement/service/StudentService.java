package raisetech.StudentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //全件検索
    List<Student> students = repository.searchStudent();

    //絞り込み検索で30代の人を抽出
    List<Student> filterStudentsList = students.stream()
        .filter(student -> 30 <= student.getAge() && student.getAge() < 40)
        .collect(Collectors.toList());

    //抽出したリストをコントローラーに返す
    return filterStudentsList;
  }

  public List<StudentCourse> searchStudentsCoursesList(){
    //絞り込み検索で「Javaコース」のコース情報のみを抽出する
    List<StudentCourse> filterStudentsCoursesList = repository.searchStudentsCourses().stream()
        .filter(studentsCourses -> studentsCourses.getCourseName().equals("Java"))
        .collect(Collectors.toList());

    //抽出したリストをコントローラーに返す
    return filterStudentsCoursesList;
  }
}
