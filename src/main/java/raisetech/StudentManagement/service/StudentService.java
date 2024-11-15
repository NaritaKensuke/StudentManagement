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
  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  //リポジトリからコース情報を受け取りコントローラーに渡す
  public List<StudentCourse> searchStudentsCoursesList(){
    return repository.searchStudentsCourses();
  }

  //受講生情報をリポジトリに渡す
  @Transactional
  public void registerStudentDetail(StudentDetail studentDetail){
    Student studentInfo = studentDetail.getStudent();
    repository.insertStudent(studentInfo);
  }

  //コース情報をリポジトリに渡す
  @Transactional
  public void registerStudentCourseDetail(StudentDetail studentDetail) {
    StudentCourse studentCourseDetail = studentDetail.getStudentCourse();

    switch (studentCourseDetail.getCourseName()) {
      case "Java" -> studentCourseDetail.setCourseId("C1");
      case "英会話" -> studentCourseDetail.setCourseId("C2");
      case "デザイン" -> studentCourseDetail.setCourseId("C3");
      case "Phython" -> studentCourseDetail.setCourseId("C4");
      case "AWS" -> studentCourseDetail.setCourseId("C5");
      case "マーケティング" -> studentCourseDetail.setCourseId("C6");
    }
    studentCourseDetail.setStudentId(repository.searchStudent().getLast().getStudentId());
    studentCourseDetail.setStartedDate(LocalDate.now());
    studentCourseDetail.setFinishDate(LocalDate.now().plusMonths(6));

    repository.insertStudentCourse(studentCourseDetail);
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    if (!(studentDetail.getStudent().getName().isEmpty())){
      repository.updateStudentName(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getNameReading().isEmpty())){
      repository.updateStudentNameReading(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getNickname().isEmpty())){
      repository.updateStudentNickName(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getMailAddress().isEmpty())){
      repository.updateStudentMailAddress(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getCity().isEmpty())){
      repository.updateStudentCity(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getAge() == 0)){
      repository.updateStudentAge(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getGender().isEmpty())){
      repository.updateStudentGender(studentDetail.getStudent());
    }
    if (!(studentDetail.getStudent().getGender().isEmpty())) {
      repository.updateStudentRemark(studentDetail.getStudent());
    }
  }
}
