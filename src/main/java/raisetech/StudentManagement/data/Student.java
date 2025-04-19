package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

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
