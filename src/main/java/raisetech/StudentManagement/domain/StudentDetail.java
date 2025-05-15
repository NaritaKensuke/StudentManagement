package raisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Schema(description = "受講生情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourse> studentCourseList;

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StudentDetail)) {
      return false;
    }

    StudentDetail studentDetail = (StudentDetail) obj;
    return studentDetail.getStudent() == this.getStudent()
        && studentDetail.getStudentCourseList() == this.getStudentCourseList();
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, studentCourseList);
  }
}
