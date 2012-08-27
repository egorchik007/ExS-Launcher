/*     */ package net.minecraft;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class OptionsPanel extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   public static boolean forceUpdate = false;
/*     */ 
/*  32 */   private static Properties options = Util.getOptions();
/*  33 */   public static boolean doNotDeleteMods = Boolean.parseBoolean(options.getProperty("leavemods"));
/*  34 */   public static boolean doNotDeleteConfig = Boolean.parseBoolean(options.getProperty("leaveconf"));
/*  35 */   public static int memory = Integer.parseInt(options.getProperty("maxmem"));
/*     */ 
/*     */   public OptionsPanel(Frame parent)
/*     */   {
/*  40 */     super(parent);
/*  41 */     setSize(800, 600);
/*  42 */     setModal(true);
/*     */     try {
/*  44 */       final TransparentCheckbox forceCheckBox = new TransparentCheckbox("Принудительно обновить клиент");
/*  45 */       final TransparentCheckbox doNotDeleteModsCheckBox = new TransparentCheckbox("Не удалять папку mods при обновлении");
/*  46 */       final TransparentCheckbox doNotDeleteConfigCheckBox = new TransparentCheckbox("Не удалять настройки модов при обновлении");
/*  47 */       forceCheckBox.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent actionEvent) {
/*  49 */           OptionsPanel.forceUpdate = forceCheckBox.isSelected();
/*     */         }
/*     */       });
/*  52 */       forceCheckBox.setForeground(Color.BLACK);
/*  53 */       doNotDeleteModsCheckBox.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent actionEvent) {
/*  55 */           OptionsPanel.doNotDeleteMods = doNotDeleteModsCheckBox.isSelected();
/*  56 */           OptionsPanel.options.setProperty("leavemods", Boolean.toString(OptionsPanel.doNotDeleteMods));
/*     */         }
/*     */       });
/*  59 */       doNotDeleteModsCheckBox.setForeground(Color.BLACK);
/*  60 */       doNotDeleteModsCheckBox.setSelected(doNotDeleteMods);
/*  61 */       doNotDeleteConfigCheckBox.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent actionEvent) {
/*  63 */           OptionsPanel.doNotDeleteConfig = doNotDeleteConfigCheckBox.isSelected();
/*  64 */           OptionsPanel.options.setProperty("leaveconf", Boolean.toString(OptionsPanel.doNotDeleteConfig));
/*     */         }
/*     */       });
/*  67 */       doNotDeleteConfigCheckBox.setForeground(Color.BLACK);
/*  68 */       doNotDeleteConfigCheckBox.setSelected(doNotDeleteConfig);
/*  69 */       JPanel panel = new JPanel(new BorderLayout());
/*  70 */       JLabel label = new JLabel("Настройки", 0);
/*  71 */       label.setBorder(new EmptyBorder(0, 0, 16, 0));
/*  72 */       label.setFont(new Font("Default", 1, 16));
/*  73 */       panel.add(label, "North");
/*  74 */       String osbits = System.getProperty("os.arch").toLowerCase();
/*  75 */       int max = 1800;
/*  76 */       if (osbits == "x64")
/*  77 */         max = 4000;
/*  78 */       final JSlider maxmemSlider = new JSlider(300, max, Integer.parseInt(options.getProperty("maxmem")));
/*     */ 
/*  80 */       maxmemSlider.setPaintTicks(true);
/*  81 */       maxmemSlider.setPaintLabels(true);
/*  82 */       maxmemSlider.setMajorTickSpacing(400);
/*  83 */       maxmemSlider.setMinorTickSpacing(100);
/*  84 */       maxmemSlider.setLabelTable(maxmemSlider.createStandardLabels(400));
/*  85 */       final JLabel maxmemLabel = new JLabel("<html><center>Выделенная память: <i>" + options.getProperty("maxmem") + " МБ</i><br />Внимание!<br />Не задавайте памяти больше, чем доступно программам!<br /><br /></center></html>", 0);
/*  86 */       maxmemSlider.addChangeListener(new ChangeListener() {
/*     */         public void stateChanged(ChangeEvent e) {
/*  88 */           JSlider source = (JSlider)e.getSource();
/*  89 */           int cval = maxmemSlider.getValue();
/*  90 */           if (cval < 2048)
/*  91 */             maxmemLabel.setText("<html><center>Выделенная память: <i>" + cval + " МБ</i><br />(требуется перезапуск лаунчера)<br />Внимание!<br />Не задавайте памяти больше, чем доступно программам!<br /></center></html>");
/*     */           else
/*  93 */             maxmemLabel.setText("<html><center>Выделенная память: <i><font color='red'>" + cval + " МБ</font></i><br />(требуется перезапуск лаунчера)<br />Внимание!<br />Не задавайте памяти больше, чем доступно программам!<br /></center></html>");
/*  94 */           if (!source.getValueIsAdjusting())
/*  95 */             OptionsPanel.options.setProperty("maxmem", String.valueOf(cval));
/*     */         }
/*     */       });
/* 101 */       JPanel optionsPanel = new JPanel(new BorderLayout());
/* 102 */       JPanel labelPanel = new JPanel(new GridLayout(0, 1));
/*     */ 
/* 104 */       optionsPanel.add(labelPanel, "East");
/*     */ 
/* 115 */       labelPanel.add(forceCheckBox);
/* 116 */       labelPanel.add(doNotDeleteModsCheckBox);
/* 117 */       labelPanel.add(doNotDeleteConfigCheckBox);
/* 118 */       labelPanel.add(maxmemSlider);
/* 119 */       labelPanel.add(maxmemLabel);
/*     */ 
/* 160 */       panel.add(optionsPanel, "Center");
/*     */ 
/* 162 */       JPanel buttonsPanel = new JPanel(new BorderLayout());
/* 163 */       buttonsPanel.add(new JPanel(), "Center");
/* 164 */       JButton doneButton = new JButton("Done");
/* 165 */       doneButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent ae) {
/* 167 */           Util.saveOptions(OptionsPanel.options);
/* 168 */           OptionsPanel.this.setVisible(false);
/*     */         }
/*     */       });
/* 171 */       buttonsPanel.add(doneButton, "East");
/* 172 */       buttonsPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
/*     */ 
/* 174 */       panel.add(buttonsPanel, "South");
/*     */ 
/* 176 */       add(panel);
/* 177 */       panel.setBorder(new EmptyBorder(16, 24, 24, 24));
/*     */     } catch (Exception e) {
/* 179 */       Util.deleteOptionsFile();
/* 180 */     }pack();
/* 181 */     setResizable(false);
/* 182 */     setLocationRelativeTo(parent);
/*     */   }
/*     */ }

/* Location:           C:\Users\exploser\Downloads\ExS.jar
 * Qualified Name:     net.minecraft.OptionsPanel
 * JD-Core Version:    0.6.0
 */