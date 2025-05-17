package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  StudentRepository sut;

  @ParameterizedTest
  @CsvSource({
      "false,3",
      "true,2"})
  void すべての受講生の基本情報一覧検索ができること(boolean deleted, int size) {
    List<Student> actual = sut.searchStudentList(deleted);

    assertThat(actual.size()).isEqualTo(size);
  }

  @ParameterizedTest
  @CsvSource({
      "false,6",
      "true,3"})
  void すべての受講生のコース情報一覧検索ができること(boolean deleted, int size) {
    List<StudentCourse> actual = sut.searchAllStudentCourseList(deleted);

    assertThat(actual.size()).isEqualTo(size);
  }

  @ParameterizedTest
  @CsvSource({"1,1", "2,2", "3,3", "4,1", "5,2"})
  void 指定の受講生のコース情報一覧検索ができること(String studentId, int size) {
    List<StudentCourse> actual = sut.searchStudentCourseList(studentId);

    assertThat(actual.size()).isEqualTo(size);
  }

  @Test
  void 単一のコース情報検索ができること() {
    StudentCourse expected = new StudentCourse(
        "1", "C1", "Java", LocalDate.of(2024, 8, 23),
        LocalDate.of(2025, 2, 23), "1", false);

    StudentCourse actual = sut.searchStudentCourse(expected);

    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"Java,C1", "英会話,C2", "デザイン,C3", "Phython,C4", "AWS,C5", "マーケティング,C6"})
  void 指定のコースIDとコース名情報を検索できること(String courseName, String courseId) {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName(courseName);

    StudentCourse actual = sut.searchCourseName(studentCourse);

    assertThat(actual.getCourseId()).isEqualTo(courseId);
  }

  @Test
  void 受講生の基本情報を登録できること() {
    Student student =
        new Student("", "石橋敬之", "いしばしのりゆき", "のり",
            "ishibasshi@sample.jp", "愛知県豊橋市", 21, "男", "", false);

    sut.insertStudent(student);
    List<Student> actual = sut.searchStudentList(false);

    student.setStudentId("6");

    assertThat(actual.size()).isEqualTo(4);
    assertThat(actual.getLast()).isEqualTo(student);
  }

  @Test
  void 受講生のコース情報を登録できること() {
    StudentCourse studentCourse =
        new StudentCourse("1", "C2", "英会話",
            LocalDate.now(), LocalDate.now().plusMonths(6), "", false);

    sut.insertStudentCourse(studentCourse);
    List<StudentCourse> actual = sut.searchAllStudentCourseList(false);

    studentCourse.setCourseDetailId("10");

    assertThat(actual.size()).isEqualTo(7);
    assertThat(actual.get(3)).isEqualTo(studentCourse);
  }

  @Test
  void 受講生の基本情報更新ができること() {
    Student student = new Student("1", "山田太郎", "やまだたろう", "たろぴ",
        "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false);

    sut.updateStudent(student);
    List<Student> actual = sut.searchStudentList(false);

    assertThat(actual.getFirst()).isEqualTo(student);
  }

  @Test
  void 受講生のコース情報更新ができること() {
    StudentCourse studentCourse =
        new StudentCourse("1", "C1", "Java",
            LocalDate.of(2024, 8, 23), LocalDate.of(2025, 3, 23),
            "1", false);

    sut.updateStudentCourse(studentCourse);
    StudentCourse actual = sut.searchStudentCourse(studentCourse);

    assertThat(actual).isEqualTo(studentCourse);
  }

}