/*  Strojno uèenje Projektni zadatak 
	Projekt je moguæe analizirati u Visual Studio J#	
	Dario Juriæ  Pametne crtice
	d:  
	cd d:\Crtice
	javac Crtice.java
	java  Crtice
*/

import java.util.*;
import java.io.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

abstract class Igrac 
{ } 

class QIgrac extends Igrac 					
{
	static final float gama_st = 0.5f;
	float gama = gama_st;
	int st, sn; 
	int m, n, bod;
	float Q[][];

	public QIgrac (int dm, int dn, float gam) 
	{
		Q = new float[dm*dn+1][dm*dn+1];		 
		gama = gam;
		m=dm; n=dn; bod=0;
		for(int i=0;i<=dm*dn;i++)
		{  for(int j=0;j<=dm*dn;j++)
			{
				Q[i][j]=0;
			}
		}			
	}
	
	
	public QIgrac (int dm, int dn) 
	{
		Q = new float[dm*dn+1][dm*dn+1];
		gama = gama_st;
		m=dm; n=dn; bod=0;
		for(int i=0;i<=dm*dn;i++)
		{  for(int j=0;j<=dm*dn;j++)
			{
				Q[i][j]=0;
			}
		}			
	}
	
	
	 float Qmax(int s, Vector<Integer> akc) 
	 {
		float max=0, tr=0;		
		if (akc.size()>0 )
		{
		for(int a: akc) 
			{
			 tr=Q[s][a];
			 if(tr>max) { max=tr; }
			}
		}
		return max;
	 }
	
	// potez (QIgrac)
	public TreeSet<Integer> potez(Igra I)
	 {
		TreeSet<Integer> pot=new TreeSet<Integer>();
		
		if(I.PL.LN.size()<=I.PL.ln) 
		{
		
			int ts, tn, vs;
			int va, id, b;
			vs = I.PL.MT.size();  
			Random sluc = new Random();
		
		
			if(st==0 || !I.PL.MT.contains(st))
			{ 
				st = I.PL.MT.elementAt(sluc.nextInt(vs)%vs);	
			}
		
			Vector<Integer> akc = new Vector<Integer>();
			akc=I.PL.moguce(st); 
			va=akc.size();
				
			if(va >= 1) 
			{   
				id=sluc.nextInt(va)%va;
				sn = akc.elementAt(id); 
			}
			else do
			{
				st = I.PL.MT.elementAt(sluc.nextInt(vs)%vs); 
				akc=I.PL.moguce(st); 
				va=akc.size();
			 if(va>0) 
				{
				id=sluc.nextInt(va)%va;
				sn = akc.elementAt(id); 
				}
			} while( akc.size()<1);
		
			pot.add(st);
			pot.add(sn);
		
			I.PL.LN.add(pot);	
	
			float r=0,q=0;
			b=I.PL.bod(pot);
			r=(float)b;
			bod+=b;
			
			if( bod>=I.PL.pobj ) { r=1000; }
			
			if(!(I.PL.moguc(st)) && !(I.PL.moguc(sn)) ) 
			{ I.PL.MT.removeAll(pot); }  
			
			akc=I.PL.moguce(sn); // sve akcije iz sn
			if(akc.size()>0) q=r+gama*Qmax(sn,akc); else q=r;
		
			Q[st][sn]=q;	
		
			st=sn; 
			
			} 
		
		return pot; 
	 }
	
}	



//__________________ SIgrac __________________

class SIgrac extends Igrac   					
{
	int st; // trenutno stanje
	int sn; // novo stanje
	int m, n, bod;
	
