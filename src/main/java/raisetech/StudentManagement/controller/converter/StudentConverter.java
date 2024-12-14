package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

@Component
public class StudentConverter {
  //受講生情報とコース情報が受講生ID順に並ぶようにソート
  public List<StudentDetail> convertStudentsDetails(List<Student> students,
      List<StudentCourse> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentsCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getStudentId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentsCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  //StudentDetailリストからStudentCourseのみ取り出してリスト化
  public List<StudentCourse> convertStudentsCourses(List<StudentDetail> studentDetails){
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentDetails.forEach(
        studentDetail -> studentCourseList.addAll(studentDetail.getStudentCourseList()));

    return studentCourseList;
  }
}
