package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
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

    List<Student> expected = new ArrayList<>();
    if (!deleted) {
      Student student = new Student("1", "山田太郎", "やまだたろう", "たろう",
          "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false);
      Student student1 = new Student("2", "田中翔太", "たなかしょうた", "しょう",
          "shota@sample.jp", "岐阜県恵那市", 30, "男", "", false);
      Student student2 = new Student("3", "山本美咲", "やまもとみさき", "みさみさ",
          "misaki@sample.jp", "福岡県仙台市", 40, "女", "", false);
      expected.add(student);
      expected.add(student1);
      expected.add(student2);
    } else {
      Student student = new Student("4", "佐藤健一", "さとうけんいち", "けんちゃん",
          "kennichi@sample.jp", "石川県金沢市", 20, "男", "", false);
      Student student1 = new Student("5", "鈴木真理", "すずきまり", "まりこ",
          "mariko@sample.jp", "愛知県碧南市", 45, "その他", "", false);
      expected.add(student);
      expected.add(student1);
    }

    assertThat(actual.size()).isEqualTo(size);
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "false,6",
      "true,3"})
  void すべての受講生のコース情報一覧検索ができること(boolean deleted, int size) {
    List<StudentCourse> expected = new ArrayList<>();
    if (!deleted) {
      StudentCourse studentCourse = new StudentCourse("1", "C1", "Java",
          LocalDate.of(2024, 8, 23), LocalDate.of(2025, 2, 23)
          , "1", false);
      StudentCourse studentCourse1 = new StudentCourse("2", "C1", "Java",
          LocalDate.of(2023, 6, 6), LocalDate.of(2023, 12, 6),
          "2", false);
      StudentCourse studentCourse2 = new StudentCourse("3", "C3", "デザイン",
          LocalDate.of(2022, 6, 20), LocalDate.of(2022, 12, 20),
          "4", false);
      StudentCourse studentCourse3 = new StudentCourse("2", "C3", "デザイン",
          LocalDate.of(2023, 8, 30), LocalDate.of(2024, 2, 29),
          "6", false);
      StudentCourse studentCourse4 = new StudentCourse("3", "C2", "英会話",
          LocalDate.of(2023, 5, 15), LocalDate.of(2023, 11, 15),
          "7", false);
      StudentCourse studentCourse5 = new StudentCourse("3", "C6", "マーケティング",
          LocalDate.of(2024, 6, 11), LocalDate.of(2024, 12, 11),
          "8", false);
      expected.add(studentCourse);
      expected.add(studentCourse1);
      expected.add(studentCourse4);
      expected.add(studentCourse2);
      expected.add(studentCourse3);
      expected.add(studentCourse5);
    } else {
      StudentCourse studentCourse = new StudentCourse("4", "C4", "Phython",
          LocalDate.of(2024, 4, 17), LocalDate.of(2024, 10, 17),
          "3", false);
      StudentCourse studentCourse1 = new StudentCourse("5", "C5", "AWS",
          LocalDate.of(2024, 8, 25), LocalDate.of(2025, 2, 25),
          "5", false);
      StudentCourse studentCourse2 = new StudentCourse("5", "C2", "英会話",
          LocalDate.of(2022, 7, 18), LocalDate.of(2023, 2, 18),
          "9", false);
      expected.add(studentCourse2);
      expected.add(studentCourse);
      expected.add(studentCourse1);
    }

    List<StudentCourse> actual = sut.searchAllStudentCourseList(deleted);

    assertThat(actual.size()).isEqualTo(size);
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"1,1", "2,2", "3,3", "4,1", "5,2"})
  void 指定の受講生のコース情報一覧検索ができること(String studentId, int size) {
    List<StudentCourse> expected = new ArrayList<>();
    switch (studentId) {
      case "1" -> {
        StudentCourse studentCourse = new StudentCourse("1", "C1", "Java",
            LocalDate.of(2024, 8, 23), LocalDate.of(2025, 2, 23)
            , "1", false);
        expected.add(studentCourse);
      }
      case "2" -> {
        StudentCourse studentCourse = new StudentCourse("2", "C1", "Java",
            LocalDate.of(2023, 6, 6), LocalDate.of(2023, 12, 6),
            "2", false);
        StudentCourse studentCourse1 = new StudentCourse("2", "C3", "デザイン",
            LocalDate.of(2023, 8, 30), LocalDate.of(2024, 2, 29),
            "6", false);
        expected.add(studentCourse);
        expected.add(studentCourse1);
      }
      case "3" -> {
        StudentCourse studentCourse = new StudentCourse("3", "C3", "デザイン",
            LocalDate.of(2022, 6, 20), LocalDate.of(2022, 12, 20),
            "4", false);
        StudentCourse studentCourse1 = new StudentCourse("3", "C2", "英会話",
            LocalDate.of(2023, 5, 15), LocalDate.of(2023, 11, 15),
            "7", false);
        StudentCourse studentCourse2 = new StudentCourse("3", "C6", "マーケティング",
            LocalDate.of(2024, 6, 11), LocalDate.of(2024, 12, 11),
            "8", false);
        expected.add(studentCourse);
        expected.add(studentCourse1);
        expected.add(studentCourse2);
      }
      case "4" -> {
        StudentCourse studentCourse = new StudentCourse("4", "C4", "Phython",
            LocalDate.of(2024, 4, 17), LocalDate.of(2024, 10, 17),
            "3", false);
        expected.add(studentCourse);
      }
      case "5" -> {
        StudentCourse studentCourse = new StudentCourse("5", "C5", "AWS",
            LocalDate.of(2024, 8, 25), LocalDate.of(2025, 2, 25),
            "5", false);
        StudentCourse studentCourse1 = new StudentCourse("5", "C2", "英会話",
            LocalDate.of(2022, 7, 18), LocalDate.of(2023, 2, 18),
            "9", false);
        expected.add(studentCourse);
        expected.add(studentCourse1);
      }
    }
    List<StudentCourse> actual = sut.searchStudentCourseList(studentId);

    assertThat(actual.size()).isEqualTo(size);
    assertThat(actual).isEqualTo(expected);
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