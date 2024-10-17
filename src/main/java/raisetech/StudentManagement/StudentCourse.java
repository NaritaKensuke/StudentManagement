package raisetech.StudentManagement;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private String studentId;
  private String courseId;
  private String courseName;
  private Date startedDate;
  private Date finishDate;

}
