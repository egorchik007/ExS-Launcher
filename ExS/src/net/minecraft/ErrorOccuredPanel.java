/*    */ package net.minecraft;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ErrorOccuredPanel extends JDialog
/*    */ {
/*    */   public ErrorOccuredPanel()
/*    */   {
/* 29 */     JPanel panel = new JPanel(new BorderLayout());
/* 30 */     JLabel label = new JLabel("Настройки", 0);
/* 31 */     panel.add(label, "North");
/* 32 */     add(panel);
/* 33 */     pack();
/*    */   }
/*    */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.ErrorOccuredPanel
 * JD-Core Version:    0.6.0
 */