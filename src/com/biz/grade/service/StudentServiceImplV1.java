package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.config.DBContract;
import com.biz.grade.config.DBContract.STUDENT;
import com.biz.grade.config.Lines;
import com.biz.grade.domain.ScoreVO;
import com.biz.grade.domain.StudentVO;

public class StudentServiceImplV1 implements StudentService {

	private List<StudentVO> studentList;
	private Scanner scan;
	private String fileName;
	
	public StudentServiceImplV1() {
		studentList = new ArrayList<StudentVO>();
		scan  = new Scanner(System.in);
		fileName = "src/com/biz/grade/exec/data/student.txt";
	}
	
	// studentList를 외부에서 가져다가 사용할 수 있도록 통로를 설정
	public List<StudentVO> getStudentList(){
		
		return studentList;
	}
	// 학번 00001을 입력해 얘한테 보냈다고 하자
	// 학적부 리스트를 뒤졌는데 getNum에 00001이 있으면
	// 그 값을 studentVO에 넣어준다.
	
	
	public StudentVO getStudent(String st_num) {
		
		
		// 1. StudentVo를 null로 clear, null값을 studentVO에 할당
		StudentVO studentVO = null;
		
		// 2. studentList를 (순서대로)뒤지면서

		for(StudentVO stVO : studentList) {
			// 3. 매개변수로 받은 st_num가 학생 정보에 나타나는지 검사
			// 4. 만약 있으면 해당하는 학생정보를 studentVO에 복사하고
			// 5. 반복문을 종료
			if(stVO.getNum().equals(st_num)) {
				studentVO = stVO;
				break;
			}
			// 6. 만약 studentList에서 해당학번을 못찾으면
			// 반복문은 끝까지 진행 할 것이다.
		}
		
		// 7. 만약 중간에 if, break를 만나고 for문이 중단된 상태라면
		//  	studentVO에는 stVO가 담겨 있을 것이고
		// 8. for 반복문이 끝까지 진행된 상태라면
		// 		studentVO에는 null값이 담겨 있을 것이다.
		return studentVO;
		// studentVO에는 00001을 보내준다.
		// 만약 학생이 없으면 for문을 그냥 빠져 나와서 null값이 담기게 된다.
	}

	
	
