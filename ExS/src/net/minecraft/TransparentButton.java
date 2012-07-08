package net.minecraft;

import java.awt.Color;
import javax.swing.JButton;

public class TransparentButton extends JButton
{
  private static final long serialVersionUID = 1L;

  public TransparentButton(String string)
  {
    super(string);
    setForeground(Color.BLACK);
    setBackground(Color.WHITE);
    setOpaque(false);
  }

  public boolean isOpaque() {
    return false;
  }
}