	public SIgrac (int dm, int dn) 
	{
		m=dm; n=dn; bod=0;
	}
	
		
	// potez (SIgrac)
	public TreeSet<Integer> potez(Igra I)
	 {
		TreeSet<Integer> pot=new TreeSet<Integer>();

		int ts, tn, vs;
		int va, id;
		
		Random sluc = new Random();
		
		vs = I.PL.MT.size();  
		if( vs>0 )
		{ 
			st = I.PL.MT.elementAt(sluc.nextInt(vs)%vs);	
		}
		
		Vector<Integer> akc = new Vector<Integer>();
		akc=I.PL.moguce(st); 
		va=akc.size();
				
		if(va >= 1) 
		{ 
			id=sluc.nextInt(va)%va;
			sn = akc.elementAt(id); 
		}
		else do
		{
			if(I.PL.LN.size()<=I.PL.ln) 
			{ 
			  st = I.PL.MT.elementAt(sluc.nextInt(vs)%vs); 
			  akc=I.PL.moguce(st); 
			  va=akc.size();
			  if(va>0) { id=sluc.nextInt(va)%va;
			  sn = akc.elementAt(id); }
			}
		} while( akc.size()<1);
		
		pot.add(st);
		pot.add(sn);
		
		if(!(I.PL.moguc(st)) && !(I.PL.moguc(sn)) ) 
		{ I.PL.MT.removeAll(pot); }  
		
		I.PL.LN.add(pot);	
	
		int r=0;
		r=I.PL.bod(pot);
		bod+=r;
		
		return pot;
	 }
} 



//__________________ CIgrac __________________

class CIgrac extends Igrac   					
{
	int st; // trenutno stanje
	int sn; // novo stanje 
	int m, n, bod;
	
	public CIgrac (int dm, int dn) 
	{
		m=dm; n=dn; bod=0;
	}
	
		
	// potez (CIgrac)
	public TreeSet<Integer> potez(Igra I)
	 {
		TreeSet<Integer> ak = new TreeSet<Integer>();
		int st, sn;
		boolean ok=false;
		
		Scanner keyboard = new Scanner(System.in);
		do { 
			// System.out.println("\n Unesite potez:  ");
			st = keyboard.nextInt();
			sn = keyboard.nextInt();
			if(st==0 || sn==0 ) { System.exit(0); }
			ak.add(st);ak.add(sn);
			if( !I.PL.LN.contains(ak) && st!=sn && I.PL.moguc(st) &&  I.PL.moguc(sn) ) 
			{ 
				int dx=Math.abs( I.PL.X(st)-I.PL.X(sn));
				int dy=Math.abs( I.PL.Y(st)-I.PL.Y(sn));
				if( dx<=1 && dy<=1 && (dx+dy)<=1 ) 
				{ 
					I.PL.LN.add(ak);
					ok=true; 
				}
			}	
		} while (ok!=true);
		
		int r=0;
		r=I.PL.bod(ak);
		bod+=r;
		
		return ak;
	 }
} 




// ___________________  class Ploca  ____________________

class Ploca
{
	static int start_m = 4;
	static int start_n = 4;
	
	Vector<Integer> MT = new Vector<Integer>(); 		// SKUP MOGUÆIH mn STANJA (dinamièki se smanjuje)
	Set<Set<Integer>> LN = new HashSet<Set<Integer>>(); // SKUP poteza <> linija <> akcija (dinamièki raste)
	
	int m = start_m;		// širina ploèe
	int n = start_n;		// visina ploèe
	int mn = m*n; 			// broj stanja (toèaka)
	int ln=m*(n-1)+(m-1)*n; // broj akcija (linija) 
	int kv=(m-1)*(n-1); 	// broj kvadrata
	int pobj=(kv/2+1);		// broj bodova za pobjedu
	
	
	public Ploca (int dm, int dn) 
	{  								
		m = dm; n = dn; mn = m*n; ln=m*(n-1)+(m-1)*n; int kv=(m-1)*(n-1);  pobj=(kv/2+1);							
		for(int i = 1; i<=mn; i++) { MT.add(i);} 
		
	}
	

	public Ploca () 
	{
		m = start_m; n = start_n;  mn = m*n; ln=m*(n-1)+(m-1)*n; kv=(m-1)*(n-1);  pobj=(kv/2+1);		 					
		for(int i = 1; i<=mn; i++) { MT.add(i);} 
	}
	
	public int T(int x, int y)
	{
		return (x+m*(y-1));
	}
	
	public int X(int t)
	{
		 return ((t-1)%m+1);
	}
	
	public int Y(int t)
	{
		 return  ((t-1)/m+1 );
	}
	
