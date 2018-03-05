package br.edu.cefsa.ec6.laion;

import java.util.Locale;

public class SvgGenerator {
	private final static int STROKE_WIDTH = 1;
	private final static int STROKE_LENGTH = 10;
	
	private int angle;
	private String lSystem;
	
	public SvgGenerator(DolSystem dolSystem){
		Locale.setDefault(Locale.ENGLISH);
		//otherwise the double values could be wrote as 0,00 instead of 0.00 
		 
		this.angle = dolSystem.getAngle();
		this.lSystem = dolSystem.getLSystem();
	}

	public String generateSvg(){
		Position currentPosition = this.getBestStartingPosition();
		String svgTag = "<svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"000000\">";
		
		char[] letters = this.lSystem.toCharArray();
		
		for(char letter : letters){
			String newPath = consume(letter, currentPosition);
			if(!newPath.isEmpty())
				svgTag += "\n   " + newPath;
		}
		
		svgTag += "\n</svg>";
		return svgTag;
	}
	
	private Position getBestStartingPosition(){
		Position currentPosition = new Position(0, 0, this.angle);
		double minX = 0;
		double minY = 0;
		
		char[] letters = this.lSystem.toCharArray();
		
		for(char letter : letters){
			consume(letter, currentPosition);
			minX = Math.min(minX, currentPosition.getX());
			minY = Math.min(minY, currentPosition.getY()); 
		}
		
		return new Position((minX * -1) + (STROKE_WIDTH * 2), (minY * -1) + (STROKE_WIDTH * 2), this.angle);
	}
	
	private String consume(char letter, Position pos){
		switch(letter){
			case 'F':
				return this.newLinePath(pos);
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
	
	private String newLinePath(Position pos){
		double rad = Math.toRadians(pos.getAngle());
		
		double newX = pos.getX() + STROKE_LENGTH * Math.cos(rad);
		double newY = pos.getY() + STROKE_LENGTH * Math.sin(rad);

		String path = "<path d=\"";
		path += String.format("M %1$.2f %2$.2f L %3$.2f %4$.2f", pos.getX(), pos.getY(), newX, newY);
		path += String.format("\" stroke=\"black\" stroke-width=\"%s\" fill=\"none\"/>", STROKE_WIDTH);
		
		pos.setX(newX);
		pos.setY(newY);
		
		return path;
	}
	private void emptyMoviment(Position pos){
		double rad = Math.toRadians(pos.getAngle());
		
		double newX = pos.getX() + STROKE_LENGTH * Math.cos(rad);
		double newY = pos.getY() + STROKE_LENGTH * Math.sin(rad);

		pos.setX(newX);
		pos.setY(newY);
	}
	private void turnClockwise(Position pos){
		int angle = pos.getAngle();
		pos.setAngle((angle + this.angle) % 360);
	}
	private void turnAntilockwise(Position pos){
		int angle = pos.getAngle();
		pos.setAngle((angle + 360 - this.angle) % 360);		
	}
}
