package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生基本情報")
@Getter
@Setter
public class Student {

  private String studentId;

  private String name;

  private String nameReading;

  private String nickname;

  private String mailAddress;

  private String city;

  private int age;

  private String gender;

  private String remark;

  private boolean deleted;

}
