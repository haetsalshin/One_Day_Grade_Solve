package com.biz.grade.exec;

import java.util.Scanner;

import com.biz.grade.config.DBContract;
import com.biz.grade.config.Lines;
import com.biz.grade.service.ScoreService;
import com.biz.grade.service.ScoreServiceImplV1;
import com.biz.grade.service.StudentService;
import com.biz.grade.service.StudentServiceImplV1;

public class GradeEx_01 {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		StudentService stService = new StudentServiceImplV1();
		stService.loadStudnet(); // 계속해서 파일을 읽어들어야 하므로 생성자 밑에다 붙여준다.
		
		ScoreService scService = new ScoreServiceImplV1();
		scService.loadScore();
				
		
		while(true) {
			System.out.println(Lines.dLine);
			System.out.println("빛고을 대학 학사관리 시스템 V1");
			System.out.println(Lines.dLine);
			System.out.println("1. 학생 정보 등록");
			System.out.println("2. 학생 정보 출력");
			System.out.println("3. 성적 등록");
			System.out.println("4. 성적 일람표 출력");
			System.out.println(Lines.sLine);
			System.out.println("QUIT. 업무종료");
			System.out.println(Lines.dLine);
			System.out.print("업무선택 >> ");
			String stMenu = scan.nextLine();
			if(stMenu.equals("QUIT")) {
				break;
			}
			
			int intMenu =0;
			try {
				intMenu = Integer.valueOf(stMenu);
			} catch (Exception e) {
				System.out.println("메뉴는 숫자로만 선택 할 수 있음!!");
				continue;
			}
			
			if(intMenu == DBContract.MENU.학생정보등록) {
				while(true) {
					if(!stService.inputStudnet()) {
						break;
					}
				}
			}else if(intMenu == DBContract.MENU.학생정보출력) { // 그냥 if로 쓰면 1번 끝난다음에 2번을 또 호출하기 때문에 else if로 써주어야 한다.
				
				stService.studentList();
			}else if(intMenu == DBContract.MENU.성적등록) {
				// inputScore먼저 호출 -> 만약 END를 눌러 종료하면 false를 꺼냄.
				// 이 값이 false이면 while내에서 끝나고 
				// true이면 while문 안에서만 무한 반복
				
				/*
				 * 1. inputScore() 호출하여 코드를 수행하고
				 * 2. inputScore()가 true를 return하면 계속 반복하고
				 * 3. inputScore()가 false를 return하면 반복을 중단한다
				 */
				while(scService.inputScore());
				
				// 성적입력을 종료(중단) 했을 경우 총점과 평균 계산 method 호출
				scService.calcSum();
				scService.calcAvg();
				
			}else if(intMenu == DBContract.MENU.성적일람표출력) {
				// 일람표를 출력하기 전에 총점과 평균을 호출해줘야한다..
				scService.calcSum();
				scService.calcAvg();
				scService.scoreList();
			}
		
		}
		System.out.println("업무종료!!!");
		System.out.println("야~ 퇴근이다!!!");
		
	}

}
