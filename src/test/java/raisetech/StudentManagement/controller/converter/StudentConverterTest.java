package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  private StudentConverter sut;

  @Test
  void 受講生に紐づくコース情報をマッピング_正常にマッピングできているか確認する() {
    sut = new StudentConverter();

    Student testStudent1 = new Student("999", "山田太郎", "やまだたろう", "たろう",
        "testmail@sample.jp", "東京都千代田区", 20, "男", "", false);
    Student testStudent2 = new Student("998", "山田太郎", "やまだたろう", "たろう",
        "testmail@sample.jp", "東京都千代田区", 20, "男", "", false);

    StudentCourse testStudentCourse1 = new StudentCourse(
        "999", "C9", "テストコース"
        , LocalDate.now(), LocalDate.now().plusMonths(6), "999", false);
    StudentCourse testStudentCourse2 = new StudentCourse(
        "998", "C9", "テストコース"
        , LocalDate.now(), LocalDate.now().plusMonths(6), "998", false);
    StudentCourse testStudentCourse3 = new StudentCourse(
        "998", "C9", "テストコース"
        , LocalDate.now(), LocalDate.now().plusMonths(6), "997", false);

    List<Student> studentList = List.of(testStudent1, testStudent2);
    List<StudentCourse> studentCourseList =
        List.of(testStudentCourse1, testStudentCourse2, testStudentCourse3);
    List<StudentCourse> studentCourseList1 = List.of(testStudentCourse1);
    List<StudentCourse> studentCourseList2 = List.of(testStudentCourse2, testStudentCourse3);

    StudentDetail expected1 = new StudentDetail(testStudent1, studentCourseList1);
    StudentDetail expected2 = new StudentDetail(testStudent2, studentCourseList2);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.getFirst().getStudentCourseList()).isEqualTo(
        expected1.getStudentCourseList());
    assertThat(actual.get(1).getStudentCourseList()).isEqualTo(expected2.getStudentCourseList());
  }

}