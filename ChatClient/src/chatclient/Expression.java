package chatclient;

import javax.swing.*;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JButton;

/* references
 * https://github.com/wangchangshuai/fei_Q
 * http://blog.csdn.net/yangxingzou/article/details/6888222
 */
public class Expression extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private MainFrame main;

	private JPanel jContentPane = null;

	private JButton a1 = null; 

	private JButton a2 = null;

	private JButton a3 = null;

	private JButton a4 = null;

	private JButton a5 = null;

	private JButton b1 = null;

	private JButton b2 = null;

	private JButton b3 = null;

	private JButton b4 = null;

	private JButton b5 = null;

	private JButton c1 = null;

	private JButton c2 = null;

	private JButton c3 = null;

	private JButton c4 = null;

	private JButton c5 = null;

	private JButton d1 = null;

	private JButton d2 = null;

	private JButton d3 = null;

	private JButton d4 = null;

	private JButton d5 = null;

	private JButton f1 = null;

	private JButton f2 = null;

	private JButton f3 = null;

	private JButton f4 = null;

	private JButton f5 = null;

	/**
	 * this JDialog's position
	 */
	public Expression(MainFrame owner,int x,int y) {
		super(owner);
		main = owner;
		initialize(x,y);
	}

	/**
	 * init this JDialog
	 */
	private void initialize(int x,int y) {//
		this.setBounds(x, y-300, 300, 300);
		this.setTitle("emoji");
		this.setVisible(true);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() { 
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(5,5));
			jContentPane.add(getA3(), null);
			jContentPane.add(getF1(), null);
			jContentPane.add(getF2(), null);
			jContentPane.add(getF3(), null);
			jContentPane.add(getF4(), null);
			jContentPane.add(getD2(), null);
			jContentPane.add(getD4(), null);
			jContentPane.add(getD5(), null);
			jContentPane.add(getD3(), null);
			jContentPane.add(getC1(), null);
			jContentPane.add(getD1(), null);
			jContentPane.add(getC5(), null);
			jContentPane.add(getC4(), null);
			jContentPane.add(getC3(), null);
			jContentPane.add(getC2(), null);
			jContentPane.add(getB5(), null);
			jContentPane.add(getB4(), null);
			jContentPane.add(getB3(), null);
			jContentPane.add(getB2(), null);
			jContentPane.add(getA2(), null);
			jContentPane.add(getA1(), null);
			jContentPane.add(getA4(), null);
			jContentPane.add(getA5(), null);
			jContentPane.add(getB1(), null);
			jContentPane.add(getF5(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes a1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getA1() {  
		//return a button
		if (a1 == null) {
			a1 = new JButton();
			a1.setBackground(Color.white);
			a1.setToolTipText("Á÷º¹");
			a1.setIcon(new ImageIcon("src/image/[11].gif"));
			a1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();    
					//insert position
					in.setFirstindex(main.messageSend.getCaretPosition()); 
					//emoji code
					in.setExpressioncode("[11]"); 
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[11].gif"));
				}
			});
		}
		return a1;
	}

	/**
	 * This method initializes a2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getA2() {
		if (a2 == null) {
			a2 = new JButton();
			a2.setBackground(Color.white);
			a2.setToolTipText("¿ÉÁ¯");
			a2.setIcon(new ImageIcon("src/image/[1112].gif"));
			a2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[1112]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[1112].gif"));
				}
			});
		}
		return a2;
	}

	/**
	 * This method initializes a3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getA3() {
		if (a3 == null) {
			a3 = new JButton();
			a3.setBackground(Color.white);
			a3.setToolTipText("Àäº¹");
			System.out.println("get icon");
			try {
				ImageIcon A3 = new ImageIcon("src/image/[22].gif");
				a3.setIcon(A3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			System.out.println("finishget icon");
			a3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[22]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[22].gif"));
				}
			});
		}
		return a3;
	}

	/**
	 * This method initializes a4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getA4() {
		if (a4 == null) {
			a4 = new JButton();
			a4.setBackground(Color.white);
			a4.setToolTipText("×¥¿ñ");
			a4.setIcon(new ImageIcon("src/image/[33].gif"));
			a4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[33]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[33].gif"));
				}
			});
		}
		return a4;
	}

	/**
	 * This method initializes a5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getA5() {
		if (a5 == null) {
			a5 = new JButton();
			a5.setBackground(Color.white);
			a5.setToolTipText("ßÚÑÀ");
			a5.setIcon(new ImageIcon("src/image/[343].gif"));
			a5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[343]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[343].gif"));
				}
			});
		}
		return a5;
	}

	/**
	 * This method initializes b1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB1() {
		if (b1 == null) {
			b1 = new JButton();
			b1.setBackground(Color.white);
			b1.setToolTipText("Æ²×ì");
			b1.setIcon(new ImageIcon("src/image/[3454].gif"));
			b1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[3454]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[3454].gif"));
				}
			});
		}
		return b1;
	}

	/**
	 * This method initializes b2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB2() {
		if (b2 == null) {
			b2 = new JButton();
			b2.setBackground(Color.white);
			b2.setToolTipText("º©Ð¦");
			b2.setIcon(new ImageIcon("src/image/[4343].gif"));
			b2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[4343]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[4343].gif"));
				}
			});
		}
		return b2;
	}

	/**
	 * This method initializes b3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB3() {
		if (b3 == null) {
			b3 = new JButton();
			b3.setBackground(Color.white);
			b3.setToolTipText("²Áº¹");
			b3.setIcon(new ImageIcon("src/image/[44].gif"));
			b3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[44]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[44].gif"));
				}
			});
		}
		return b3;
	}

	/**
	 * This method initializes b4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB4() {
		if (b4 == null) {
			b4 = new JButton();
			b4.setBackground(Color.white);
			b4.setToolTipText("´ó±ø");
			b4.setIcon(new ImageIcon("src/image/[4546r].gif"));
			b4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[4546r]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[4546r].gif"));
				}
			});
		}
		return b4;
	}

	/**
	 * This method initializes b5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB5() {
		if (b5 == null) {
			b5 = new JButton();
			b5.setBackground(Color.white);
			b5.setToolTipText("·¢Å­");
			b5.setIcon(new ImageIcon("src/image/[45g].gif"));
			b5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[45g]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[45g].gif"));
				}
			});
		}
		return b5;
	}

	/**
	 * This method initializes c1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getC1() {
		if (c1 == null) {
			c1 = new JButton();
			c1.setBackground(Color.white);
			c1.setToolTipText("º¦Ðß");
			c1.setIcon(new ImageIcon("src/image/[46435].gif"));
			c1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[46435]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[46435].gif"));
				}
			});
		}
		return c1;
	}

	/**
	 * This method initializes c2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getC2() {
		if (c2 == null) {
			c2 = new JButton();
			c2.setBackground(Color.white);
			c2.setToolTipText("°×ÑÛ");
			c2.setIcon(new ImageIcon("src/image/[545634].gif"));
			c2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[545634]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[545634].gif"));
				}
			});
		}
		return c2;
	}

	/**
	 * This method initializes c3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getC3() {
		if (c3 == null) {
			c3 = new JButton();
			c3.setBackground(Color.white);
			c3.setToolTipText("¿Ù±Ç");
			c3.setIcon(new ImageIcon("src/image/[55].gif"));
			c3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[55]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[55].gif"));
				}
			});
		}
		return c3;
	}

	/**
	 * This method initializes c4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getC4() {
		if (c4 == null) {
			c4 = new JButton();
			c4.setBackground(Color.white);
			c4.setToolTipText("¹þÇ·");
			c4.setIcon(new ImageIcon("src/image/[5567g1].gif"));
			c4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[5567g1]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[5567g1].gif"));
				}
			});
		}
		return c4;
	}

	/**
	 * This method initializes c5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getC5() {
		if (c5 == null) {
			c5 = new JButton();
			c5.setBackground(Color.white);
			c5.setToolTipText("ÇÃ´ò");
			c5.setIcon(new ImageIcon("src/image/[56g56].gif"));
			c5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[56g56]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[56g56].gif"));
				}
			});
		}
		return c5;
	}

	/**
	 * This method initializes d1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getD1() {
		if (d1 == null) {
			d1 = new JButton();
			d1.setBackground(Color.white);
			d1.setToolTipText("¹ÄÕÆ");
			d1.setIcon(new ImageIcon("src/image/[5g5].gif"));
			d1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[5g5]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[5g5].gif"));
				}
			});
		}
		return d1;
	}

	/**
	 * This method initializes d2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getD2() {
		if (d2 == null) {
			d2 = new JButton();
			d2.setBackground(Color.white);
			d2.setToolTipText("ÔÙ¼û");
			d2.setIcon(new ImageIcon("src/image/[5g5d].gif"));
			d2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[5g5d]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[5g5d].gif"));
				}
			});
		}
		return d2;
	}

	/**
	 * This method initializes d3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getD3() {
		if (d3 == null) {
			d3 = new JButton();
			d3.setBackground(Color.white);
			d3.setToolTipText("ÒÉÎÊ");
			d3.setIcon(new ImageIcon("src/image/[6565].gif"));
			d3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[6565]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[6565].gif"));
				}
			});
		}
		return d3;
	}

	/**
	 * This method initializes d4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getD4() {
		if (d4 == null) {
			d4 = new JButton();
			d4.setBackground(Color.white);
			d4.setToolTipText("¾ª¿Ö");
			d4.setIcon(new ImageIcon("src/image/[65f].gif"));
			d4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[65f]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[65f].gif"));
				}
			});
		}
		return d4;
	}

	/**
	 * This method initializes d5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getD5() {
		if (d5 == null) {
			d5 = new JButton();
			d5.setBackground(Color.white);
			d5.setToolTipText("Î¢Ð¦");
			d5.setIcon(new ImageIcon("src/image/[gg].gif"));
			d5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[gg]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[gg].gif"));
				}
			});
		}
		return d5;
	}

	/**
	 * This method initializes f1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getF1() {
		if (f1 == null) {
			f1 = new JButton();
			f1.setBackground(Color.white);
			f1.setToolTipText("±Õ×ì");
			f1.setIcon(new ImageIcon("src/image/[hfe].gif"));
			f1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[hfe]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[hfe].gif"));
				}
			});
		}
		return f1;
	}

	/**
	 * This method initializes f2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getF2() {
		if (f2 == null) {
			f2 = new JButton();
			f2.setBackground(Color.white);
			f2.setToolTipText("Ë¥");
			f2.setIcon(new ImageIcon("src/image/[hgfr].gif"));
			f2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[hgfr]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[hgfr].gif"));
				}
			});
		}
		return f2;
	}

	/**
	 * This method initializes f3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getF3() {
		if (f3 == null) {
			f3 = new JButton();
			f3.setBackground(Color.white);
			f3.setToolTipText("Î¯Çü");
			f3.setIcon(new ImageIcon("src/image/[r4gr4].gif"));
			f3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[r4gr4]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[r4gr4].gif"));
				}
			});
		}
		return f3;
	}

	/**
	 * This method initializes f4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getF4() {
		if (f4 == null) {
			f4 = new JButton();
			f4.setBackground(Color.white);
			f4.setToolTipText("ÄÑ¹ý");
			f4.setIcon(new ImageIcon("src/image/[rev].gif"));
			f4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					in.setExpressioncode("[rev]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[rev].gif"));
				}
			});
		}
		return f4;
	}

	/**
	 * This method initializes f5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getF5() {
		if (f5 == null) {
			f5 = new JButton();
			f5.setBackground(Color.white);
			f5.setToolTipText("¿ÞÆü");
			f5.setIcon(new ImageIcon("src/image/[rt4].gif"));
			f5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExpressionIndex in = new ExpressionIndex();
					in.setFirstindex(main.messageSend.getCaretPosition());
					//   in.setFirstindex(main.messageSend.getText());
					in.setExpressioncode("[rt4]");
					main.messageWithEmoji.add(in);
					main.messageSend.insertIcon(new ImageIcon("src/image/[rt4].gif"));
				}
			});
		}
		return f5;
	}
} 
