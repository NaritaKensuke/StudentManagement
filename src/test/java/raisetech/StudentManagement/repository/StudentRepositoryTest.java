package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.CourseState;
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
  @ValueSource(strings = {"1", "2", "3", "4", "5"})
  void 指定の受講生IDの受講生基本情報一覧検索ができること(String studentId) {
    List<Student> studentList = new ArrayList<>();
    switch (studentId) {
      case "1" -> studentList.add(
          new Student("1", "山田太郎", "やまだたろう", "たろう",
              "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false));
      case "2" -> studentList.add(
          new Student("2", "田中翔太", "たなかしょうた", "しょう",
              "shota@sample.jp", "岐阜県恵那市", 30, "男", "", false)
      );
      case "3" -> studentList.add(
          new Student("3", "山本美咲", "やまもとみさき", "みさみさ",
              "misaki@sample.jp", "福岡県仙台市", 40, "女", "", false)
      );
      case "4" -> studentList.add(
          new Student("4", "佐藤健一", "さとうけんいち", "けんちゃん",
              "kennichi@sample.jp", "石川県金沢市", 20, "男", "", false)
      );
      case "5" -> studentList.add(
          new Student("5", "鈴木真理", "すずきまり", "まりこ",
              "mariko@sample.jp", "愛知県碧南市", 45, "その他", "", false)
      );
    }

    List<Student> actual = sut.searchStudentListWhereStudentId(studentId);

    assertThat(actual).isEqualTo(studentList);
  }

  @ParameterizedTest
  @ValueSource(strings = {"やまだたろう", "たなかしょうた", "やまもとみさき", "さとうけんいち",
      "すずきまり"})
  void 指定のふりがなの受講生基本情報一覧検索ができること(String nameReading) {
    List<Student> studentList = new ArrayList<>();
    switch (nameReading) {
      case "やまだたろう" -> studentList.add(
          new Student("1", "山田太郎", "やまだたろう", "たろう",
              "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false));
      case "たなかしょうた" -> studentList.add(
          new Student("2", "田中翔太", "たなかしょうた", "しょう",
              "shota@sample.jp", "岐阜県恵那市", 30, "男", "", false)
      );
      case "やまもとみさき" -> studentList.add(
          new Student("3", "山本美咲", "やまもとみさき", "みさみさ",
              "misaki@sample.jp", "福岡県仙台市", 40, "女", "", false)
      );
      case "さとうけんいち" -> studentList.add(
          new Student("4", "佐藤健一", "さとうけんいち", "けんちゃん",
              "kennichi@sample.jp", "石川県金沢市", 20, "男", "", false)
      );
      case "すずきまり" -> studentList.add(
          new Student("5", "鈴木真理", "すずきまり", "まりこ",
              "mariko@sample.jp", "愛知県碧南市", 45, "その他", "", false)
      );
    }

    List<Student> actual = sut.searchStudentListWhereNameReading(nameReading);

    assertThat(actual).isEqualTo(studentList);
  }

  @ParameterizedTest
  @ValueSource(strings = {"男", "女", "その他"})
  void 指定の性別の受講生基本情報一覧検索ができること(String gender) {
    List<Student> studentList = new ArrayList<>();
    switch (gender) {
      case "男" -> {
        studentList.add(
            new Student("1", "山田太郎", "やまだたろう", "たろう",
                "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false));
        studentList.add(
            new Student("2", "田中翔太", "たなかしょうた", "しょう",
                "shota@sample.jp", "岐阜県恵那市", 30, "男", "", false)
        );
        studentList.add(
            new Student("4", "佐藤健一", "さとうけんいち", "けんちゃん",
                "kennichi@sample.jp", "石川県金沢市", 20, "男", "", false)
        );
      }
      case "女" -> studentList.add(
          new Student("3", "山本美咲", "やまもとみさき", "みさみさ",
              "misaki@sample.jp", "福岡県仙台市", 40, "女", "", false)
      );
      case "その他" -> studentList.add(
          new Student("5", "鈴木真理", "すずきまり", "まりこ",
              "mariko@sample.jp", "愛知県碧南市", 45, "その他", "", false)
      );
    }

    List<Student> actual = sut.searchStudentListWhereGender(gender);

    assertThat(actual).isEqualTo(studentList);
  }

  @ParameterizedTest
  @ValueSource(strings = {"20", "30", "40"})
  void 指定の性別の受講生基本情報一覧検索ができること(int age) {
    List<Student> studentList = new ArrayList<>();
    switch (age) {
      case 20 -> {
        studentList.add(
            new Student("1", "山田太郎", "やまだたろう", "たろう",
                "taro@sample.jp", "愛知県名古屋市", 20, "男", "", false));
        studentList.add(
            new Student("4", "佐藤健一", "さとうけんいち", "けんちゃん",
                "kennichi@sample.jp", "石川県金沢市", 20, "男", "", false)
        );
      }
      case 30 -> studentList.add(
          new Student("2", "田中翔太", "たなかしょうた", "しょう",
              "shota@sample.jp", "岐阜県恵那市", 30, "男", "", false)
      );
      case 40 -> {
        studentList.add(
            new Student("3", "山本美咲", "やまもとみさき", "みさみさ",
                "misaki@sample.jp", "福岡県仙台市", 40, "女", "", false)
        );
        studentList.add(
            new Student("5", "鈴木真理", "すずきまり", "まりこ",
                "mariko@sample.jp", "愛知県碧南市", 45, "その他", "", false)
        );
      }
    }

    List<Student> actual = sut.searchStudentListWhereAge(age);

    assertThat(actual).isEqualTo(studentList);
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
  void すべてのコースの申込状況一覧を検索できること() {
    CourseState courseState = new CourseState("1", "1", "受講終了");
    CourseState courseState1 = new CourseState("2", "2", "受講終了");
    CourseState courseState2 = new CourseState("3", "3", "受講終了");
    CourseState courseState3 = new CourseState("4", "4", "受講終了");
    CourseState courseState4 = new CourseState("5", "5", "受講終了");
    CourseState courseState5 = new CourseState("6", "6", "受講終了");
    CourseState courseState6 = new CourseState("7", "7", "受講終了");
    CourseState courseState7 = new CourseState("8", "8", "受講終了");
    CourseState courseState8 = new CourseState("9", "9", "受講終了");
    List<CourseState> expected = List.of(courseState, courseState1, courseState2, courseState3,
        courseState4, courseState5, courseState6, courseState7, courseState8);

    List<CourseState> actual = sut.searchCourseStateList();

    assertThat(actual.size()).isEqualTo(9);
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void 単一のコースの申込状況を検索できること(String courseDetailId) {
    CourseState expected = new CourseState();
    switch (courseDetailId) {
      case "1" -> expected = new CourseState("1", "1", "受講終了");
      case "2" -> expected = new CourseState("2", "2", "受講終了");
      case "3" -> expected = new CourseState("3", "3", "受講終了");
      case "4" -> expected = new CourseState("4", "4", "受講終了");
      case "5" -> expected = new CourseState("5", "5", "受講終了");
      case "6" -> expected = new CourseState("6", "6", "受講終了");
      case "7" -> expected = new CourseState("7", "7", "受講終了");
      case "8" -> expected = new CourseState("8", "8", "受講終了");
      case "9" -> expected = new CourseState("9", "9", "受講終了");
    }

    CourseState actual = sut.searchCourseState(courseDetailId);

    assertThat(actual).isEqualTo(expected);
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

    sut.insertCourseState("10");
    studentCourse.setCourseDetailId("10");

    assertThat(actual.size()).isEqualTo(7);
    assertThat(actual.get(3)).isEqualTo(studentCourse);
  }

  @Test
  void コースの申込状況を登録できること() {
    StudentCourse studentCourse =
        new StudentCourse("1", "C2", "英会話",
            LocalDate.now(), LocalDate.now().plusMonths(6), "", false);
    CourseState expected = new CourseState("11", "11", "仮登録");
    sut.insertStudentCourse(studentCourse);

    sut.insertCourseState(expected.getCourseDetailId());
    CourseState actual = sut.searchCourseState(expected.getCourseDetailId());

    assertThat(actual.getStateId()).isEqualTo(expected.getStateId());
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

  @Test
  void コースの申込状況更新ができること() {
    CourseState courseState =
        new CourseState("1", "1", "受講中");

    sut.updateCourseState(courseState);
    CourseState actual = sut.searchCourseState(courseState.getCourseDetailId());

    assertThat(actual).isEqualTo(courseState);
  }

}