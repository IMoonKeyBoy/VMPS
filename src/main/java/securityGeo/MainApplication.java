package securityGeo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.MultiPolygon;

public class MainApplication {

	public static List<ShpFilesData> myshpDatas = new ArrayList<ShpFilesData>();

	public static List<ShpFilesData> showshpDatas = new ArrayList<ShpFilesData>();

	/**
	 * @return void
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			System.err.println("error!");
		}
		// select the files
		File[] files = UtilsFile.chooseMulFiles();

		// read the shpfiles data and save the data to shpfilecontents.
		UtilsShp utilsShp = new UtilsShp();
		utilsShp.readSHP(files);
		utilsShp.setinformation();

		 UtilsFile utilsFile = new UtilsFile();
		// utilsFile.writeInformation();
		utilsFile.readInformation();

		utilsShp.SortFunction(); 

		utilsShp.writeSourceshpinfo(files);
		Utilinterfere utilinterfere = new Utilinterfere();
		utilinterfere.Runinterfere();
		utilsShp.writeshpinfo(files);

		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
