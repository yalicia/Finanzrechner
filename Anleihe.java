
public class Anleihe {
	private double koupon;
	private double Restlaufzeit;
	private double nennwert;
	private double rückzahlungskurs;
	
	// Konstruktor
	
	public Anleihe(double koupon,double RestLaufzeit, double nennwert, double rückzahlungskurs) {
		this.koupon= koupon;
		this.Restlaufzeit= RestLaufzeit;
		this.nennwert= nennwert;
		this.rückzahlungskurs=rückzahlungskurs;
	}
	
	// Getter-Methoden
	
	public double getKoupon() {
		return this.koupon;
	}
	
	public double getRestLaufzeit() {
		return this.Restlaufzeit;
	}
	
	public double getNennwert() {
		return this.nennwert;
	}
	
	public double getRückzahlungskurs() {
		return this.rückzahlungskurs;
	}
	
	
	// Setter-Methoden
	
	public void setKoupon(double koupon) {
		 this.koupon= koupon;
	}
	
	public void setRestlaufzeit(double restlaufzeit) {
		 this.Restlaufzeit= restlaufzeit;
	}
	
	public void setNennwert(double nennwert) {
		 this.nennwert= nennwert;
	}
	
	public void setRückzahlungskurs(double rückzahlungskurs) {
		 this.rückzahlungskurs= rückzahlungskurs;
	}
	
// börsenkurs einer Anleihe
	
	public double kaufkurs(double marktzinsatz) {
		double m= this.getRestLaufzeit();
		double n= Math.ceil(m);
		double q = 1+ (marktzinsatz/100);
		double Rzk= this.getRückzahlungskurs();
		double kurs = ((this.getKoupon())*((Math.pow(q,n)-1)/(q-1))+ Rzk)*(1/(Math.pow(q,m)));
		return kurs;
	}
   
   //  numerische Berechnung der Äquivalenzgleichung
	
	private static double newtonIteration(double q, double k, double c,double n, double N ) {
	   double f1= k*Math.pow(q,(n+1)) -(k+c)*Math.pow(q,n)-N*q+c+N;
	   double f2=k*(n+1)*Math.pow(q,n)-n*(k+c)*Math.pow(q,(n-1))-N;
	   return q-(f1/f2);
   }
   
   // Rendite der Anleihe
   
	public  double rendite (double kurs) {
		double k= kurs;
		double n= this.getRestLaufzeit();
		double N= 100;
		double c=this.getKoupon();
		double q0=2.00, q1=newtonIteration(q0,k,c,n,N); 
		while (Math.abs(q1-q0)>0.0000000000005) {
			q0=q1;
			q1=newtonIteration(q1,k,c,n,N);	
		}
		return (q1-1)*100;
	}
	
	
	
	public String [][] tilgungsplan(double rendite){
		double m= this.getRestLaufzeit();
		int n= (int)(Math.ceil(m));
		String[][] tab = new String[n+1][4];
		tab[0][0]= "0"; tab[0][1]="kauf";
		tab[0][2]= ((this.kaufkurs(rendite))/100)*(this.getNennwert())*(-1)+""; tab[0][3]= tab[0][2];
		double saldo=((this.kaufkurs(rendite))/100)*(this.getNennwert())*(-1) ;
		for(int i= 1; i<n;i++) {
				
			tab[i][0]=i+"";tab[i][1]="Zinszahlung"; 
			tab[i][2]= (this.getKoupon()/100) *this.getNennwert()+"";
			tab[i][3]= saldo + (this.getKoupon()/100) *this.getNennwert() +"";
			saldo+= (this.getKoupon()/100) *this.getNennwert();	
		}
		tab[n][0]=n+""; tab[n][1]="Zinszahlung + Rückzahlung";
		tab[n][2]=this.getNennwert()+(this.getKoupon()/100) *this.getNennwert()+""; 
		tab[n][3]=saldo +this.getNennwert()+(this.getKoupon()/100) *this.getNennwert()  +"";
		
		return tab;
	}
	
	private static void Tilgungsplan_zeigen(String[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
			System.out.print(array[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Anleihe anleihe1 = new Anleihe(8, 10, 100,100);		
		System.out.println(anleihe1.kaufkurs(8.5));
		System.out.println(anleihe1.rendite(95.41));
		Tilgungsplan_zeigen(anleihe1.tilgungsplan(8.5));
		
	}
}
