/*     */ package net.minecraft;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class Util
/*     */ {
/*  28 */   private static File workDir = null;
/*     */   public static String host = "http://exsserver.ru/";
/*  30 */   public static int getMem() { return 512; }
/*     */ 
/*     */   public static File getWorkingDirectory() {
/*  33 */     if (workDir == null) workDir = getWorkingDirectory("minecraft");
/*  34 */     return workDir;
/*     */   }
/*     */ 
/*     */   public static Properties getOptions() {
/*  38 */     File f = new File(getWorkingDirectory() + File.separator + "bin");
/*  39 */     f.mkdirs();
/*  40 */     f = new File(getWorkingDirectory() + File.separator + "bin" + File.separator + "opts.properties");
/*     */     try {
/*  42 */       if (f.exists()) {
/*  43 */         InputStream optfile = new FileInputStream(f);
/*  44 */         Properties p = new Properties();
/*  45 */         p.load(optfile);
/*  46 */         optfile.close();
/*  47 */         return p;
/*     */       }
/*  49 */       return createOptsFile(f);
/*     */     } catch (Exception e) {
/*     */     }
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public static Properties createOptsFile(File f) throws Exception {
/*  56 */     OutputStream optfile = new FileOutputStream(f);
/*  57 */     Properties p = new Properties();
/*  58 */     p.setProperty("leavemods", "false");
/*  59 */     p.setProperty("leaveconf", "false");
/*  60 */     p.setProperty("maxmem", "512");
/*  61 */     p.store(optfile, null);
/*  62 */     optfile.close();
/*  63 */     return p;
/*     */   }
/*     */   public static void deleteOptionsFile() {
/*     */     try {
/*  67 */       File f = new File(getWorkingDirectory() + File.separator + "bin" + File.separator + "opts.properties");
/*  68 */       f.delete(); } catch (Exception e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Boolean saveOptions(Properties p) {
/*     */     try {
/*  73 */       File f = new File(getWorkingDirectory() + File.separator + "bin" + File.separator + "opts.properties");
/*  74 */       OutputStream optfile = new FileOutputStream(f);
/*  75 */       p.store(optfile, null);
/*  76 */       return Boolean.valueOf(true); } catch (Exception e) {
/*  77 */     }return Boolean.valueOf(false);
/*     */   }
/*     */   public static File getWorkingDirectory(String applicationName) {
/*  80 */     String userHome = System.getProperty("user.home", ".");
/*     */     File workingDirectory;
/*  82 */     switch (getPlatform().ordinal()) {
/*     */     case 0:
/*     */     case 1:
/*  85 */       workingDirectory = new File(userHome, '.' + applicationName + '/');
/*  86 */       break;
/*     */     case 2:
/*  88 */       String applicationData = System.getenv("APPDATA");
/*  89 */       if (applicationData != null)
/*  90 */         workingDirectory = new File(applicationData, "." + applicationName + '/');
/*     */       else
/*  92 */         workingDirectory = new File(userHome, '.' + applicationName + '/');
/*  93 */       break;
/*     */     case 3:
/*  95 */       workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
/*  96 */       break;
/*     */     default:
/*  98 */       workingDirectory = new File(userHome, applicationName + '/');
/*     */     }
/* 100 */     if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs())) throw new RuntimeException("The working directory could not be created: " + workingDirectory);
/* 101 */     return workingDirectory;
/*     */   }
/*     */ 
/*     */   public static OS getPlatform() {
/* 105 */     String osName = System.getProperty("os.name").toLowerCase();
/* 106 */     if (osName.contains("win")) return OS.windows;
/* 107 */     if (osName.contains("mac")) return OS.macos;
/* 108 */     if (osName.contains("solaris")) return OS.solaris;
/* 109 */     if (osName.contains("sunos")) return OS.solaris;
/* 110 */     if (osName.contains("linux")) return OS.linux;
/* 111 */     if (osName.contains("unix")) return OS.linux;
/* 112 */     return OS.unknown;
/*     */   }
/*     */ 
/*     */   public static String excutePost(String targetURL, String urlParameters)
/*     */   {
/* 118 */     HttpURLConnection connection = null;
/*     */     try
/*     */     {
/* 121 */       URL url = new URL(targetURL);
/*     */ 
/* 123 */       connection = (HttpURLConnection)url.openConnection();
/* 124 */       connection.setRequestMethod("POST");
/* 125 */       connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*     */ 
/* 127 */       connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
/* 128 */       connection.setRequestProperty("Content-Language", "en-US");
/*     */ 
/* 130 */       connection.setUseCaches(false);
/* 131 */       connection.setDoInput(true);
/* 132 */       connection.setDoOutput(true);
/*     */ 
/* 134 */       connection.connect();
/*     */ 
/* 137 */       byte[] bytes = new byte[294];
/* 138 */       DataInputStream dis = new DataInputStream(Util.class.getResourceAsStream("minecraft.key"));
/* 139 */       dis.readFully(bytes);
/* 140 */       dis.close();
/*     */ 
/* 150 */       DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
/* 151 */       wr.writeBytes(urlParameters);
/* 152 */       wr.flush();
/* 153 */       wr.close();
/*     */ 
/* 155 */       InputStream is = connection.getInputStream();
/* 156 */       BufferedReader rd = new BufferedReader(new InputStreamReader(is));
/*     */ 
/* 158 */       StringBuffer response = new StringBuffer();
/*     */       String line;
/* 160 */       while ((line = rd.readLine()) != null)
/*     */       {
/* 162 */         response.append(line);
/* 163 */         response.append('\r');
/*     */       }
/* 165 */       rd.close();
/*     */ 
/* 167 */       String str1 = response.toString();
/*     */       return str1;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 172 */       e.printStackTrace();
/* 173 */       String bytes = null;
/*     */       return bytes;
/*     */     }
/*     */     finally
/*     */     {
/* 177 */       if (connection != null)
/* 178 */         connection.disconnect(); 
/* 178 */     }
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(String str)
/*     */   {
/* 183 */     return (str == null) || (str.length() == 0);
/*     */   }
/*     */ 
/*     */   public static void openLink(URI uri) {
/*     */     try {
/* 188 */       Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 189 */       o.getClass().getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { uri });
/*     */     } catch (Throwable e) {
/* 191 */       System.out.println("Failed to open link " + uri.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void resetVersion()
/*     */   {
/* 198 */     DataOutputStream dos = null;
/*     */     try {
/* 200 */       File dir = new File(getWorkingDirectory() + File.separator + "bin" + File.separator);
/* 201 */       File versionFile = new File(dir, "version");
/* 202 */       dos = new DataOutputStream(new FileOutputStream(versionFile));
/* 203 */       dos.writeUTF("0");
/* 204 */       dos.close();
/*     */     } catch (FileNotFoundException ex) {
/* 206 */       Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (IOException ex) {
/* 208 */       Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } finally {
/*     */       try {
/* 211 */         dos.close();
/*     */       } catch (IOException ex) {
/* 213 */         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getFakeLatestVersion() {
/*     */     try {
/* 220 */       File dir = new File(getWorkingDirectory() + File.separator + "bin" + File.separator);
/* 221 */       File file = new File(dir, "version");
/* 222 */       DataInputStream dis = new DataInputStream(new FileInputStream(file));
/* 223 */       String version = dis.readUTF();
/* 224 */       dis.close();
/* 225 */       if (version.equals("0")) {
/* 226 */         return "1285241960000";
/*     */       }
/* 228 */       return version; } catch (IOException ex) {
/*     */     }
/* 230 */     return "1285241960000";
/*     */   }
/*     */ 
/*     */   public static enum OS
/*     */   {
/* 236 */     linux, solaris, windows, macos, unknown;
/*     */   }
/*     */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.Util
 * JD-Core Version:    0.6.0
 */