import java.awt.*;
import java.awt.event.*;
class WindowDestroyer extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
public class BallThreadAssig_20171300 extends Frame implements ActionListener {
	private Canvas canvas;
	public BallThreadAssig_20171300() {
		canvas=new Canvas();
		add("Center",canvas);
		Panel p=new Panel();
		Button s=new Button("start");
		Button c=new Button("Close");
		p.add(s);p.add(c);
		s.addActionListener(this);
		c.addActionListener(this);
		add("South",p);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame f=new BallThreadAssig_20171300();
		f.setSize(400,300);
		WindowDestroyer listner=new WindowDestroyer();
		f.addWindowListener(listner);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="start") {
			Ball b=new Ball(canvas);
			b.start();
		}
		else if(e.getActionCommand()=="Close") {
			System.exit(0);
		}
		
	}
	private Canvas box;


	class Ball extends Thread{
		//228   334
		private int XSIZE[]= {20,20,20,20,20};
		private int YSIZE[]= {20,20,20,20,20};
		private int x[]=new int[5];
		private int y[]=new int[5];
		private int dx[]= {-2,2,2,-2,2};
		private int dy[]= {-2,2,-2,2,0};
		public int allow_crac=0;
		public Ball(Canvas c) {
			box=c;

		}
		public int crak() {
			int startx,sstartx;
			int starty,sstarty;
			for(int i=0;i<4;i++) {
				startx=x[i]-XSIZE[i]/2;
				starty=y[i]-YSIZE[i]/2;
				for(int j=i+1;j<5;j++) {
					sstartx=x[j]-XSIZE[j]/2;
					sstarty=y[j]-YSIZE[j]/2;
					if(((sstartx>=startx&&sstartx<=startx+XSIZE[i])&&(sstarty>=starty&&sstarty<=starty+YSIZE[i]))|| //x1이 50이상 100이하이고 y1이 50이상 100이하 혹은
					       ((sstartx+XSIZE[j]>=startx&&sstartx+XSIZE[j]<=startx+XSIZE[i])&&(sstarty+YSIZE[j]>=starty&&sstarty+YSIZE[j]<=starty+YSIZE[i]))|| //x2가 50이상 100이하 y2가 50이상 100이하 혹은
					      ((sstartx<=startx&&sstarty<=starty)&&(sstartx+XSIZE[j]>=startx+XSIZE[i]&&sstarty+YSIZE[j]>=starty+YSIZE[i]))) //x1과 y1이 50이하이고 x2와 y2가 100이상
					{
						System.out.println("ddd");
						return 1;
					}
				}
			}
			return 0;
		}
		public void draw() {
			Graphics g=box.getGraphics();
			Dimension d=box.getSize();
			
			for(int i=0;i<5;i++) {
				x[i]=d.width/2;
				y[i]=d.height/2;
				g.fillOval(x[i],y[i],XSIZE[i],YSIZE[i]);
			}
			g.dispose();
			
		}
		public void move() {
			Graphics g=box.getGraphics();
			g.setXORMode(box.getBackground());
			for(int i=0;i<5;i++) {
				if(x[i]>=-500) {
					g.fillOval(x[i],y[i],XSIZE[i],YSIZE[i]);

					x[i]+=dx[i];y[i]+=dy[i];
				}
			}
			Dimension d=box.getSize();

			for(int i=0;i<5;i++) {
				if(XSIZE[i]!=1) {
				if(x[i]<0) {
					x[i]=0;dx[i]=-dx[i];
				}
				if(x[i]+XSIZE[i]>=d.width) {
					x[i]=d.width-XSIZE[i];
					dx[i]=-dx[i];
				}
				if(y[i]<0) {
					y[i]=0;dy[i]=-dy[i];
				}
				if(y[i]+YSIZE[i]>=d.height) {
					y[i]=d.height-YSIZE[i];
					dy[i]=-dy[i];
				}


				g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
				}
			}
			int startx,sstartx;
			int starty,sstarty;
			int tox,toy;
			for(int ii=0;ii<4;ii++) {
				startx=x[ii]-XSIZE[ii]/2;
				starty=y[ii]-YSIZE[ii]/2;
				for(int j=ii+1;j<5;j++) {
					sstartx=x[j]-XSIZE[j]/2;
					sstarty=y[j]-YSIZE[j]/2;
					if(((sstartx>=startx&&sstartx<=startx+XSIZE[ii])&&(sstarty>=starty&&sstarty<=starty+YSIZE[ii]))|| //x1이 50이상 100이하이고 y1이 50이상 100이하 혹은
					       ((sstartx+XSIZE[j]>=startx&&sstartx+XSIZE[j]<=startx+XSIZE[ii])&&(sstarty+YSIZE[j]>=starty&&sstarty+YSIZE[j]<=starty+YSIZE[ii]))|| //x2가 50이상 100이하 y2가 50이상 100이하 혹은
					      ((sstartx<=startx&&sstarty<=starty)&&(sstartx+XSIZE[j]>=startx+XSIZE[ii]&&sstarty+YSIZE[j]>=starty+YSIZE[ii]))) //x1과 y1이 50이하이고 x2와 y2가 100이상
					{
						allow_crac++;
						//System.out.println(allow_crac);
						if(allow_crac>50) {
							if(x[ii]>-500)
								g.fillOval(x[ii], y[ii], XSIZE[ii], YSIZE[ii]);
							if(x[j]>-500)
								g.fillOval(x[j], y[j], XSIZE[j], YSIZE[j]);
						
							XSIZE[ii]/=2;
							YSIZE[ii]/=2;
							XSIZE[j]/=2;
							YSIZE[j]/=2;
							tox=dx[ii]+dx[j];
							toy=dy[ii]+dy[j];
							dx[ii]=tox+dx[j]/2;
							dx[j]=tox+dx[ii]/2;
							dy[ii]=dy[j]/2+toy;
							dy[j]=dy[ii]/2+toy;
							if(XSIZE[ii]<=1) {
								if(x[ii]>-500) {
			
									g.fillOval(x[ii], y[ii], XSIZE[ii], YSIZE[ii]);
									g.fillOval(x[j], y[j], XSIZE[j], YSIZE[j]);
									//repaint();
								}
								x[ii]=-1000*(ii+1);
								y[ii]=-1000*(ii+1);

							}
							if(XSIZE[j]<=1)
							{
								if(x[j]>-500) {
									g.fillOval(x[ii], y[ii], XSIZE[ii], YSIZE[ii]);
									g.fillOval(x[j], y[j], XSIZE[j], YSIZE[j]);
									//repaint();
								}
								x[j]=-1000*(j+1);
								y[j]=-1000*(j+1);

							}
							else {
								g.fillOval(x[ii], y[ii], XSIZE[ii], YSIZE[ii]);
								g.fillOval(x[j], y[j], XSIZE[j], YSIZE[j]);
							}
						}
						//System.out.println(x[ii]);
						}
					
				
				//g.fillOval(x[ii], y[ii], XSIZE[ii], YSIZE[ii]);
				}
				if(x[ii]<-500)
				//System.out.println(x[ii]);
			}
			g.dispose();
			//box.
		}
		public void run() {
			draw();
			for(int i=0;;i++) {
				move();
				try {
					Thread.sleep(10);
				}
				catch(InterruptedException e) {
				}
			}
		}
	}
}


