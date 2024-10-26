package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  public List<StudentCourse> searchStudentsCoursesList(){
    return repository.searchStudentsCourses();
  }

  public void registerStudentDetail(StudentDetail studentDetail){
    Student studentInfo = studentDetail.getStudent();
    repository.insertStudent(studentInfo);
  }

  public void registerStudentCourseDetail(StudentDetail studentDetail) {
    StudentCourse studentCourseDetail = studentDetail.getStudentCourse();
    studentCourseDetail.setStudentId(studentDetail.getStudent().getStudentId());

    repository.insertStudentCourse(studentCourseDetail);
  }
}
