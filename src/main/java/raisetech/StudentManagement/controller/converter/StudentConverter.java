package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.Arrays;
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

  //コース情報をコースIDでソート
  public List<StudentCourse> sortStudentsCoursesCourseId(List<StudentCourse> studentsCourses){
    List<String> courseIdList =
        new ArrayList<>(Arrays.asList("C1", "C2", "C3", "C4", "C5"));

    List<StudentCourse> sortStudentsCourses = new ArrayList<>();
    courseIdList.forEach(courseId -> {
      studentsCourses.stream().filter(studentCourse ->
          studentCourse.getCourseId().equals(courseId)).forEach(sortStudentsCourses::add);
    });
    return sortStudentsCourses;
  }

  public List<StudentCourse> getStudentCourseList(List<StudentDetail> studentDetails,
      String studentId){
    List<List<StudentCourse>> studentsCourseList = new ArrayList<>();
    for (StudentDetail studentDetail : studentDetails) {
      studentsCourseList.add(studentDetail.getStudentCourseList());
    }
    return studentsCourseList.get(Integer.parseInt(studentId)-1);
  }
}
