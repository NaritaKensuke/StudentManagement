package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentCourse {

  @NotBlank
  @Pattern(regexp = "^[0-9]{1,3}")
  private String studentId;

  @NotBlank
  @Pattern(regexp = "^C+[0-9]")
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