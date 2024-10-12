package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class
StudentManagementApplication {

	private String name = "Narita Kensuke";
	private String age = "24";

	private Map<String, String> students = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo(){
		return name + " " + age + "歳";
	}

	@GetMapping("/studentsInfo")
	public Map getStudentsInfo(){
		return students;
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age){
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void updateStudentName(String name){
		this.name = name;
	}

	@PostMapping("/studentAge")
	public void updateStudentAge(String age){
		this.name = age;
	}

	@PostMapping("/studentsInfo")
	public void setStudentsInfo(String name, String age){
		this.students.put(name,age);
	}

	@PostMapping("/updateStudentName")
	public void updateStudentName(String beforename,String aftername){
		String studentname = this.students.get(beforename);
		this.students.remove(beforename);
		this.students.put(aftername,studentname);
	}

}
