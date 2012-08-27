/*    */ package net.minecraft;
/*    */ 
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class ErrorLog
/*    */ {
/* 16 */   private static Logger l = Logger.getLogger("");
/*    */ 
/*    */   public static void main() throws Exception {
/* 19 */     FileHandler handler = new FileHandler("C:\\launcherlog.txt");
/* 20 */     l.addHandler(handler);
/*    */ 
/* 22 */     l.setLevel(Level.ALL);
/*    */ 
/* 24 */     l.info("Error logs");
/*    */ 
/* 30 */     l.fine("");
/*    */   }
/*    */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.ErrorLog
 * JD-Core Version:    0.6.0
 */