	public boolean pripada(int x, int y)
	{
			if ( x>=1 && x<=m && y>=1 && y<=n  ) return true; 
			else return false; 
	}
	
	
	public boolean pripada(int t) 
	{	
			if ( t>=1 && t<=m*n  ) return true; 
			else return false; 
	}

					
	public Set<Integer> susjedi(int t)
	{
		Set<Integer> susj = new HashSet<Integer>();
		
		int x, xs, y, ys;
		x=X(t);
		y=Y(t);
		
		xs=x+1; ys=y;
		if (pripada(xs,ys)) susj.add(T(xs,ys));
		xs=x-1; 
		if (pripada(xs,ys)) susj.add(T(xs,ys));
		xs=x; ys=y+1; 
		if ( pripada(xs,ys)) susj.add(T(xs,ys));
		ys=y-1; 
		if (pripada(xs,ys)) susj.add(T(xs,ys));
			
		return susj;		
	} 
	
	
	public boolean moguc(int a)   // tocka je moguca za potez 
	{
		if ( pripada(a)) 
		{   
			int x=X(a),y=Y(a),b;
			Set<Integer> ab = new HashSet<Integer>(Arrays.asList(new Integer[]{a}));
			
			if ( pripada(x,y+1))
			{ 	b=T(x,y+1); ab.add(b);
				if( !LN.contains(ab)) { return true; }
				ab.remove(b);		
			}	
			if (pripada(x,y-1))
				{ 	b=T(x,y-1); ab.add(b);
					if( !LN.contains(ab)) { return true; }
					ab.remove(b);		
				}	
			if (pripada(x+1,y))
				{ 	b=T(x+1,y); ab.add(b);
					if( !LN.contains(ab)) { return true; }
					ab.remove(b);		
				}	
			if (pripada(x-1,y))
				{ 	b=T(x-1,y); ab.add(b);
					if( !LN.contains(ab)) { return true; }
					ab.remove(b);		
				}	
		}
		return false; 
	}
	
	
	public Vector<Integer> moguce(Integer a)   // Svi moguæi potezi iz tocke a
	{
		Vector<Integer> mt = new Vector<Integer>();
		if(moguc(a))
		{   
			Set<Integer> st = new HashSet<Integer>(susjedi(a));
			Set<Integer> ab = new HashSet<Integer>(Arrays.asList(new Integer[]{a}));
			for(int b : st) 
			{ ab.add(b);
			  if( !LN.contains(ab)) { mt.addElement(b); }
			  ab.remove(b);	
			}
		}	
		return mt; 
	}
	
	
	boolean sadrzi3l(int a, int b, int c, int d)
	{
			Set<Integer> ac = new HashSet<Integer>(Arrays.asList(new Integer[]{a,c}));
			Set<Integer> bd = new HashSet<Integer>(Arrays.asList(new Integer[]{b,d}));
			Set<Integer> cd = new HashSet<Integer>(Arrays.asList(new Integer[]{c,d}));
			Set<Set<Integer>> L3 = new HashSet<Set<Integer>>();
			L3.add(ac); L3.add(cd); L3.add(bd);
			if (LN.containsAll(L3)) return true; 
			else return false; 
	}
	
