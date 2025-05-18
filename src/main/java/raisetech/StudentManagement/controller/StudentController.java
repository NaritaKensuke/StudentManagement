package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseState;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるController
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  /**
   * すべての受講生の基本情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生リストもしくはfalseの受講生リストを返す
   */
  @Operation(summary = "受講生一覧", description = "すべての受講生の基本情報一覧を検索する")
  @Parameter(description = "論理削除")
  @GetMapping("/studentList")
  public List<Student> getStudentList(@RequestParam("deleted") boolean deleted) {
    return service.searchStudentList(deleted);
  }

  /**
   * 指定した条件で受講生の基本情報を一覧検索する
   *
   * @param filter 条件名を受け取る
   * @param value  条件の値を受け取る
   * @return 検索した受講生の基本情報一覧を返す
   */
  @Operation(summary = "受講生一覧", description = "指定した条件で受講生の基本情報一覧を検索する")
  @Parameter(description = "条件名")
  @Parameter(description = "条件の値")
  @GetMapping("/filterStudentList")
  public List<Student> getFilterStudentList(@RequestParam String filter,
      @RequestParam String value) {
    return service.searchFilterStudentList(filter, value);
  }

  /**
   * 未使用のAPI エラー文を返す
   *
   * @throws TestException エラーを返す
   */
  @Operation(summary = "未使用", description = "未使用のAPI")
  @ApiResponse(responseCode = "400", description = "未使用のAPI")
  @GetMapping("/students")
  public List<Student> getStudents() throws TestException {
    throw new TestException("こちらのAPIは現在使用することができません。URLをご確認ください。");
  }

  /**
   * すべての受講生のコース情報一覧を検索する
   *
   * @param deleted 論理削除の情報を受け取る
   * @return 論理削除がtrueの受講生コースリストもしくはfalseの受講生コースリストを返す
   */
  @Operation(summary = "コース情報一覧", description = "すべての受講生のコース情報一覧を検索する")
  @Parameter(description = "論理削除")
  @GetMapping("/allStudentCourseList")
  public List<StudentCourse> getAllStudentsCourseList(
      @RequestParam("deleted") boolean deleted) {
    return service.searchAllStudentCourseList(deleted);
  }

  /**
   * 特定の受講生のコース情報を検索する
   *
   * @param studentId 受講生IDの情報を受け取る
   * @return 受け取った受講生IDを持つ受講生コース情報を返す
   */
  @Operation(summary = "受講生コース情報一覧", description = "特定の受講生のコース情報一覧を検索する")
  @Parameter(description = "受講生ID")
  @ApiResponse(responseCode = "200", description = "成功")
  @ApiResponse(responseCode = "400", description = "クエリパラメータの入力エラー")
  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList(
      @RequestParam("studentId") @Size(min = 1, max = 3) @Pattern(regexp = "^\\d+$") String studentId) {
    return service.searchStudentCourseList(studentId);
  }

  /**
   * すべてのコースの申込状況一覧を検索する
   *
   * @return 申込状況リストを返す
   */
  @Operation(summary = "申込状況一覧", description = "すべてのコースの申込状況一覧を検索する")
  @GetMapping("/courseStateList")
  public List<CourseState> getCourseState() {
    return service.searchCourseStateList();
  }

  /**
   * 指定のコースの申込状況を検索する
   *
   * @param courseDetailId コース情報固有ID
   * @return 検索した申込状況を返す
   */
  @Operation(summary = "申込状況", description = "指定のコースの申込状況を検索する")
  @Parameter(description = "コース情報固有ID")
  @ApiResponse(responseCode = "200", description = "成功")
  @ApiResponse(responseCode = "400", description = "クエリパラメータの入力エラーです")
  @GetMapping("/courseState")
  public CourseState courseState(@RequestParam("courseDetailId") @Pattern(regexp = "^\\d+$")
  String courseDetailId) {
    return service.searchCourseState(courseDetailId);
  }

  /**
   * 受講生情報を登録する
   *
   * @param studentDetail 登録する受講生情報を受け取る
   * @return 正常に処理された場合、論理削除がfalseの受講生情報リストを返す
   */
  @Operation(summary = "受講生登録", description = "受講生情報を登録する")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "受講生情報")
  @PostMapping("/registerStudent")
  public ResponseEntity<List> registerStudent(
      @RequestBody StudentDetail studentDetail) {
    service.registerStudent(studentDetail);
    return ResponseEntity.ok(service.searchStudentList(false));
  }

  /**
   * 受講生の基本情報を更新する
   *
   * @param studentDetail 更新する受講生の基本情報を受け取る
   * @return 正常に処理された場合、更新した受講生の基本情報を返す
   */
  @Operation(summary = "受講生基本情報更新", description = "受講生の基本情報を更新する")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "更新する受講生情報")
  @PutMapping("/updateStudent")
  public ResponseEntity<Student> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail.getStudent());
  }

  /**
   * 受講生のコース情報を更新する
   *
   * @param studentDetail 更新する受講生のコース情報を受け取る
   * @return 正常に処理された場合、更新した受講生のコース情報を受け取る
   */
  @Operation(summary = "受講生コース情報更新", description = "受講生のコース情報を更新する")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "更新する受講生情報")
  @ApiResponse(responseCode = "200", description = "成功")
  @ApiResponse(responseCode = "400", description = "更新情報の不足")
  @PutMapping("/updateStudentCourse")
  public StudentCourse updateStudentCourse(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudentCourse(studentDetail);
    return service.searchStudentCourse(studentDetail);
  }

  /**
   * コースの申込状況を更新する
   *
   * @param courseState 更新する申込状況を受け取る
   * @return 正常に処理された場合、更新した受講背のコース情報を受け取る
   */
  @Operation(summary = "申し込み状況更新")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "更新する申込状況")
  @ApiResponse(responseCode = "200", description = "成功")
  @ApiResponse(responseCode = "400", description = "更新情報の入力エラーです")
  @PutMapping("/updateCourseState")
  public CourseState updateCourseState(@RequestBody @Valid CourseState courseState) {
    service.updateCourseState(courseState);
    return service.searchCourseState(courseState.getCourseDetailId());
  }

}
