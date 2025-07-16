
public class Option {
	
	private double strike;
	private double laufzeit;
	private String optionsart;
	
	
	// Konstruktor
	
	public Option(String optionsart,double strike,double laufzeit) {
		this.strike=strike;
		this.laufzeit= laufzeit;
		this.optionsart=optionsart.toLowerCase();	
	}
	
	
	// Getters
	public double getStrike() {
		return this.strike;
	}
	
	public double getlaufzeit() {
		return this.laufzeit;
	}
	
	public String getOptionsart() {
		return this.optionsart;
	}
	
	// Setters
	
	public void setStrike(double strike) {
		this.strike= strike;
	}
	
	public void setLaufzeit(double laufzeit) {
		this.strike= laufzeit;
	}
	
	public void setOptionsart(String optionsart) {
		this.optionsart= optionsart;
	}
	
	
	// Dichte der Standardnormalverteilung
	
	private static double f(double x) {
		return Math.exp((-x*x)/2)/(Math.sqrt(2*Math.PI));
	}
	
	
	// Verteilungsfunktion der Standardnormalverteilung
	
	 private  static double snverteilung(double b) {
	      int N = 10000;                    // precision parameter
	      double h = (b - (-10)) / (N - 1);     // step size

	      // 1/3 terms
	      double sum = 1.0 / 3.0 * (f(-10) + f(b));

	      // 4/3 terms
	      for (int i = 1; i < N - 1; i += 2) {
	         double x = -10 + h * i;
	         sum += 4.0 / 3.0 * f(x);
	      }

	      // 2/3 terms
	      for (int i = 2; i < N - 1; i += 2) {
	         double x = -10+ h * i;
	         sum += 2.0 / 3.0 * f(x);
	      }

	      return sum * h;
	   }
	 
	 // Preis oder Wert einer Option
	 
	public  double fairerOptionpreis(double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d1= (Math.log(S/K)+ (r+(v*v/2.0))*T)/(v*Math.sqrt(T));
		double d2= (Math.log(S/K)+ (r-(v*v/2.0))*T)/(v*Math.sqrt(T));
		if(this.getOptionsart()=="call") {
			return S*snverteilung(d1)-K*Math.exp(-r*T)*snverteilung(d2);
		}else {
			return  K*Math.exp(-r*T)*snverteilung(-d2)- S*snverteilung(-d1);	
		}	
	}
	
	// Delta einer Option
	
	public double delta(double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d1= (Math.log(S/K)+ (r+(v*v/2.0))*T)/(v*Math.sqrt(T));
		if(this.getOptionsart()=="call") {
			return snverteilung(d1);
		}else {
			return  -1*snverteilung(-d1);	
		}	
	}
	
	// Gamma der Option
	
	public double gamma(double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d1= (Math.log(S/K)+ (r+(v*v/2.0))*T)/(v*Math.sqrt(T));
		return (f(d1))/(S*v*Math.sqrt(T));	
	}
	
	
	// Vega der Option
	
	public double vega(double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d1= (Math.log(S/K)+ (r+(v*v/2.0))*T)/(v*Math.sqrt(T));
		return S*Math.sqrt(T)*f(d1);	
	}
	
	// Theta der Option 
	
	public  double theta (double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d1= (Math.log(S/K)+ (r+(v*v/2.0))*T)/(v*Math.sqrt(T));
		double d2= (Math.log(S/K)+ (r-(v*v/2.0))*T)/(v*Math.sqrt(T));
		if(this.getOptionsart()=="call") {
			return (-S*f(d1)*v)/(2*Math.sqrt(T))-K*r*Math.exp(-r*T)*snverteilung(d2);
		}else {
			return (-S*f(d1)*v)/(2*Math.sqrt(T))+ K*r*Math.exp(-r*T)*snverteilung(-d2) ;	
		}	
	}	
	
	// Rho einer Option
	
	public  double  rho (double PreisBasiswert, double volatilität, double zinssatz) {
		double S=PreisBasiswert ;
		double K= this.getStrike();
		double r= zinssatz/100.0;
		double T= this.getlaufzeit();
		double v= volatilität/100.0;
		double d2= (Math.log(S/K)+ (r-(v*v/2.0))*T)/(v*Math.sqrt(T));
		if(this.getOptionsart()=="call") {
			return T*K*Math.exp(-r*T)*snverteilung(d2);
		}else {
			return -T*K*Math.exp(-r*T)*snverteilung(-d2) ;	
		}	
	}	
	public static void main(String[] args) {
		
		System.out.println(snverteilung(-0.296));
		Option option1= new Option ("call",12500,0.5);
		System.out.println(option1.fairerOptionpreis(12000,30,0.1));
		
	}


	
	
}