	public int potez ( int a, int b )
	{
		int r=0;			
		if(  pripada(a) && pripada(b) && a!=b)
		{
			Set<Integer> ab = new HashSet<Integer>(Arrays.asList(new Integer[]{a,b}));
			if(!LN.contains(ab)) 
			{ LN.add(ab); 
				if(!moguc(a)) {MT.remove(a);}
				if(!moguc(b)) {MT.remove(b);}
			} 
			else return -1;
			int xa=X(a),ya=Y(a),xb=X(b),yb=Y(b),c,d,xc,yc,xd,yd;								
			
			if(ya==yb) 
				{	// k^ 
					xc=xa;yc=ya+1;xd=xb;yd=yb+1;
					c=T(xc,yc); d=T(xd,yd);
					if(sadrzi3l(a,b,c,d)) { ++r; }
					// k¡
					yc=ya-1;yd=yb-1;
					c=T(xc,yc); d=T(xd,yd);
					if(sadrzi3l(a,b,c,d)) { ++r; }					
				}			
			else if(xa==xb) 
				{	// k< 
					xc=xa-1;yc=ya;xd=xb-1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);
					if(sadrzi3l(a,b,c,d)) { ++r; }
					// k>
					xc=xa+1;yc=ya;xd=xb+1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);
					if(sadrzi3l(a,b,c,d)) { ++r; }
				}			
		}
		return r;
	} // kraj ( potez ) 
		
	
	public int bod( Set<Integer> ab )
	{
		LN.add(ab); // Odigran je moguæ potez
		
		Iterator<Integer> it = ab.iterator();
		int a = (int)it.next(), b = (int)it.next(), r=0;			
		if( ab.size()==2 && pripada(a) && pripada(b) )
		{
			int xa=X(a),ya=Y(a),xb=X(b),yb=Y(b),c,d,xc,yc,xd,yd;								
				if(ya==yb) //  Ako ( ab = horizontalna ) -> k^, k¡
				{	// k^ 
					xc=xa;yc=ya+1;xd=xb;yd=yb+1;
					c=T(xc,yc); d=T(xd,yd);
					
					if(sadrzi3l(a,b,c,d))
					{ ++r; }
					
					// k¡
					yc=ya-1;yd=yb-1;
					c=T(xc,yc); d=T(xd,yd);
					
					if(sadrzi3l(a,b,c,d))
					{ ++r; }					
				}			
				else if(xa==xb) //  Ako ( ab = vertikalna ) -> k<, k>
				{	// k< 
					xc=xa-1;yc=ya;xd=xb-1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);
										
					if(sadrzi3l(a,b,c,d))
					{  ++r; }
					
					// k>
					xc=xa+1;yc=ya;xd=xb+1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);
					
					if(sadrzi3l(a,b,c,d))
					{ ++r; }					
				}			
		}
		return r;
	} // kraj ( bod ) 


	boolean zauzeta( TreeSet<Integer> ab )
	{
		boolean rj=false, r1=false, r2=false; 
		int a = ab.first(), b = ab.last();			
		int xa=X(a),ya=Y(a),xb=X(b),yb=Y(b),c,d,xc,yc,xd,yd;								
		if( ab.size()==2 && pripada(a) && pripada(b) )
		{
				if(ya==yb) //  Ako ( ab = horizontalna ) -> k^, k¡
				{	// k^ 
					xc=xa;yc=ya+1;xd=xb;yd=yb+1;
					c=T(xc,yc); d=T(xd,yd);
					
					 if(yc<1 || yd<1 ) { r1=true; }		
					 else if(sadrzi3l(a,b,c,d) )
					 { r1=true; }
					
					// k¡
					yc=ya-1;yd=yb-1;
					c=T(xc,yc); d=T(xd,yd);
					
					if(yc<1 || yd<1) { r2=true; }		
					else if(sadrzi3l(a,b,c,d) )
					{ r2=true; }					
					
					rj=r1&&r2;
				}			
				else if(xa==xb) //  Ako ( ab = vertikalna ) -> k<, k>
				{	// k< 
					xc=xa-1;yc=ya;xd=xb-1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);

					if( xc<1 || xd<1 )
					{  r1=true; }									
					else if(sadrzi3l(a,b,c,d) )
					{  r1=true; }
					
					// k>
					xc=xa+1;yc=ya;xd=xb+1;yd=yb;
					c=T(xc,yc); d=T(xd,yd);
					
					if(sadrzi3l(a,b,c,d)  )
					{ r2=true; }					
					else if( xc>m || xd>m  )
					{  r1=true; }					
					rj=r1&&r2;
				}			
		}
		return rj;
	} // kraj ( zauzeta ) 
		
} // kraj ( class Ploca ) 



// _____________________  class Igra  _________________________

class Igra 
{	
	static int start_m = 4;
	static int start_n = 4;
		
	Ploca PL = new Ploca(start_m,start_n);
	
	Igrac I1;
	Igrac I2;
	
	public Igra( int m , int n )
	{
		PL = new Ploca(m,n);
	}
	
	public Igra(Igrac I01, Igrac I02, int m , int n )
	{
		I1=I01;
		I2=I02;
		PL = new Ploca(m,n);
	}

}  // kraj ( class Igra )



class Crtez1 extends JFrame 
{
	  static final long serialVersionUID = 17L;
	  
	  public Crtez1() 
	  {
		setTitle(" PAMETNE CRTICE ");
		setSize(600,600); 
		setLocation(0,10); 
		setBackground(Color.white);
	  }
}	

