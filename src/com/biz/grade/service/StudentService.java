package com.biz.grade.service;

import java.util.List;

import com.biz.grade.domain.StudentVO;

/*
 * 파일을 읽어서 List에 담기
 * 학생 정보를 입력 받아 List에 담기
 * List에 담긴 학생 정보를 파일에 저장하기
 */
public interface StudentService {
	public void loadStudnet();
	public boolean inputStudnet();
	public void saveStudent();
	public void studentList();
	
	public StudentVO getStudent(String st_num);
	
	// List<StudentVO> 타입으로 설정된 변수를 return 하겠다.
	public List<StudentVO> getStudentList();

}
