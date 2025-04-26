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

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //リポジトリから受講生情報を受け取りコントローラーに渡す
  public List<Student> searchStudentList(boolean deleted) {
    return repository.searchStudents(deleted);
  }

  //リポジトリから受講生情報を受け取りコントローラーに渡す
  public Student searchStudent(String studentId) {
    Student student = new Student();
    student.setStudentId(studentId);
    return repository.searchStudent(student);
  }

  //リポジトリからコース情報を受け取りコントローラーに渡す
  public List<StudentCourse> searchStudentsCoursesList(boolean deleted) {
    return repository.searchStudentsCourses(deleted);
  }

  // 受講生のコース情報を取得
  public List<StudentCourse> searchStudentCourses(StudentCourse studentCourse) {
    return repository.searchStudentCourses(studentCourse);
  }

  // 単一コース情報を取得
  public StudentCourse searchStudentCourse(int courseDetailId) {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseDetailId(courseDetailId);
    return repository.searchStudentCourse(studentCourse);
  }

  //受講生情報をリポジトリに渡す
  @Transactional
  public void registerStudentDetail(StudentDetail studentDetail) {
    Student studentInfo = studentDetail.getStudent();
    repository.insertStudent(studentInfo);
  }

  //コース情報をリポジトリに渡す
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

  //受講生情報をリポジトリに渡す
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