class Crtez2 extends JFrame 
{
	  static final long serialVersionUID = 27L;
	  public Crtez2() 
	  {
		setTitle(" PAMETNE CRTICE ");
		setSize(600,600); 
		setLocation(600,10); 
		setBackground(Color.white);
	  }
}	

class Crtez3 extends JFrame 
{
	  static final long serialVersionUID = 37L;
	  public Crtez3() 
	  {
		setTitle(" PAMETNE CRTICE ");
		setSize(600,600); 
		setLocation(1200,10); 
		setBackground(Color.white);
	  }
}	

class Situacija1 extends JApplet 
{
	static final long serialVersionUID = 7L;
	int br, m, n;
	float gama1, gama2; 
	
	QIgrac Q1, Q2;
	
	Igra IG1;
		
    public void paint(Graphics g) 
	{
				
		TreeSet<Integer> pot=new TreeSet<Integer>(); 
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i=1; i<=br; i++ )
		{			    
			IG1 = new Igra(Q1,Q2,Q1.m,Q1.n); 
		
			do 
			{
			pot=Q1.potez(IG1);
			nacrtaj(g2, IG1, pot, Color.blue);
		
			pot=Q2.potez(IG1);		
			nacrtaj(g2, IG1, pot, Color.magenta);
			
			} while (IG1.PL.LN.size()<IG1.PL.ln);
	
		rezultat(g2, Q1, Q2);

		}
    }
	
	public Situacija1()
	{
		br=10;
		m=4;
		n=4;
	    gama1=(float)0.8;
		gama2=(float)0.3; 
		Q1 = new QIgrac(m,n,gama1);
		Q2 = new QIgrac(m,n,gama2);
	}
	
	
	public Situacija1(QIgrac Q01, QIgrac Q02, int b1 )
	{ 
		Q1=Q01;
		Q2=Q02;
		br=b1; 
		m=Q1.m;
		n=Q1.n;
	}		
	
	void nacrtaj(Graphics2D g2,  Igra IG1, TreeSet<Integer> pot, Color boja )
	{
		int sirina=getSize().width /( IG1.PL.m+1);
		int visina=getSize().height/(IG1.PL.n+1);
		int x1,y1,x2,y2;
		
		g2.setPaint(boja);
		g2.setStroke(new BasicStroke(10));
		
		if(pot.size() == 2)
		{ 	x1=IG1.PL.X(pot.first());
			y1=IG1.PL.Y(pot.first());
			x2=IG1.PL.X(pot.last());
			y2=IG1.PL.Y(pot.last());		
		
			g2.draw(new Line2D.Double(sirina*x1, visina*y1, sirina*x2, visina*y2));
		}
	}
	
	void rezultat(Graphics2D g2, QIgrac Q1, QIgrac Q2)
	{
		g2.setColor(Color.white);
		g2.clearRect(0,0,getSize().width, 70);
		
		g2.setColor(Color.blue);
		Font font1 = new Font("Serif", Font.BOLD, 35 );
		Font font2 = new Font("Serif", Font.BOLD, 25 );
		Font font3 = new Font("Serif", Font.BOLD, 33 );
		g2.setFont(font1);
        g2.drawString(" Q",90,50);
		g2.setFont(font2);
		g2.drawString("1("+Q1.gama+") ",125,50);
		g2.setFont(font3);
		g2.drawString(" "+Q1.bod,185,50);
		g2.setFont(font2);
		g2.drawString(":",285,45);
		g2.setColor(Color.magenta);
		g2.setFont(font1);
        g2.drawString(" Q",295,50);
		g2.setFont(font2);
		g2.drawString("2("+Q2.gama+") ",335,50);
		g2.setFont(font3);
		g2.drawString(" "+Q2.bod,395,50);
	}
}


// ____________________ Situacija2 ____________________

class Situacija2 extends JApplet 
{
	static final long serialVersionUID = 77L;
	 int br, m, n;
	 float gama1; 
	
	QIgrac I1;
	CIgrac I2;
	
	Igra IG2;
	
