package it.polimi.db2.pairs;

import it.polimi.db2.entities.Answer;
import java.util.*;



public class AnswersPoints {
	
	private List<Answer> answers;
	private int points;
	
	public AnswersPoints(List<Answer> answers,int points) {
		this.answers = answers;
		this.points = points;
	}
	
	
	public int getPoints() {
		return this.points;
	}
	
	public List<Answer> getAnswers() {
		return this.answers;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	

}
