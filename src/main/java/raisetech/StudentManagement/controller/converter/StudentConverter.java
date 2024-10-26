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
  public List<StudentDetail> convertStudentDetails(List<Student> students,
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

  public List<StudentCourse> convertStudentCourse(List<StudentDetail> studentDetails){
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentDetails.forEach(
        studentDetail -> studentDetail.getStudentCourseList().forEach(studentCourseList::add));
    return studentCourseList;
  }
}
