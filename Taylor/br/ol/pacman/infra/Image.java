package br.ol.pacman.infra;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Image extends BufferedImage implements Serializable
{
	public Image(int x, int y, int z)
	{
		super(x, y, z);
	}
}
