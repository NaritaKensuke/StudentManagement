package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "コースの申込状況")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseState {

  @NotBlank
  @Pattern(regexp = "^[0-9]{1,3}")
  private String stateId;

  @NotBlank
  @Pattern(regexp = "^[0-9]{1,3}")
  private String courseDetailId;

  @NotBlank
  @Size(min = 3, max = 4)
  @Pattern(regexp = "\\p{IsHan}+$")
  private String state;
}
