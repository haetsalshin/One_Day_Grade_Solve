package com.biz.grade.service;

import java.util.List;

import com.biz.grade.domain.ScoreVO;

public interface ScoreService {
	
	public void loadScore();
	public boolean inputScore();
	
	public void saveScoreVO(ScoreVO scoreVO);
	
	public void saveScore();
	public void scoreList();
	
	public void calcSum();
	public void calcAvg();
	
	
}
