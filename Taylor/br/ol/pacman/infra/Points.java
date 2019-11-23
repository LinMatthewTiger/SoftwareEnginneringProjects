package br.ol.pacman.infra;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Points extends Point2D.Double implements Serializable
{
	private int x;
	private int y;
	
	public Points(double x, double y)
	{
		super(x,y);
	}
}
