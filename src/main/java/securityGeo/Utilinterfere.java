package securityGeo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vividsolutions.jts.awt.PointShapeFactory.X;

public class Utilinterfere {

	static double interparameter = 0.0001;
	static double Percentage = 0.175;
	static double radius = 0.0001;

	/**
	 * @return void
	 */
	public double getlocationlenth(double x, double y) {
		// TODO Auto-generated method stub
		double result = 0.001;
		// = Math.sqrt((x - locationX) * (x - locationX) + (y - locationY) * (y -
		// locationY));
		return result;
		// System.out.println(result+1);
	}

	/**
	 * @return void
	 */
	public void Runinterfere() {

		// interfere Percentage
		

		// init the henon
		HenonInterfere henonInterfere = new HenonInterfere();
		henonInterfere.initHenon((double) 100);

		// TODO Auto-generated method stub
		for (int i = 0; i < MainApplication.myshpDatas.size() * Percentage; i++) {

			// Point files
			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("Point")) {

				// one point POINT (13.380767 52.5137672)
				String string = MainApplication.myshpDatas.get(i).getShpcontentList().replace("POINT (", "").replace(")", "");
				double x = Double.parseDouble(string.split(" ")[0]);
				double y = Double.parseDouble(string.split(" ")[1]);
				double[] interDouble = henonInterfere.getinternumber();
				x = x + interDouble[0];
				y = y + interDouble[1];
				MainApplication.myshpDatas.get(i).setShpcontentList("POINT (" + x + " " + y + ")");

			}
			// MultiLineString files
			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiLineString")) {
				// one line~
				String string = MainApplication.myshpDatas.get(i).getShpcontentList().replace("MULTILINESTRING ((", "").replace("))", "");
				String newString = "MULTILINESTRING ((";
				// get each number in line
				String[] eachnumber = string.split(", ");
				double[] interDouble = henonInterfere.getinternumber();
				for (int k = 0; k < eachnumber.length; k++) {
					double x = Double.parseDouble(eachnumber[k].split(" ")[0]);
					double y = Double.parseDouble(eachnumber[k].split(" ")[1]);
					
					x = x + interDouble[0];
					y = y + interDouble[1];
					eachnumber[k] = x + " " + y;
				}
				int k;
				for (k = 0; k < eachnumber.length - 1; k++) {
					newString = newString + eachnumber[k] + ", ";
				}
				newString = newString + eachnumber[k] + "))";
				MainApplication.myshpDatas.get(i).setShpcontentList(newString);

			}
			// MultiPolygon files
			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiPolygon")) {

				
				// one line~
				String string = MainApplication.myshpDatas.get(i).getShpcontentList().replace("MULTIPOLYGON (((", "").replace(")))", "");
				String newString = "MULTIPOLYGON (((";
				// get each number in line
				String[] eachnumber = string.split(", ");
				// don't touch the first and last data
				
				double[] interDouble = henonInterfere.getinternumber();
				
	
				for (int k = 0; k < eachnumber.length ; k++) {
					double x = Double.parseDouble(eachnumber[k].split(" ")[0]);
					double y = Double.parseDouble(eachnumber[k].split(" ")[1]);
					
					x = x + interDouble[0];
					y = y + interDouble[1];
					eachnumber[k] = x + " " + y;
				}
				