	@Override
	public void loadStudnet() {
		FileReader fileReader = null;
		BufferedReader buffer = null;
		
		// 필드변수에 있는 걸 갖다 쓴다는 의미로 this붙여줌 확실하게 하기 위해
		try {
			fileReader = new FileReader(this.fileName);
			buffer = new BufferedReader(fileReader);
			
			String reader = "";
			while(true) {
				reader = buffer.readLine();
				if(reader == null) {
					break;
				}
				
				String[] students = reader.split(":");
				StudentVO studentVO = new StudentVO();
				
				studentVO.setNum(students[ DBContract.STUDENT.ST_NUM ]);
				studentVO.setName(students[ DBContract.STUDENT.ST_NAME ]);
				studentVO.setDept(students[ DBContract.STUDENT.ST_DEPT ]);
				studentVO.setGrade(Integer.valueOf(students[ DBContract.STUDENT.ST_GRADE ]));
				studentVO.setTel(students[ DBContract.STUDENT.ST_TEL ]);
				
				studentList.add(studentVO);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("학생 정보 파일 열기 오류!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("학생 정보 파일 읽기 오류!!!");
		}
		
		
	}

	@Override
	public boolean inputStudnet() {
		
		StudentVO studentVO = new StudentVO();
		
		System.out.print("학번(END:종료) >> ");
		// 변수명 명명 규칙
		// Camel cas : 두 단어 이상 사용할 때 단어의 첫글자 대문자
		// snack case : 두 단어 이상 사용 할 때 단어 사이 _(underscore)
		// 나중에 데이터베이스와 연동할 때 DB에서 보통 변수 이름을 저렇게 하기 때문에 호환성을 위하여 이렇게 작성
		String st_num = scan.nextLine(); 
		if(st_num.equals("END")) {
			return false;
		}
		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 가능");
			System.out.println("입력한 문자열 : "+ st_num);
			return true;
		}
		if(intNum< 1 || intNum > 99999) {
			System.out.println("학번은 1부터 99999까지만 입력가능합니다.");
			System.out.println("다시 입력해주세요");
			return true;
		}
		
		// 학번은 00001형식으로 만들기
		st_num = String.format("%05d",intNum);
		
		// ScoreSerivceImplV1에서 입력한 것을 그대로 가져옴.
		for(StudentVO sVO : studentList) {
			if(sVO.getNum().equals(st_num)) {
				System.out.println(st_num+"학생정보가 이미 등록 되어 있습니다.");
				return true;
			}
		}
		
		
		
		studentVO.setNum(st_num);
		
		
		System.out.print("이름 (END:종료) >> ");
		String st_name = scan.nextLine();
		if(st_name.equals("END")) {
			return false;
		}
		studentVO.setName(st_name);
		
		
		System.out.print("학과 (END:종료) >> ");
		String st_dept = scan.nextLine();
		if(st_dept.equals("END")) {
			return false;
		}
		studentVO.setDept(st_dept);
		
		System.out.print("학년 (END:종료) >> ");
		String st_grade = scan.nextLine(); 
		if(st_grade.equals("END")) {
			return false;
		}
		
		int intGrade = 0;
		try {
			intGrade = Integer.valueOf(st_grade);
		} catch (Exception e) {
			System.out.println("학년은숫자만 가능");
			System.out.println("입력한 문자열 : "+ st_num);
			return true;
		}
		if(intGrade< 1 || intGrade > 4) {
			System.out.println("학년은 1부터 4까지만 입력가능합니다.");
			System.out.println("다시 입력해주세요");
			return true;
		}
		studentVO.setGrade(intGrade);
		
		System.out.print("전화번호 : 010-111-1111 형식으로 입력. (END:종료) >> ");
		String st_tel = scan.nextLine();
		if(st_tel.equals("END")) {
			return false;
		}
		studentVO.setTel(st_tel);
		
		// 리스트에 추가하기
		studentList.add(studentVO);
		// 파일에 저장하기
		this.saveStudent();
		
		return true;
	}

	@Override
	public void saveStudent() {
		
		FileWriter fileWriter = null;
		PrintWriter pWriter = null;
		
		try {
			fileWriter = new FileWriter(this.fileName);
			pWriter = new PrintWriter(fileWriter);
			
			// 내부의 Writer Buffer에 값을 기록
			for(StudentVO sVO : studentList) {
				pWriter.printf("%s:",sVO.getNum());
				pWriter.printf("%s:",sVO.getName());
				pWriter.printf("%s:",sVO.getDept());
				pWriter.printf("%d:",sVO.getGrade());
				pWriter.printf("%s\n",sVO.getTel());
				
			}
			// Writer buffer에 기록된 값을 파일에 저장
			pWriter.flush(); // 이걸 호출해야만 기록이된다.
			pWriter.close(); // close 해줘야 한다.
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void studentList() {
		
		System.out.println(Lines.dLine);
		System.out.println("학생 명부 리스트");
		System.out.println(Lines.dLine);
		System.out.println("학번\t|이름\t|학과\t|학년\t|전화번호");
		System.out.println(Lines.sLine);
		for(StudentVO sVO : studentList) {
			
			System.out.printf("%s\t|",sVO.getNum() );
			System.out.printf("%s\t|",sVO.getName() );
			System.out.printf("%s\t|",sVO.getDept() );
			System.out.printf("%d\t|",sVO.getGrade() );
			System.out.printf("%s\t|\n",sVO.getTel() );
			
			
		}
		System.out.println(Lines.dLine);
		
	}

	
	
	
	
	
	
	
	
}
