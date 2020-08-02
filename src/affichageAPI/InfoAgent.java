package affichageAPI;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class InfoAgent implements Serializable {
	@Expose
	private int x;
	@Expose
	private int y;
	@Expose
	private AgentAction agentAction;
	@Expose
	private ColorAgent color;
	@Expose
	private char type;
	@Expose
	private boolean isInvincible;
	@Expose
	private boolean isSick;
	
	public InfoAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		this.x=x;
		this.y=y;
		this.agentAction = agentAction;
		this.color = color;
		this.type = type;
		
		this.isInvincible = isInvincible;
		this.isSick = isSick;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	
	public ColorAgent getColor() {
		return color;
	}

	public void setColor(ColorAgent color) {
		this.color = color;
	}
	
	
	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}


	public boolean isInvincible() {
		return isInvincible;
	}


	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}


	public boolean isSick() {
		return isSick;
	}


	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}


	public AgentAction getAgentAction() {
		return agentAction;
	}


	public void setAgentAction(AgentAction agentAction) {
		this.agentAction = agentAction;
	}


}
	