package br.edu.cefsa.ec6.laion;

import java.util.Locale;

public class SvgGenerator {
	private final static int STROKE_WIDTH = 1;
	private final static int STROKE_LENGTH = 10;
	
	private int incrementAngle;	
	private int starterAngle;
	private String turtle;
	
	public SvgGenerator(DolSystem dolSystem){
		Locale.setDefault(Locale.ENGLISH);
		//otherwise the double values could be wrote as 0,00 instead of 0.00 
		 
		this.incrementAngle = dolSystem.getIncrementAngle();
		this.starterAngle = dolSystem.getStarterAngle();
		this.turtle = dolSystem.getTurtle();
	}

	public String generateSvg(){
		Position currentPosition = this.getBestStartingPosition();
		String svgTag = "<svg xmlns=\"http://www.w3.org/2000/svg\">";
		
		char[] letters = this.turtle.toCharArray();
		
		for(char letter : letters){
			String newLine = consume(letter, currentPosition);
			if(!newLine.isEmpty())
				svgTag += "\n   " + newLine;
		}
		
		svgTag += "\n</svg>";
		return svgTag;
	}
	
	private Position getBestStartingPosition(){
		Position currentPosition = new Position(0, 0, this.starterAngle);
		double minX = 0;
		double minY = 0;
		
		char[] letters = this.turtle.toCharArray();
		
		for(char letter : letters){
			consume(letter, currentPosition);
			minX = Math.min(minX, currentPosition.getX());
			minY = Math.min(minY, currentPosition.getY()); 
		}
		
		return new Position((minX * -1) + (STROKE_WIDTH * 2), (minY * -1) + (STROKE_WIDTH * 2), this.starterAngle);
	}
	
	private String consume(char letter, Position pos){
		switch(letter){
			case 'F':
				return this.newLine(pos);
			case 'f':
				this.emptyMoviment(pos);
				break;
			case '-':
				this.turnClockwise(pos);
				break;
			case '+':
				this.turnAntilockwise(pos);
				break;
		}
		return new String();
	}
	
	private String newLine(Position pos){
		double rad = Math.toRadians(pos.getAngle());
		
		double newX = pos.getX() + STROKE_LENGTH * Math.cos(rad);
		double newY = pos.getY() + STROKE_LENGTH * Math.sin(rad);
		
		String line = "<line ";
		line += String.format("x1=\"%1$.3f\" y1=\"%2$.3f\" x2=\"%3$.3f\" y2=\"%4$.3f\"", pos.getX(), pos.getY(), newX, newY);
		line += String.format(" style=\"stroke:black;stroke-width:%s\" />", STROKE_WIDTH);
		
		pos.setX(newX);
		pos.setY(newY);
		
		return line;
	}
	private void emptyMoviment(Position pos){
		double rad = Math.toRadians(pos.getAngle());
		
		double newX = pos.getX() + STROKE_LENGTH * Math.cos(rad);
		double newY = pos.getY() + STROKE_LENGTH * Math.sin(rad);

		pos.setX(newX);
		pos.setY(newY);
	}
	
	//the clock orientation is reversed because the y-axis on HTML is upside down
	private void turnClockwise(Position pos){
		int angle = pos.getAngle();
		pos.setAngle((angle + this.incrementAngle) % 360);
	}
	private void turnAntilockwise(Position pos){
		int angle = pos.getAngle();
		pos.setAngle((angle + 360 - this.incrementAngle) % 360);		
	}
}
