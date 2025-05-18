package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseState;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  @MockBean
  private StudentConverter converter;

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @ParameterizedTest
  @ValueSource(strings = {"false", "true"})
  void すべての受講生の基本情報一覧検索_正常に実行できること(boolean deleted)
      throws Exception {
    mockMvc.perform(get("/studentList")
            .param("deleted", String.valueOf(deleted)))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList(deleted);
  }

  @ParameterizedTest
  @CsvSource({"受講生ID,999", "なまえ,テスト", "性別,男", "年齢,999"})
  void 指定した条件で受講生の基本情報を一覧検索_正常に実行できること(String filter, String value)
      throws Exception {
    mockMvc.perform(get("/filterStudentList")
            .param("filter", filter)
            .param("value", value))
        .andExpect(status().isOk());

    verify(service, times(1)).searchFilterStudentList(filter, value);
  }

  @ParameterizedTest
  @ValueSource(strings = {"false", "true"})
  void すべての受講生のコース情報一覧検索_正常に実行できること(boolean deleted)
      throws Exception {
    mockMvc.perform(get("/allStudentCourseList")
            .param("deleted", String.valueOf(deleted)))
        .andExpect(status().isOk());

    verify(service, times(1)).searchAllStudentCourseList(deleted);
  }

  @Test
  void 特定の受講生のコース情報一覧検索_正常に実行できること() throws Exception {
    String studentId = "999";
    mockMvc.perform(get("/studentCourseList")
            .param("studentId", studentId))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentCourseList(studentId);
  }

  @Test
  void 特定の受講生のコース情報一覧検索_クエリパラメータを数字以外で指定した時に400エラーとなること()
      throws Exception {
    mockMvc.perform(get("/studentCourseList?studentId=テストID"))
        .andExpect(status().is(400));
  }

  @Test
  void すべてのコースの申込状況一覧検索_正常に実行できること() throws Exception {
    mockMvc.perform(get("/courseStateList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchCourseStateList();
  }

  @Test
  void 単一のコースの申込状況検索_正常に実行できること() throws Exception {
    String courseDetailId = "999";

    mockMvc.perform(get("/courseState")
            .param("courseDetailId", courseDetailId))
        .andExpect(status().isOk());

    verify(service, times(1)).searchCourseState(courseDetailId);
  }

  @Test
  void 単一のコースの申し込み状況検索_クエリパラメータを数字以外で指定した時に400エラーとなること()
      throws Exception {
    String courseDetailId = "テストID";

    mockMvc.perform(get("/courseState")
            .param("courseDetailId", courseDetailId))
        .andExpect(status().is(400));
  }

  @Test
  void 受講生情報の登録_正常に実行できること() throws Exception {
    String studentDetail = """
        {
            "student": {},
            "studentCourseList": []
        }
        """;
    mockMvc.perform(
            post("/registerStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentDetail))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any(StudentDetail.class));
    verify(service, times(1)).searchStudentList(false);
  }

  @Test
  void 受講生の基本情報更新_正常に実行できること() throws Exception {
    String studentDetailJson = """
        {
           "student" : {
                   "studentId" : "999",
                   "name" : "山田太郎",
                   "nameReading" : "山田太郎",
                   "nickname" : "たろう",
                   "mailAddress" : "testmail@sample.jp",
                   "city" : "東京都練馬区",
                   "age" : "23",
                   "gender" : "男",
                   "remark" : "",
                   "deleted" : "false"
           }
        }
        """;
    mockMvc.perform(
            put("/updateStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentDetailJson))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生の基本情報更新_入力エラーが正常に返ってくること() throws Exception {
    String studentDetailJson = """
        {
           "student" : {
                   "studentId" : "",
                   "name" : "山田太郎",
                   "nameReading" : "山田太郎",
                   "nickname" : "たろう",
                   "mailAddress" : "testmail@sample.jp",
                   "city" : "東京都練馬区",
                   "age" : "23",
                   "gender" : "男",
                   "remark" : "",
                   "deleted" : "false"
           }
        }
        """;
    mockMvc.perform(
            put("/updateStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentDetailJson))
        .andExpect(status().is(400));
  }

  @Test
  void 受講生のコース情報更新_正常に実行できていること() throws Exception {
    String studentDetailJson = """
        {
            "studentCourseList" : [
                {
                    "studentId": "999",
                    "courseId": "C0",
                    "courseName": "テスト",
                    "startedDate" : "2022-07-18",
                    "finishDate" : "2023-02-18",
                    "courseDetailId": 999,
                    "deleted": false
                }
            ]
        }
        """;
    mockMvc.perform(put("/updateStudentCourse")
            .contentType(MediaType.APPLICATION_JSON)
            .content(studentDetailJson))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudentCourse(any(StudentDetail.class));
    verify(service, times(1)).searchStudentCourse(any(StudentDetail.class));
  }

  @Test
  void コースの申込状況更新_正常に実行できていること() throws Exception {
    String courseStateJson = """
        {
          "stateId" : "1",
          "courseDetailId" : "1",
          "state" : "受講中"
        }
        """;
    mockMvc.perform(put("/updateCourseState")
            .contentType(MediaType.APPLICATION_JSON)
            .content(courseStateJson))
        .andExpect(status().isOk());

    verify(service, times(1)).updateCourseState(any(CourseState.class));
    verify(service, times(1)).searchCourseState(any(String.class));
  }

  @Test
  void 受講生情報の基本情報で受講生IDに数字以外を用いた場合に入力チェックにかかること() {
    Student student =
        new Student("テストID", "山田太郎", "やまだたろう",
            "たろう", "testmail@sample.com", "東京都千代田区", 99,
            "男", "", false);

    Set<ConstraintViolation<Student>> validations = validator.validate(student);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生の基本情報でメールアドレスにメールアドレスの形式以外を用いた場合に入力チェックにかかること() {
    Student student =
        new Student("999", "山田太郎", "やまだたろう",
            "たろう", "testmail.sample.com", "東京都千代田区", 99,
            "男", "", false);

    Set<ConstraintViolation<Student>> validations = validator.validate(student);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のコース情報で受講生IDに数字以外を用いた場合に入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse(
        "テストID", "C9", "テストコース"
        , LocalDate.now(), LocalDate.now().plusMonths(6), "999", false);

    Set<ConstraintViolation<StudentCourse>> validations = validator.validate(studentCourse);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のコース情報でコースIDに適する形式を用いなかった場合に入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse(
        "999", "A10", "テストコース"
        , LocalDate.now(), LocalDate.now().plusMonths(6), "999", false);

    Set<ConstraintViolation<StudentCourse>> validations = validator.validate(studentCourse);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void コースの申込状況で申し込み状況IDが空白の場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("", "999", "仮登録");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(2);
  }

  @Test
  void コースの申込状況で申し込み状況IDに数字以外を用いた場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("テストID", "999", "仮登録");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void コースの申込状況でコース情報固有IDが空白の場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("999", "", "仮登録");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(2);
  }

  @Test
  void コースの申込状況でコース情報固有IDに数字以外を用いた場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("999", "テストID", "仮登録");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void コースの申込状況で申込状況が空白の場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("999", "999", "");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(3);
  }

  @Test
  void コースの申込状況で申込状況に適する形式を用いなかった場合に入力チェックにかかること() {
    CourseState courseState = new CourseState("999", "999", "テスト状況");

    Set<ConstraintViolation<CourseState>> validations = validator.validate(courseState);

    assertThat(validations.size()).isEqualTo(2);
  }
}