    public void paint(Graphics g) 
	{
				
		TreeSet<Integer> pot=new TreeSet<Integer>(); 
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i=1; i<=br; i++ )
		{			    
		IG2 = new Igra(I1,I2,I1.m,I1.n);
		nacrtaj0(g2,IG2);
		
		do {
			pot=I1.potez(IG2);		
			nacrtaj(g2, IG2, pot, Color.magenta);
		
			rezultat( g2, I1, I2);
		
			pot=I2.potez(IG2);		
			nacrtaj(g2, IG2, pot, Color.blue);

			} while (IG2.PL.LN.size()<IG2.PL.ln);
		}  rezultat(g2, I1, I2);
    }
	
	public Situacija2()
	{
		br=10;
		m=4;
		n=4;
	    gama1=(float)0.5;
		I1 = new QIgrac(m,n,gama1);
		I2 = new CIgrac(m,n);
	}
	
	public Situacija2( QIgrac I01, CIgrac I02, int b1 )
	{ 
		I1=I01;
		I2=I02;
		br=b1; 
		m=I1.m;
		n=I1.n;
	}
	
	
	void nacrtaj(Graphics2D g2,  Igra IG2, TreeSet<Integer> pot, Color boja )
	{
		int sirina=getSize().width /(IG2.PL.m+1);
		int visina=getSize().height/(IG2.PL.n+1);
		int x1,y1,x2,y2;
		
		g2.setPaint(boja);
		g2.setStroke(new BasicStroke(10));
		
		if(pot.size() == 2)
		{ 	x1=IG2.PL.X(pot.first());
			y1=IG2.PL.Y(pot.first());
			x2=IG2.PL.X(pot.last());
			y2=IG2.PL.Y(pot.last());		
		
			g2.draw(new Line2D.Double(sirina*x1, visina*y1, sirina*x2, visina*y2));
		}
	}
	
	void nacrtaj0(Graphics2D g2,  Igra IG2 )
	{
		int sirina=getSize().width /( IG2.PL.m+1);
		int visina=getSize().height/(IG2.PL.n+1);
		int x1,y1,x2,y2;
		
		g2.setPaint(Color.gray);
		g2.setStroke(new BasicStroke(1));
		
		for(int i=1;i<=IG2.PL.m;i++)
		{ 
		    x1=i; y1=1;
			x2=i; y2=IG2.PL.n;		
			g2.draw(new Line2D.Double(sirina*x1, visina*y1, sirina*x2, visina*y2));
		} 
		
		for(int j=1;j<=IG2.PL.n;j++)
		{ 
			x1=1; y1=j;
			x2=IG2.PL.m;
			y2=j;		
			g2.draw(new Line2D.Double(sirina*x1, visina*y1, sirina*x2, visina*y2));
		}	
	}

	
	void rezultat(Graphics2D g2, QIgrac I1, CIgrac I2)
	{
		g2.setColor(Color.white);
		g2.clearRect(0,0,getSize().width, 70);
		
		g2.setColor(Color.blue);
		Font font1 = new Font("Serif", Font.BOLD, 36 );
		Font font2 = new Font("Serif", Font.BOLD, 26 );
		Font font3 = new Font("Serif", Font.BOLD, 34 );
		g2.setFont(font1);
        g2.drawString(" C",130,50);
		g2.setFont(font3);
		g2.drawString(" "+I2.bod,210,50);
		g2.setFont(font2);
		g2.drawString(":",280,45);
		g2.setColor(Color.magenta);
		g2.setFont(font1);
        g2.drawString(" Q",300,50);
		g2.setFont(font2);
		g2.drawString("("+I1.gama+") ",340,50);
		g2.setFont(font1);
		g2.drawString(" "+I1.bod,400,50);
	}
}


// ____________________ Situacija3 ______________________

class Situacija3 extends JApplet 
{
	static final long serialVersionUID = 777L;
    
	 int br, m, n;
	 float gama1; 
	
	QIgrac I1;
	SIgrac I2;
	
	Igra IG3;
	
