package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  @NotBlank
  private String studentId;

  @NotBlank
  private String courseId;

  @NotBlank
  private String courseName;

  @NotNull
  private LocalDate startedDate;

  @NotNull
  private LocalDate finishDate;

  @NotBlank
  private String courseDetailId;

  private boolean deleted;

}