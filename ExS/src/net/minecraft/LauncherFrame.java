/*     */ package net.minecraft;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class LauncherFrame extends Frame
/*     */ {
/*     */   public static final int VERSION = 25;
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   public Map<String, String> customParameters = new HashMap();
/*     */   public Launcher launcher;
/*     */   public LoginForm loginForm;
/*     */ 
/*     */   public LauncherFrame()
/*     */   {
/*  30 */     super("Minecraft Launcher");
/*     */ 
/*  32 */     setBackground(Color.BLACK);
/*  33 */     this.loginForm = new LoginForm(this);
/*  34 */     JPanel p = new JPanel();
/*  35 */     p.setLayout(new BorderLayout());
/*  36 */     p.add(this.loginForm, "Center");
/*     */ 
/*  38 */     p.setPreferredSize(new Dimension(854, 480));
/*     */ 
/*  40 */     setLayout(new BorderLayout());
/*  41 */     add(p, "Center");
/*     */ 
/*  43 */     pack();
/*  44 */     setResizable(false);
/*  45 */     setLocationRelativeTo(null);
/*     */     try
/*     */     {
/*  49 */       setIconImage(ImageIO.read(LauncherFrame.class.getResource("favicon.png")));
/*     */     } catch (IOException e1) {
/*  51 */       e1.printStackTrace();
/*     */     }
/*     */ 
/*  54 */     addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent arg0) {
/*  56 */         new Thread() {
/*     */           public void run() {
/*     */             try {
/*  59 */               Thread.sleep(30000L);
/*     */             } catch (InterruptedException e) {
/*  61 */               e.printStackTrace();
/*     */             }
/*  63 */             System.out.println("FORCING EXIT!");
/*  64 */             System.exit(0);
/*     */           }
/*     */         }
/*  56 */         .start();
/*     */ 
/*  68 */         if (LauncherFrame.this.launcher != null) {
/*  69 */           LauncherFrame.this.launcher.stop();
/*  70 */           LauncherFrame.this.launcher.destroy();
/*     */         }
/*  72 */         System.exit(0);
/*     */       } } );
/*     */   }
/*     */ 
/*     */   public void playCached(String userName) {
/*     */     try {
/*  78 */       if ((userName == null) || (userName.length() <= 0)) {
/*  79 */         userName = "Player";
/*     */       }
/*  81 */       this.launcher = new Launcher();
/*  82 */       this.launcher.customParameters.putAll(this.customParameters);
/*  83 */       this.launcher.customParameters.put("userName", userName);
/*  84 */       this.launcher.init();
/*  85 */       removeAll();
/*  86 */       add(this.launcher, "Center");
/*  87 */       validate();
/*  88 */       this.launcher.start();
/*  89 */       this.loginForm = null;
/*  90 */       setTitle("Minecraft");
/*     */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*  93 */       showError(e.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void login(String userName, String password)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       String parameters = "user=" + URLEncoder.encode(userName, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&version=" + 25;
/* 106 */       String result = Util.excutePost(Util.host + "server/login/auth.php", parameters);
/* 107 */       if (result == null) {
/* 108 */         showError("Невозможно подключиться к серверу!");
/* 109 */         this.loginForm.setNoNetwork();
/* 110 */         return;
/*     */       }
/* 112 */       if (!result.contains(":")) {
/* 113 */         if (result.trim().equals("Ошибка авторизации.")) {
/* 114 */           showError("Неправильный логин или пароль!");
/* 115 */         } else if (result.trim().equals("Old version")) {
/* 116 */           this.loginForm.setOutdated();
/* 117 */           showError("Нужно обновить лаунчер!");
/*     */         } else {
/* 119 */           showError(result);
/*     */         }
/* 121 */         this.loginForm.setNoNetwork();
/* 122 */         return;
/*     */       }
/* 124 */       String[] values = result.split(":");
/*     */ 
/* 126 */       this.launcher = new Launcher();
/* 127 */       this.launcher.customParameters.putAll(this.customParameters);
/* 128 */       this.launcher.customParameters.put("userName", values[2].trim());
/* 129 */       this.launcher.customParameters.put("latestVersion", values[0].trim());
/* 130 */       this.launcher.customParameters.put("downloadTicket", values[1].trim());
/* 131 */       this.launcher.customParameters.put("sessionId", values[3].trim());
/* 132 */       this.launcher.init();
/*     */ 
/* 134 */       removeAll();
/* 135 */       add(this.launcher, "Center");
/* 136 */       validate();
/* 137 */       this.launcher.start();
/* 138 */       this.loginForm.loginOk();
/* 139 */       this.loginForm = null;
/* 140 */       setTitle("Minecraft");
/* 141 */       setResizable(true);
/*     */     } catch (Exception e) {
/* 143 */       e.printStackTrace();
/* 144 */       showError(e.toString());
/* 145 */       this.loginForm.setNoNetwork();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showError(String error) {
/* 150 */     removeAll();
/* 151 */     add(this.loginForm);
/* 152 */     this.loginForm.setError(error);
/* 153 */     validate();
/*     */   }
/*     */ 
/*     */   public boolean canPlayOffline(String userName) {
/* 157 */     Launcher launcher = new Launcher();
/* 158 */     launcher.customParameters.putAll(this.customParameters);
/* 159 */     launcher.init(userName, null, null, null);
/* 160 */     return launcher.canPlayOffline();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*     */     try {
/* 165 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 169 */     LauncherFrame launcherFrame = new LauncherFrame();
/* 170 */     launcherFrame.setVisible(true);
/* 171 */     launcherFrame.customParameters.put("stand-alone", "true");
/*     */ 
/* 186 */     if (args.length >= 1) {
/* 187 */       launcherFrame.loginForm.userName.setText(args[0]);
/* 188 */       if (args.length >= 2) {
/* 189 */         launcherFrame.loginForm.password.setText(args[1]);
/* 190 */         launcherFrame.loginForm.doLogin();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.LauncherFrame
 * JD-Core Version:    0.6.0
 */