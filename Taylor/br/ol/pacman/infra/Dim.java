package br.ol.pacman.infra;

import java.awt.Dimension;
import java.io.Serializable;

public class Dim extends Dimension implements Serializable
{
	private int w;
	private int h;
	
	public Dim(int w, int h)
	{
		super(w,h);
	}
}
