/*    */ package net.minecraft;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class MinecraftLauncher
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 18 */       String pathToJar = MinecraftLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
/* 19 */       Properties props = Util.getOptions();
/* 20 */       ArrayList params = new ArrayList();
/* 21 */       Util.OS os = Util.getPlatform();
/* 22 */       if (os == Util.OS.windows)
/* 23 */         params.add("javaw");
/*    */       else
/* 25 */         params.add("java");
/* 26 */       String param1 = "-Xmx" + props.getProperty("maxmem") + "m";
/* 27 */       String param2 = "-Xms" + props.getProperty("maxmem") + "m";
/* 28 */       params.add(param1);
/* 29 */       params.add(param2);
/* 30 */       params.add("-classpath");
                System.out.println(pathToJar);
/* 31 */       params.add(pathToJar);
/* 32 */       params.add("net.minecraft.LauncherFrame");
/* 33 */       ProcessBuilder pb = new ProcessBuilder(params);
/* 34 */       Process process = pb.start();
/* 35 */       if (process == null) throw new Exception("!");
/*    */ 
/* 37 */       PrintStream prnt = new PrintStream(new File("C:\\launcherout.txt"));
/* 38 */       PrintStream err = new PrintStream(new File("C:\\launchererror.txt"));
/* 39 */       System.setOut(prnt);
/* 40 */       System.setErr(err);
/*    */     } catch (Exception e) {
/* 42 */       e.printStackTrace();
/* 43 */       LauncherFrame.main(args);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.MinecraftLauncher
 * JD-Core Version:    0.6.0
 */