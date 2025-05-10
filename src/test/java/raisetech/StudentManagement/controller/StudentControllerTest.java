package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.controller.converter.StudentConverter;
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

  @Test
  void すべての受講生の基本情報一覧検索_false_正常に実行できること() throws Exception {
    mockMvc.perform(get("/studentList?deleted=false"))
        .andExpect(status().isOk());
    boolean deleted = false;

    verify(service, times(1)).searchStudentList(deleted);
  }

  @Test
  void すべての受講生の基本情報一覧検索_false_受講生基本情報のリストが返ってくること() {
    boolean deleted = false;
    StudentController sut = new StudentController(service, converter);
    List<Student> expected = new ArrayList<>();

    List<Student> actual = sut.getStudentList(deleted);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void すべての受講生の基本情報一覧検索_true_正常に実行できることと() throws Exception {
    mockMvc.perform(get("/studentList?deleted=true"))
        .andExpect(status().isOk());
    boolean deleted = true;

    verify(service, times(1)).searchStudentList(deleted);
  }

  @Test
  void すべての受講生の基本情報一覧検索_true_受講生基本情報のリストが返ってくること() {
    boolean deleted = true;
    StudentController sut = new StudentController(service, converter);
    List<Student> expected = new ArrayList<>();

    List<Student> actual = sut.getStudentList(deleted);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void すべての受講生のコース情報一覧検索_false_正常に実行できること() throws Exception {
    boolean deleted = false;
    mockMvc.perform(get("/allStudentCourseList?deleted=false"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchAllStudentCourseList(deleted);
  }

  @Test
  void すべての受講生のコース情報一覧検索_false_受講生コース情報のリストが返ってくること() {
    StudentController sut = new StudentController(service, converter);
    boolean deleted = false;
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.getAllStudentsCourseList(deleted);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void すべての受講生のコース情報一覧検索_true_正常に実行できること() throws Exception {
    boolean deleted = true;
    mockMvc.perform(get("/allStudentCourseList?deleted=true"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchAllStudentCourseList(deleted);
  }

  @Test
  void すべての受講生のコース情報一覧検索_true_受講生コース情報のリストが返ってくること() {
    StudentController sut = new StudentController(service, converter);
    boolean deleted = true;
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.getAllStudentsCourseList(deleted);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 特定の受講生のコース情報一覧検索_正常に実行できること() throws Exception {
    String studentId = "999";
    mockMvc.perform(get("/studentCourseList?studentId=999"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentCourseList(studentId);
  }

  @Test
  void 特定の受講生のコース情報一覧検索_受講生コース情報のリストが返ってくること() {
    StudentController sut = new StudentController(service, converter);
    String studentId = "999";
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.getStudentCourseList(studentId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 特定の受講生のコース情報一覧検索_クエリパラメータを数字以外で指定した時に400エラーとなること()
      throws Exception {
    mockMvc.perform(get("/studentCourseList?studentId=テストID"))
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
  void 受講生の登録_リストのレスポンスエンティティが返ってくること() {
    StudentController sut = new StudentController(service, converter);
    ResponseEntity<List> expected = ResponseEntity.ok(new ArrayList<Student>());

    ResponseEntity<List> actual = sut.registerStudent(new StudentDetail());

    assertThat(actual).isEqualTo(expected);
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
  void 受講生の基本情報更新_受講生の基本情報オブジェクトのレスポンスエンティティが返ってくること() {
    StudentController sut = new StudentController(service, converter);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    ResponseEntity<Student> expected = ResponseEntity.ok(studentDetail.getStudent());

    ResponseEntity<Student> actual = sut.updateStudent(studentDetail);

    assertThat(actual).isEqualTo(expected);
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
  void 受講生のコース情報更新_正常に戻り値が返ってくること() {
    StudentController sut = new StudentController(service, converter);
    StudentDetail studentDetail = new StudentDetail();
    StudentCourse expected = new StudentCourse();
    when(service.searchStudentCourse(studentDetail)).thenReturn(expected);

    StudentCourse actual = sut.updateStudentCourse(studentDetail);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生情報の基本情報で受講生IDに数字以外を用いた場合に入力チェックにかかること() {
    Student student = new Student();
    student.setStudentId("テストID");
    student.setName("山田太郎");
    student.setNameReading("やまだたろう");
    student.setNickname("たろう");
    student.setMailAddress("testmail@sample.com");
    student.setCity("東京都千代田区");
    student.setAge(99);
    student.setGender("男");

    Set<ConstraintViolation<Student>> validations = validator.validate(student);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生の基本情報でメールアドレスにメールアドレスの形式以外を用いた場合に入力チェックにかかること() {
    Student student = new Student();
    student.setStudentId("999");
    student.setName("山田太郎");
    student.setNameReading("やまだたろう");
    student.setNickname("たろう");
    student.setMailAddress("testmail.sample.com");
    student.setCity("東京都千代田区");
    student.setAge(99);
    student.setGender("男");

    Set<ConstraintViolation<Student>> validations = validator.validate(student);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のコース情報で受講生IDに数字以外を用いた場合に入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("テストID");
    studentCourse.setCourseId("C9");
    studentCourse.setCourseName("テストコース");
    studentCourse.setStartedDate(LocalDate.now());
    studentCourse.setFinishDate(LocalDate.now().plusMonths(6));
    studentCourse.setCourseDetailId("999");

    Set<ConstraintViolation<StudentCourse>> validations = validator.validate(studentCourse);

    assertThat(validations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のコース情報でコースIDに適する形式を用いなかった場合に入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("999");
    studentCourse.setCourseId("A9");
    studentCourse.setCourseName("テストコース");
    studentCourse.setStartedDate(LocalDate.now());
    studentCourse.setFinishDate(LocalDate.now().plusMonths(6));
    studentCourse.setCourseDetailId("999");

    Set<ConstraintViolation<StudentCourse>> validations = validator.validate(studentCourse);

    assertThat(validations.size()).isEqualTo(1);
  }

}