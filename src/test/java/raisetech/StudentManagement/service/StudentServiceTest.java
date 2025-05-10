package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository);
  }

  void setStudentDetail(StudentDetail studentDetail){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);
    studentDetail.setStudentCourseList(studentCourseList);
    studentDetail.setStudent(student);
  }

  void setStudentCourseList(StudentDetail studentDetail){
    StudentCourse studentCourse1 = new StudentCourse();
    StudentCourse studentCourse2 = new StudentCourse();
    studentDetail.getStudentCourseList().add(studentCourse1);
    studentDetail.getStudentCourseList().add(studentCourse2);
  }

  @Test
  void すべての受講生の基本情報一覧検索_false_リポジトリの処理_戻り値(){
    boolean deleted = false;
    List<Student> expected = new ArrayList<>();

    List<Student> actual = sut.searchStudentList(deleted);

    verify(repository, times(1)).searchStudentList(deleted);
    assertEquals(expected,actual);
  }

  @Test
  void すべての受講生の基本情報一覧検索_true_リポジトリの処理_戻り値(){
    boolean deleted = true;
    List<Student> expected = new ArrayList<>();

    List<Student> actual = sut.searchStudentList(deleted);

    verify(repository,times(1)).searchStudentList(deleted);
    assertEquals(expected,actual);
  }

  @Test
  void すべての受講生のコース情報一覧検索_false_リポジトリの処理_戻り値(){
    boolean deleted = false;
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.searchAllStudentCourseList(deleted);

    verify(repository,times(1)).searchAllStudentCourseList(deleted);
    assertEquals(expected,actual);
  }

  @Test
  void すべての受講生のコース情報一覧検索_true_リポジトリの処理_戻り値(){
    boolean deleted = true;
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.searchAllStudentCourseList(deleted);

    verify(repository,times(1)).searchAllStudentCourseList(deleted);
    assertEquals(expected,actual);
  }

  @Test
  void 指定の受講生のコース情報一覧検索_リポジトリの処理_戻り値(){
    String studentId = "テストID";
    List<StudentCourse> expected = new ArrayList<>();

    List<StudentCourse> actual = sut.searchStudentCourseList(studentId);

    verify(repository,times(1)).searchStudentCourseList(studentId);
    assertEquals(expected,actual);
  }

  @Test
  void 単一の受講生コース情報検索_リポジトリの処理_戻り値(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);
    StudentCourse expected = new StudentCourse();
    StudentCourse studentCourse = new StudentCourse();
    when(repository.searchStudentCourse(studentDetail.getStudentCourseList().getFirst()))
        .thenReturn(expected);

    StudentCourse actual = sut.searchStudentCourse(studentDetail);

    verify(repository,times(1))
        .searchStudentCourse(studentDetail.getStudentCourseList().getFirst());
    assertEquals(expected,actual);
  }

  @Test
  void 受講生情報の登録_リポジトリの処理が適切に呼び出せていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);

    List<Student> studentList = new ArrayList<>();
    studentList.add(studentDetail.getStudent());
    when(repository.searchStudentList(false)).thenReturn(studentList);

    when(repository
        .searchCourseName(any(StudentCourse.class)))
        .thenReturn(new StudentCourse());

    sut.registerStudent(studentDetail);

    verify(repository,times(1))
        .insertStudent(studentDetail.getStudent());
    verify(repository,times(1))
        .insertStudentCourse(studentDetail.getStudentCourseList().getFirst());
  }

  @Test
  void 受講生情報の登録_ループ処理が適切に実行されていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);
    setStudentCourseList(studentDetail);

    List<Student> studentList = new ArrayList<>();
    studentList.add(studentDetail.getStudent());
    when(repository.searchStudentList(false)).thenReturn(studentList);

    when(repository.searchCourseName(any(StudentCourse.class)))
        .thenReturn(new StudentCourse());

    sut.registerStudent(studentDetail);

    verify(repository,times(studentDetail.getStudentCourseList().size()))
        .insertStudentCourse(any(StudentCourse.class));
  }

  @Test
  void コースID_開始日_終了日の自動登録_リポジトリの処理_適切な自動登録(){
    Student student = new Student();
    List<Student> studentList = new ArrayList<>();
    studentList.add(student);
    StudentCourse studentCourse = new StudentCourse();
    String testCourseId = "テストID";
    studentCourse.setCourseId(testCourseId);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);

    when(repository.searchStudentList(false)).thenReturn(studentList);
    when(repository.searchCourseName(studentCourse)).thenReturn(studentCourse);

    sut.setCourseDetail(studentCourse);

    verify(repository,times(1)).searchStudentList(false);
    verify(repository,times(1))
        .searchCourseName(studentCourse);
    assertEquals(testCourseId,studentCourse.getCourseId());
    assertEquals(LocalDate.now(),
        studentCourse.getStartedDate());
    assertEquals(LocalDate.now().plusMonths(6),
        studentCourse.getFinishDate());
  }

  @Test
  void 受講生の基本情報更新_リポジトリの処理が適切に呼び出せていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);

    sut.updateStudent(studentDetail);

    verify(repository,times(1))
        .updateStudent(studentDetail.getStudent());
  }

  @Test
  void 受講生の基本情報更新_ループ処理が適切に実行されていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);
    setStudentCourseList(studentDetail);
    studentDetail.getStudent().setDeleted(true);
    when(repository.searchStudentCourseList(null))
        .thenReturn(studentDetail.getStudentCourseList());

    sut.updateStudent(studentDetail);

    verify(repository,times(studentDetail.getStudentCourseList().size()))
        .updateStudentCourse(any(StudentCourse.class));
  }

  @Test
  void 受講生のコース情報更新_リポジトリの処理が適切に呼び出せていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);
    when(repository
        .searchCourseName(any(StudentCourse.class)))
        .thenReturn(new StudentCourse());

    sut.updateStudentCourse(studentDetail);

    verify(repository,times(1))
        .searchCourseName(studentDetail.getStudentCourseList().getFirst());
    verify(repository,times(1))
        .updateStudentCourse(studentDetail.getStudentCourseList().getFirst());
  }

  @Test
  void 受講生のコース情報更新_ループ処理が適切に実行されていること(){
    StudentDetail studentDetail = new StudentDetail();
    setStudentDetail(studentDetail);
    setStudentCourseList(studentDetail);
    when(repository.searchCourseName(any(StudentCourse.class)))
        .thenReturn(new StudentCourse());

    sut.updateStudentCourse(studentDetail);

    verify(repository,times(studentDetail.getStudentCourseList().size()))
        .searchCourseName(any(StudentCourse.class));
    verify(repository,times(studentDetail.getStudentCourseList().size()))
        .updateStudentCourse(any(StudentCourse.class));
  }

}