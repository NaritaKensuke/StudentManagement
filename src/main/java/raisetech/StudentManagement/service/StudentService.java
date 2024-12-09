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

    for (StudentCourse studentCourse : repository.searchCoursesName()){
      if (studentCourse.getCourseName().equals(studentCourseDetail.getCourseName())){
        studentCourseDetail.setCourseId(studentCourse.getCourseId());
      }
    }
    studentCourseDetail.setStartedDate(LocalDate.now());
    studentCourseDetail.setFinishDate(LocalDate.now().plusMonths(6));

    repository.insertStudentCourse(studentCourseDetail);
  }

  //入力されている受講生情報のみリポジトリに渡す
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
     List<Student> studentList = repository.searchStudent();
     Student studentContainer = new Student();

    for(Student student : studentList){
      if (student.getStudentId().equals(studentDetail.getStudent().getStudentId())){
        studentContainer = student;
      }
    }
    if (!(studentDetail.getStudent().getName().isEmpty())){
      studentContainer.setName(studentDetail.getStudent().getName());
    }
    if (!(studentDetail.getStudent().getNameReading().isEmpty())){
      studentContainer.setNameReading(studentDetail.getStudent().getNameReading());
    }
    if (!(studentDetail.getStudent().getNickname().isEmpty())){
      studentContainer.setNickname(studentDetail.getStudent().getNickname());
    }
    if (!(studentDetail.getStudent().getMailAddress().isEmpty())){
      studentContainer.setMailAddress(studentDetail.getStudent().getMailAddress());
    }
    if (!(studentDetail.getStudent().getCity().isEmpty())){
      studentContainer.setCity(studentDetail.getStudent().getCity());
    }
    if (!(studentDetail.getStudent().getAge() == 0)){
      studentContainer.setAge(studentDetail.getStudent().getAge());
    }
    if (!(studentDetail.getStudent().getGender().isEmpty())){
      studentContainer.setGender(studentDetail.getStudent().getGender());
    }
    if (!(studentDetail.getStudent().getRemark().isEmpty())) {
      studentContainer.setRemark(studentDetail.getStudent().getRemark());
    }
    repository.updateStudent(studentContainer);
  }

  @Transactional
  public void updateStudentCourse(StudentDetail studentDetail){
    StudentCourse studentCourseDetail = studentDetail.getStudentCourse();
    StudentCourse studentCourseContainer = new StudentCourse();

    for (StudentCourse studentCourse : repository.searchStudentsCourses()){
      if (studentCourse.getCourseDetailId() == studentCourseDetail.getCourseDetailId()){
        studentCourseContainer = studentCourse;
      }
    }
    if (!(studentCourseDetail.getCourseName().isEmpty())){
      studentCourseContainer.setCourseName(studentCourseDetail.getCourseName());
      for (StudentCourse studentCourse : repository.searchCoursesName()){
        if (studentCourse.getCourseName().equals(studentCourseDetail.getCourseName())){
          studentCourseContainer.setCourseId(studentCourse.getCourseName());
        }
      }
    }
    if ((studentCourseDetail.getFinishDate() == null) &
        (studentCourseDetail.getStartedDate() == null)){
      //何もしない
    }else if (studentCourseDetail.getFinishDate() == null){
      studentCourseContainer.setStartedDate(studentCourseDetail.getStartedDate());
      studentCourseContainer.setFinishDate(
          studentCourseDetail.getStartedDate().plusMonths(6));
    }else if (studentCourseDetail.getStartedDate() == null){
      studentCourseContainer.setFinishDate(studentCourseDetail.getFinishDate());
    }else {
      studentCourseContainer.setStartedDate(studentCourseDetail.getStartedDate());
      studentCourseContainer.setFinishDate(studentCourseDetail.getFinishDate());
    }
    repository.updateStudentCourse(studentCourseContainer);
  }
}
