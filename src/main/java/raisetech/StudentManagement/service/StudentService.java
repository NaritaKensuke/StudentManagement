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
    return repository.searchStudents();
  }

  //リポジトリから受講生情報を受け取りコントローラーに渡す
  public Student searchStudent(Student student) {
    return repository.searchStudent(student);
  }

  //リポジトリからコース情報を受け取りコントローラーに渡す
  public List<StudentCourse> searchStudentsCoursesList(){
    return repository.searchStudentsCourses();
  }

  // 受講生のコース情報を取得
  public List<StudentCourse> searchStudentCourses(StudentCourse studentCourse){
    return repository.searchStudentCourses(studentCourse);
  }

  // 単一コース情報を取得
  public StudentCourse searchStudentCourse(StudentCourse studentCourse){
    return repository.searchStudentCourse(studentCourse);
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

    studentCourseDetail.setCourseId(
        repository.searchCoursesName(studentCourseDetail).getCourseId());
    studentCourseDetail.setStartedDate(LocalDate.now());
    studentCourseDetail.setFinishDate(LocalDate.now().plusMonths(6));

    repository.insertStudentCourse(studentCourseDetail);
  }

  //入力されている受講生情報のみリポジトリに渡す
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
     Student student = repository.searchStudent(studentDetail.getStudent());

    if (!(studentDetail.getStudent().getName().isEmpty())){
      student.setName(studentDetail.getStudent().getName());
    }
    if (!(studentDetail.getStudent().getNameReading().isEmpty())){
      student.setNameReading(studentDetail.getStudent().getNameReading());
    }
    if (!(studentDetail.getStudent().getNickname().isEmpty())){
      student.setNickname(studentDetail.getStudent().getNickname());
    }
    if (!(studentDetail.getStudent().getMailAddress().isEmpty())){
      student.setMailAddress(studentDetail.getStudent().getMailAddress());
    }
    if (!(studentDetail.getStudent().getCity().isEmpty())){
      student.setCity(studentDetail.getStudent().getCity());
    }
    if (!(studentDetail.getStudent().getAge() == 0)){
      student.setAge(studentDetail.getStudent().getAge());
    }
    if (!(studentDetail.getStudent().getGender().isEmpty())){
      student.setGender(studentDetail.getStudent().getGender());
    }
    if (!(studentDetail.getStudent().getRemark().isEmpty())) {
      student.setRemark(studentDetail.getStudent().getRemark());
    }
    repository.updateStudent(student);
  }

  @Transactional
  public void updateStudentCourse(StudentDetail studentDetail){
    StudentCourse studentCourse1 = studentDetail.getStudentCourse();
    StudentCourse studentCourse =
        repository.searchStudentCourse(studentDetail.getStudentCourse());

    if (!(studentCourse1.getCourseName().isEmpty())){
      studentCourse.setCourseName(studentCourse1.getCourseName());
      studentCourse.setCourseId(
          repository.searchCoursesName(studentCourse1).getCourseId());
      }
    if ((studentCourse1.getStartedDate() == null) && (studentCourse1.getFinishDate() == null)){
      // 何もしない
    }else if (studentCourse1.getFinishDate() == null){
      studentCourse.setStartedDate(studentCourse1.getStartedDate());
      studentCourse.setFinishDate(studentCourse1.getStartedDate().plusMonths(6));
    }else if (studentCourse1.getStartedDate() == null){
      studentCourse.setFinishDate(studentCourse1.getFinishDate());
    }else {
      studentCourse.setStartedDate(studentCourse1.getStartedDate());
      studentCourse.setFinishDate(studentCourse1.getFinishDate());
    }
    repository.updateStudentCourse(studentCourse);
  }
}
