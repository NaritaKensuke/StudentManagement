package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生基本情報")
@Getter
@Setter
public class Student {

  @NotBlank
  @Pattern(regexp = "^[0-9]{1,3}")
  private String studentId;

  @NotBlank
  private String name;

  @NotBlank
  private String nameReading;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String mailAddress;

  @NotBlank
  private String city;

  @NotNull
  private int age;

  @NotBlank
  private String gender;

  private String remark;

  private boolean deleted;

}
