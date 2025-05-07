package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private String studentId;

  private String courseId;

  private String courseName;

  @NotNull
  private LocalDate startedDate;

  @NotNull
  private LocalDate finishDate;

  private String courseDetailId;

  private boolean deleted;

}