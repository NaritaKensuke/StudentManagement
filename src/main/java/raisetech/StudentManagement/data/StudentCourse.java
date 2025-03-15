package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private String studentId;
  private String courseId;
  private String courseName;
  private LocalDate startedDate;
  private LocalDate finishDate;
  private int courseDetailId;
  private boolean delete;

}
