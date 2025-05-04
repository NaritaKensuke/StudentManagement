package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @NotBlank
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