				int k;
				newString = newString +eachnumber[0];
				for (k = 1; k < eachnumber.length; k++) {
					newString = newString +", "+ eachnumber[k];
				}
				newString = newString + ")))";
				MainApplication.myshpDatas.get(i).setShpcontentList(newString);
			}
		}
	}

	/**
	 * @return void
	 */
	public void RunPointPartinterfere(ShpFilesData[] shpfilecontents) {
		// TODO Auto-generated method stub

		for (int i = 0; i < shpfilecontents.length; i++) {

			// Point files
			if (shpfilecontents[i].getShptypeString().equals("Point")) {

				List<Boolean[]> isDisturbanceList = new ArrayList<Boolean[]>();
				HenonInterfere henonInterfere = new HenonInterfere();
				henonInterfere.initHenon((double) 100);

				for (int j = 0; j < shpfilecontents[i].getShpcontentList().size(); j++) {

					List<Boolean> isDisturbanceListboolean = new ArrayList<Boolean>();
					// one point POINT (13.380767 52.5137672)
					String string = shpfilecontents[i].getShpcontentList().get(j)[1].replace("POINT (", "").replace(")",
							"");
					double x = Double.parseDouble(string.split(" ")[0]);
					double y = Double.parseDouble(string.split(" ")[1]);

					if (getlocationlenth(x, y) < radius) {
						isDisturbanceListboolean.add(true);

						double[] interDouble = henonInterfere.getinternumber();
						x = x + interDouble[0];
						y = y + interDouble[1];
						shpfilecontents[i].getShpcontentList().get(j)[1] = "POINT (" + x + " " + y + ")";
					} else {
						isDisturbanceListboolean.add(false);
					}

					Boolean[] filesisBooleans = (Boolean[]) isDisturbanceListboolean
							.toArray(new Boolean[isDisturbanceListboolean.size()]);
					isDisturbanceList.add(filesisBooleans);
					isDisturbanceListboolean.clear();

				}

			}
		}
	}

	/**
	 * @return void
	 */
	public void RunLineStringPartinterfere(ShpFilesData[] shpfilecontents) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shpfilecontents.length; i++) {

			// MultiLineString files
			if (shpfilecontents[i].getShptypeString().equals("MultiLineString")) {

				HenonInterfere henonInterfere = new HenonInterfere();
				henonInterfere.initHenon((double) 100);
				List<Boolean[]> isDisturbanceList = new ArrayList<Boolean[]>();

				for (int j = 0; j < shpfilecontents[i].getShpcontentList().size(); j++) {

					List<Boolean> isDisturbanceListboolean = new ArrayList<Boolean>();
					// one line~
					String string = shpfilecontents[i].getShpcontentList().get(j)[1].replace("MULTILINESTRING ((", "")
							.replace("))", "");
					String newString = "MULTILINESTRING ((";
					// get each number in line
					String[] eachnumber = string.split(", ");

					for (int k = 0; k < eachnumber.length; k++) {

						double x = Double.parseDouble(eachnumber[k].split(" ")[0]);
						double y = Double.parseDouble(eachnumber[k].split(" ")[1]);

						if (getlocationlenth(x, y) < radius) {

							double[] interDouble = henonInterfere.getinternumber();

							x = x + interDouble[0];
							y = y + interDouble[1];
							eachnumber[k] = x + " " + y;

							isDisturbanceListboolean.add(true);
						} else {
							isDisturbanceListboolean.add(false);
						}
					}

					int k;
					for (k = 0; k < eachnumber.length - 1; k++) {
						newString = newString + eachnumber[k] + ", ";
					}
					newString = newString + eachnumber[k] + "))";
					shpfilecontents[i].getShpcontentList().get(j)[1] = newString;
					Boolean[] filesisBooleans = (Boolean[]) isDisturbanceListboolean
							.toArray(new Boolean[isDisturbanceListboolean.size()]);
					isDisturbanceList.add(filesisBooleans);
					isDisturbanceListboolean.clear();

				}
			}

		}
	}

	/**
	 * @return void
	 */
	public void RunMultiPolygonPartinterfere(ShpFilesData[] shpfilecontents) {
		// TODO Auto-generated method stub

		for (int i = 0; i < shpfilecontents.length; i++) {

			// Point files
			if (shpfilecontents[i].getShptypeString().equals("MultiPolygon")) {

				HenonInterfere henonInterfere = new HenonInterfere();
				henonInterfere.initHenon((double) 100);
				List<Boolean[]> isDisturbanceList = new ArrayList<Boolean[]>();

				for (int j = 0; j < shpfilecontents[i].getShpcontentList().size(); j++) {

					List<Boolean> isDisturbanceListboolean = new ArrayList<Boolean>();
					// one line~
					String string = shpfilecontents[i].getShpcontentList().get(j)[1].replace("MULTIPOLYGON (((", "")
							.replace(")))", "");
					String newString = "MULTIPOLYGON (((";
					// get each number in line
					String[] eachnumber = string.split(", ");
					// don't touch the first and last data
					for (int k = 1; k < eachnumber.length - 1; k++) {

						double x = Double.parseDouble(eachnumber[k].split(" ")[0]);
						double y = Double.parseDouble(eachnumber[k].split(" ")[1]);

						if (getlocationlenth(x, y) < radius) {
							double[] interDouble = henonInterfere.getinternumber();
							x = x + interDouble[0];
							y = y + interDouble[1];
							eachnumber[k] = x + " " + y;
							isDisturbanceListboolean.add(true);
						} else {
							isDisturbanceListboolean.add(false);
						}
					}

					int k;
					for (k = 0; k < eachnumber.length - 1; k++) {
						newString = newString + eachnumber[k] + ", ";
					}
					newString = newString + eachnumber[k] + ")))";

					shpfilecontents[i].getShpcontentList().get(j)[1] = newString;

					Boolean[] filesisBooleans = (Boolean[]) isDisturbanceListboolean
							.toArray(new Boolean[isDisturbanceListboolean.size()]);
					isDisturbanceList.add(filesisBooleans);
					isDisturbanceListboolean.clear();

				}
			}
		}
	}

}
