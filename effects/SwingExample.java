package org.effects;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;

//import com.sun.java.swing.plaf.nimbus.*;

import org.filter.*;

class FileChooser implements ActionListener{
	String directory,fileName;
		public void actionPerformed(ActionEvent evt){
			JFileChooser choose=new JFileChooser();
			int returnValue = choose.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File select = choose.getSelectedFile();
				directory=select.getPath();
				fileName=select.getName();
				SwingExample.setDirectory(directory);
			}
	        else{
	        	
	        }
		}
}

public class SwingExample{
	JFrame jfrm;
	JLabel jl,status;
	JPanel jpl1,jpl2;
	JMenuBar jm;
	ImageIcon imic;
	BufferedImage srcimg;
	BufferedImage dest;
	Graphics gres;
	static String directory;
	String fileName;
	static boolean ready=false;
	SwingExample(String title){
		try {
			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			 //UIManager.put("control", Color.WHITE);
			 
			 UIManager.put("Button.background",new Color(0,200,0));
			 UIManager.put("Button.font", new Font("Arial Black",Font.BOLD,12));
			 UIManager.put("Button.textForeground", new Color(0,0,100));
			 UIManager.put("MenuBar.background",new Color(0,200,0));			 
		 } 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		jfrm = new JFrame(title);
		jfrm.setResizable(false);
		jfrm.setSize(800, 600);
		JFrame.setDefaultLookAndFeelDecorated(true);
		jfrm.setLocationRelativeTo(null);
		jm=new JMenuBar();
		jm.setBackground(new Color(0,0,200));
		JMenu jfile=new JMenu("File");
		jm.add(jfile);
		JMenu jName=new JMenu("Amitava Ghosh Production");
		jName.setEnabled(false);
		jm.add(Box.createHorizontalGlue());
		jm.add(jName);
		jpl1=new JPanel();
		jpl2=new JPanel();
		jpl2.setBackground(new Color(0,0,100));
		jpl2.setMaximumSize(new Dimension(150,500));
		jpl1.setPreferredSize(new Dimension(500,500));
		jpl2.setLayout(new GridLayout(15,0));
		JMenuItem jChose=new JMenuItem("Open");
		jChose.setPreferredSize(new Dimension(150,20));
		jChose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				BufferedImage img=null,dimg=null;
				int i;
				JFileChooser choose=new JFileChooser();
				choose.setAcceptAllFileFilterUsed(false);
				String [] suffices=ImageIO.getReaderFileSuffixes();
				for(i=0;i<suffices.length;i++){
				    FileFilter filter = new FileNameExtensionFilter(suffices[i] + " files", suffices[i]);
				    choose.addChoosableFileFilter(filter);
				}
				int returnValue = choose.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File select = choose.getSelectedFile();
					
					fileName=select.getAbsolutePath();
					SwingExample.setDirectory(fileName);
					try{
						img=ImageIO.read(new File(fileName));
						dimg=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TRANSLUCENT);
						
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(jfrm,"Error in Opening File","Inane error",
			        		    JOptionPane.ERROR_MESSAGE);
						}
					imic=new ImageIcon(img);
					jl=new JLabel(null,new ImageIcon(dimg),JLabel.CENTER);
					jpl1.add(jl,BorderLayout.CENTER);
					srcimg=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB );
					jl.setIcon(new ImageIcon(resize(img,jpl1.getWidth(),jpl1.getHeight())));
					jpl1.revalidate();
					jpl1.repaint();
				}
		        else{
		        	
		        }
			}
		});
		
		JMenuItem jSave=new JMenuItem("Save");
		jSave.setPreferredSize(new Dimension(150,20));
		jSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		        try{
		        	if(dest!=null){
		        		ImageIO.write(dest, "png", new File("output.png"));
		        		JOptionPane.showMessageDialog(jfrm,
		        		    "Succesfull");
		        	}
		        	else
		        		throw new Exception();
		        }
		        catch(Exception ex){
		        	JOptionPane.showMessageDialog(jfrm,
		        		    "Error in Saving File",
		        		    "Inane error",
		        		    JOptionPane.ERROR_MESSAGE);
		        }
		    }
		    });
		JMenuItem jMast=new JMenuItem("About");
		jMast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(jfrm,
	        		    "The Output File is in\n" +
	        		    " the Same Directory as the\n" +
	        		    "Application\n" +
	        		    "Image Filter Application\n--Amitava Ghosh");
			}
		});
		jfile.add(jChose);
		jfile.add(jSave);
		jfile.add(jMast);
		jfrm.setJMenuBar(jm);
		JScrollPane jp=new JScrollPane(jpl2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp.setPreferredSize(new Dimension(150,500));
		//jfrm.setJMenuBar(jm);
		
		
		JSplitPane sp= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpl1,jp);
		jfrm.getContentPane().add(sp,BorderLayout.CENTER);
		jfrm.setVisible(true);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void setDirectory(String dir){
		directory=dir;
	}
	public void makeButtons(){
		JButton jAmber=new JButton("Amber");
		JButton jColblot=new JButton("Blot");
		JButton jCrossProcs=new JButton("CrossProcess");
		JButton jInked=new JButton("Inked");
		JButton jLomo=new JButton("Lomo");
		JButton jPotra=new JButton("Potra");
		JButton jSandBlast=new JButton("SandBlast");
		JButton jSpeckel=new JButton("Speckel");
		JButton jSulpha=new JButton("Sulpha");
		JButton jSunset=new JButton("Sunset");
		JButton jOriginal=new JButton("Original");
		jAmber.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				AmberEffectEvent(evt);
				}
			});
	jpl2.add(jAmber);
	
	jColblot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					ColorBlotEvent(evt);
					}
				});
	jpl2.add(jColblot);
			
	jCrossProcs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
						CrossProcessEvent(evt);
						}
					});
	jpl2.add(jCrossProcs);
			
	jInked.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
			InkedEvent(evt);
			}
		});
	jpl2.add(jInked);
	
	jLomo.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		LomoEvent(evt);
		}
	});
	jpl2.add(jLomo);

	jPotra.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		PotraEvent(evt);
		}
	});
	jpl2.add(jPotra);

	jSandBlast.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		SandBlastEvent(evt);
		}
	});
	jpl2.add(jSandBlast);

	jSpeckel.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		SpeckelEvent(evt);
		}
	});
	jpl2.add(jSpeckel);

	jSulpha.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		SulphaEvent(evt);
		}
	});
	jpl2.add(jSulpha);

	jSunset.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		SunsetEvent(evt);
		}
	});
	jpl2.add(jSunset);
	
	jOriginal.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt){
		OriginalEvent(evt);
		}
	});
	jpl2.add(jOriginal);
	jfrm.repaint();

}

	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	
	public void setSource(){
		int width, height;
		gres=null;
		Graphics g=srcimg.createGraphics();
		imic.paintIcon(null, g, 0, 0);
		g.dispose();
		ImageData im=new ImageData(srcimg);
		width=(im.getImage()).getWidth();
		height=(im.getImage()).getHeight();
		dest= new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);		
	}

	public void AmberEffectEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin = null;
		fin=new AmberEffect().Amber(fin,dest,srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void ColorBlotEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin = null;
		fin=new ColorBlotEffect().ColorBlot(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));

	}
	public void CrossProcessEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin = null;
		fin=new CrossProcessEffect().CrossProcess(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void InkedEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin = null;
		fin=new InkedYellowEffect().InkedYellow(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void LomoEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new LomoEffect().Lomo(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void OriginalEvent(ActionEvent evt){
		this.setSource();
		gres=dest.createGraphics();
		gres.drawImage(srcimg, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(srcimg,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void PotraEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new PotraFilm().Potra(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	
	public void SandBlastEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new SandBlastEffect().SandBlast(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void SpeckelEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new SpeckelEffect().Speckel(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void SulphaEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new SulphaEffect().Sulpha(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	public void SunsetEvent(ActionEvent evt){
		this.setSource();
		BufferedImage fin=null;
		fin=new SunsetEffect().Sunset(fin, dest, srcimg);
		gres=dest.createGraphics();
		gres.drawImage(fin, 0, 0, null);
		jl.setIcon(new ImageIcon(resize(dest,jpl1.getWidth(),jpl1.getHeight())));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						SwingExample se = new SwingExample("Flimters");
						se.makeButtons();
					}
				});
	}

}
