package securityGeo;

import java.util.ArrayList;
import java.util.List;

public class LogisticsInterfere {

	double l_0 = 3;
	double a = 3.6865;
	double T = 100;

	/**
	 * @return void
	 */
	private void inilogistics(double n) {
		// TODO Auto-generated method stub
		this.T = n;
		for (int i = 0; i < T; i++) {
			l_0 = a * l_0 - l_0 * l_0;
		}
	}

	/**
	 * @return void
	 */
	public double GenChaotic(double n) {
		// TODO Auto-generated method stub
		// genlogistics number!
		l_0 = a * l_0 - l_0 * l_0;
		//get k!
		return l_0 / 2 + 0.5;
	}

}
