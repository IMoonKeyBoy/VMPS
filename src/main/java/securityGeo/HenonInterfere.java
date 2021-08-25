package securityGeo;

public class HenonInterfere {

	double a = 1.25;
	double b = 0.3;
	double x0 = 1;
	double y0 = 1;
	double T0 = 0;

	/**
	 * @return void
	 */
	public void initHenon(Double n) {
		// TODO Auto-generated method stub
		this.T0 = n;
		for (int i = 0; i < T0; i++) {
			x0 = y0 + 1 - a * x0 * x0;
			y0 = b * x0;
		}
	}

	/**
	 * @return void
	 */
	public double[] getinternumber() {
		// TODO Auto-generated method stub
		x0 = y0 + 1 - a * x0 * x0;
		y0 = b * x0;
		
		double[] results = new double[2];
		results[0] = x0%Utilinterfere.interparameter;
		results[1] = y0%Utilinterfere.interparameter;
		return results;
	}

}
