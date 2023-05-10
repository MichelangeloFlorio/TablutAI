package it.unibo.ai.didattica.competition.tablut.tablutai.domain;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + "]";
	};
	
	

}