    public void paint(Graphics g) 
	{

		TreeSet<Integer> pot=new TreeSet<Integer>(); 
		Graphics2D g3 = (Graphics2D) g;
		
		for (int i=1; i<=br; i++ )
		{			    
		IG3 = new Igra(I1,I2,I1.m,I1.n);
		
		do {
		
			pot=I1.potez(IG3);		
			nacrtaj(g3, IG3, pot, Color.blue);
		
			pot=I2.potez(IG3);		
			nacrtaj(g3, IG3, pot, Color.magenta);
			
		} while (IG3.PL.LN.size()<IG3.PL.ln);
	
		rezultat( g3, I1, I2);
		
		}
    }
	
	public Situacija3()
	{
		br=10;
		m=4;
		n=4;
	    gama1=(float)0.5;
		I1 = new QIgrac(m,n,gama1);
		I2 = new SIgrac(m,n);
	}
	
	public Situacija3( QIgrac I01, SIgrac I02, int b1 )
	{ 
		I1=I01;
		I2=I02;
		br=b1; 
		m=I1.m;
		n=I1.n;
	}
	
	
	void nacrtaj(Graphics2D g3,  Igra IG3, TreeSet<Integer> pot, Color boja )
	{
		int sirina=getSize().width /( IG3.PL.m+1);
		int visina=getSize().height/(IG3.PL.n+1);
		int x1,y1,x2,y2;
		
		g3.setPaint(boja);
		g3.setStroke(new BasicStroke(8));
		
		if(pot.size() == 2)
		{ 	x1=IG3.PL.X(pot.first());
			y1=IG3.PL.Y(pot.first());
			x2=IG3.PL.X(pot.last());
			y2=IG3.PL.Y(pot.last());		
		
			g3.draw(new Line2D.Double(sirina*x1, visina*y1, sirina*x2, visina*y2));
		}
	}
	
	
	void rezultat(Graphics2D g3, QIgrac I1, SIgrac I2)
	{
		g3.setColor(Color.white);
		g3.clearRect(0,0,getSize().width, 70);
		
		g3.setColor(Color.blue);
		Font font1 = new Font("Serif", Font.BOLD, 36 );
		Font font2 = new Font("Serif", Font.BOLD, 33 );
		Font font3 = new Font("Serif", Font.BOLD, 26 );
		g3.setFont(font1);
		
        g3.setFont(font1);
        g3.drawString(" Q",90,50);
		g3.setFont(font3);
		g3.drawString("1("+I1.gama+") ",130,50);
		g3.setFont(font2);
		g3.drawString(" "+I1.bod,195,50);
		g3.setFont(font3);
		g3.drawString(":",295,45);
		
		g3.setColor(Color.magenta);
		g3.setFont(font1);
        g3.drawString(" S",315,50);
		g3.setFont(font2);
		g3.drawString(" "+I2.bod,355,50);	}
}


		
public class Crtice 
{

// _______________________ main ___________________________
		
public static void main(String[] args) 
{
	
	 int dm=5, dn=5, br1=1000;
	 float gm1a=(float)0.8, gm1b=(float)0.3; 
	 QIgrac Q11=new QIgrac(dm,dn,gm1a);
	 QIgrac Q12=new QIgrac(dm,dn,gm1b);
	 
	 JFrame crtez1 = new Crtez1();
     JFrame crtez2 = new Crtez2();
	 JFrame crtez3 = new Crtez3();
	
	 crtez1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 crtez2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 crtez3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	 int dm2=5, dn2=5, br2=5;
	 float gm2=(float)0.3;
	 
	 QIgrac Q2=new QIgrac(dm2,dn2,gm2);
	 CIgrac C=new CIgrac(dm2,dn2);
	 
	 int dm3=6, dn3=6, br3=1000;
	 float gm3=(float)0.4;
	 
	 QIgrac Q3=new QIgrac(dm3,dn3,gm3);
	 SIgrac S=new SIgrac(dm3,dn3);
	 
	 JApplet st1 = new Situacija1(Q11,Q12,br1);
	 JApplet st2 = new Situacija2(Q2,C,br2);	
	 JApplet st3 = new Situacija3(Q3,S,br3);	
	 
	 crtez1.getContentPane().add(st1);
	 crtez1.setVisible(true);		
	 
	 crtez3.getContentPane().add(st3);
	 crtez3.setVisible(true);		
	 
	 crtez2.getContentPane().add(st2);
	 crtez2.setVisible(true);		
	 
} // kraj ( main )
	
} // kraj ( class Crtice )




