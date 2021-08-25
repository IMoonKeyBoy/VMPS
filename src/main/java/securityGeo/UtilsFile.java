package securityGeo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class UtilsFile {

	/**
	 * @return void
	 * @throws Exception
	 */
	public static File[] chooseMulFiles() {
		// TODO Auto-generated method stub

		/*
		JFileChooser addChooser = new JFileChooser(new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape"));
		addChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		addChooser.setMultiSelectionEnabled(true);
		int returnval = addChooser.showOpenDialog(null);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			File[] files = addChooser.getSelectedFiles();
			
			 for (File file : files) { System.out.println(file.getAbsolutePath()); }
			 
			return files;
		} else {
			System.err.println("No files are selects");
			System.exit(0);
		}
	*/
		File[] files = new File[6];
		files[0] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\roads.shp");
		files[1] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\railways.shp");
		files[2] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\points.shp");
		files[3] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\natural.shp");
		files[4] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\landuse.shp");
		files[5] = new File("C:\\Users\\MonkeyBoy\\Desktop\\Other\\实验数据二\\planet_13.38,52.511_13.387,52.517.osm.shp\\planet_13.38,52.511_13.387,52.517-shp\\shape\\buildings.shp");
		
		
		return files;
	}

	/**
	 * @return void
	 */
	public static void showFiletype(ShpFilesData[] shpfilecontents) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shpfilecontents.length; i++) {
			if (shpfilecontents[i].getFile() != null) {
				// MultiPoint LineString MultiLineString MultiPolygon
				if (shpfilecontents[i].getShptypeString().equals("Point")) {
					System.err.println(shpfilecontents[i].getFile().getAbsolutePath() + " is  point file");
				}
				if (shpfilecontents[i].getShptypeString().equals("MultiPoint")) {
					System.err.println(shpfilecontents[i].getFile().getAbsolutePath() + " is  MultiPoint file");

				}
				if (shpfilecontents[i].getShptypeString().equals("LineString")) {
					System.err.println(shpfilecontents[i].getFile().getAbsolutePath() + " is  LineString file");

				}
				if (shpfilecontents[i].getShptypeString().equals("MultiLineString")) {
					System.err.println(shpfilecontents[i].getFile().getAbsolutePath() + " is  MultiLineString file");

				}
				if (shpfilecontents[i].getShptypeString().equals("MultiPolygon")) {
					System.err.println(shpfilecontents[i].getFile().getAbsolutePath() + " is  MultiPolygon file");
				}
			}
		}
	}

	/**
	 * @return void
	 */
	public void writeInformation() {
		
		System.out.println("writeInformation");
		
		// TODO Auto-generated method stub
		String fileName = "C:\\Users\\MonkeyBoy\\Desktop\\Expriment\\information";
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < MainApplication.myshpDatas.size(); i++) {
			try {
				writer.write(MainApplication.myshpDatas.get(i).getInformation()[0] + " "
						+ MainApplication.myshpDatas.get(i).getInformation()[1] + " "
						+ MainApplication.myshpDatas.get(i).getInformation()[2] + " "
						+ MainApplication.myshpDatas.get(i).getInformation()[3]+"\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return void
	 */
	public void readInformation() {
		
		System.out.println("readInformation");
		
		// TODO Auto-generated method stub
		String fileName = "E:\\OneDrive\\Documents\\PaperWriting2\\Expriment\\information";
		int number = 0;
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			line = in.readLine();
			while (line != null) {
				String[] information = line.split(" ");
				MainApplication.myshpDatas.get(number).setInformation(information);
				number++;
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
