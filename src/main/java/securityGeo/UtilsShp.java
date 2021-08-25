package securityGeo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureWriter;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class UtilsShp {

	/**
	 * @return void
	 */
	public static void showshpfiles(ShpFilesData[] shpfilecontents) throws Exception {
		// TODO Auto-generated method stub

		// init the mapcontent
		MapContent mapContent = new MapContent();
		mapContent.setTitle("MonkeyBoy's GeoTools");

		for (int i = 0; i < shpfilecontents.length; i++) {

			if (shpfilecontents[i].getFile() != null) {
				FileDataStore store = FileDataStoreFinder.getDataStore(shpfilecontents[i].getFile());

				((ShapefileDataStore) store).setCharset(Charset.forName("UTF-8"));
				SimpleFeatureSource featureSource = store.getFeatureSource();

				Style stylePloygon = SLD.createSimpleStyle(featureSource.getSchema());
				// createPolygonStyle(Color.black, new Color(242, 237, 206), (float) 0.0);

				int r = (int) (Math.random() * 255);
				int g = (int) (Math.random() * 255);
				int b = (int) (Math.random() * 255);
				Style styleline = SLD.createLineStyle(new Color(r, g, b), 1);

				// point
				r = (int) (Math.random() * 255);
				g = (int) (Math.random() * 255);
				b = (int) (Math.random() * 255);

				Style stylePoint = SLD.createPointStyle("Triangle", new Color(r, g, b), new Color(r, g, b), 1f, 6);

				Layer layer = null;
				// String filename = file.getName();
				if (shpfilecontents[i].getShptypeString().contains("Point")) {
					layer = new FeatureLayer(featureSource, stylePoint);
				} else if (shpfilecontents[i].getShptypeString().contains("Line")) {
					layer = new FeatureLayer(featureSource, styleline);
				} else if (shpfilecontents[i].getShptypeString().contains("Polygon")) {
					layer = new FeatureLayer(featureSource, stylePloygon);
				} else if (shpfilecontents[i].getShptypeString().contains("other")) {
					System.err.println("other " + "Error!");
					Style otherstyle = SLD.createSimpleStyle(featureSource.getSchema());
					layer = new FeatureLayer(featureSource, otherstyle);
				} else {
					System.err.println(shpfilecontents[i].getShptypeString() + "Error!");
				}
				mapContent.addLayer(layer);
			}
		}

		JMapFrame.showMap(mapContent);
	}

	/**
	 * @return void
	 * @param files
	 */
	public static void readDBF(File[] files) {
		for (int i = 0; i < files.length; i++) {
			readDBF(files[i].getAbsolutePath());
		}
	}

	/**
	 * @return void
	 * @param path
	 */
	public static void readDBF(String path) {
		DbaseFileReader reader = null;
		try {
			reader = new DbaseFileReader(new ShpFiles(path), false, Charset.forName("GBK"));
			DbaseFileHeader header = reader.getHeader();
			int numFields = header.getNumFields();
			while (reader.hasNext()) {
				try {
					Object[] entry = reader.readEntry();
					for (int i = 0; i < numFields; i++) {
						String title = header.getFieldName(i);
						Object value = entry[i];
						System.out.println(title + "=" + value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * @return void
	 * @param files
	 */
	public static void readSHP(File[] files) {
		// List<String[]> shpList
		for (int i = 0; i < files.length; i++) {
			readSHP(files[i].getAbsolutePath());
		}
	}

	/**
	 * @return void
	 * @param path
	 */
	public static void readSHP(String path) {

		ShapefileDataStore shpDataStore = null;
		try {
			shpDataStore = new ShapefileDataStore(new File(path).toURI().toURL());
			shpDataStore.setCharset(Charset.forName("UTF-8"));
			String typeName = shpDataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;
			featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>) shpDataStore.getFeatureSource(typeName);
			FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
			// System.out.println(result.size());
			FeatureIterator<SimpleFeature> itertor = result.features();

			while (itertor.hasNext()) {
				SimpleFeature feature = itertor.next();
				Collection<Property> p = feature.getProperties();
				Iterator<Property> it = p.iterator();

				while (it.hasNext()) {
					Property pro = it.next();

					// new a Variable
					ShpFilesData tempShpFilesData = new ShpFilesData();

					if (pro.getValue() instanceof Point) {

						String pointString = pro.getValue().toString();
						/*
						 * pointString = pointString.substring(pointString.indexOf("("),
						 * pointString.lastIndexOf(")") + 1) .replace(")), ((", "#").replace("), (",
						 * "&").replace("(", "").replace(")", "") .replace(" ", ",").replace(",,", ";");
						 */
						tempShpFilesData.setShpcontentList(pointString);
						tempShpFilesData.setShptypeString("Point");
						tempShpFilesData.setFile(new File(path));
						MainApplication.myshpDatas.add(tempShpFilesData);
						// System.out.println(pointString);
					}
					if (pro.getValue() instanceof MultiPoint) {
						String multiPointString = pro.getValue().toString();
						/*
						 * multiPointString = multiPointString .substring(multiPointString.indexOf("("),
						 * multiPointString.lastIndexOf(")") + 1) .replace(")), ((",
						 * "#").replace("), (", "&").replace("(", "").replace(")", "") .replace(" ",
						 * ",").replace(",,", ";");
						 */
						tempShpFilesData.setShpcontentList(multiPointString);
						tempShpFilesData.setShptypeString("MultiPoint");
						tempShpFilesData.setFile(new File(path));
						MainApplication.myshpDatas.add(tempShpFilesData);
						// System.out.println("multiPointString:"+multiPointString);
					}
					if (pro.getValue() instanceof LineString) {
						String lineString = pro.getValue().toString();
						/*
						 * lineString = lineString.substring(lineString.indexOf("("),
						 * lineString.lastIndexOf(")") + 1) .replace(")), ((", "#").replace("), (",
						 * "&").replace("(", "").replace(")", "") .replace(" ", ",").replace(",,", ";");
						 */

						tempShpFilesData.setShpcontentList(lineString);
						tempShpFilesData.setShptypeString("LineString");
						tempShpFilesData.setFile(new File(path));
						MainApplication.myshpDatas.add(tempShpFilesData);
						// System.out.println("lineString:"+lineString);
					}
					if (pro.getValue() instanceof MultiLineString) {
						String multiLineString = pro.getValue().toString();

						/*
						 * multiLineString = multiLineString .substring(multiLineString.indexOf("("),
						 * multiLineString.lastIndexOf(")") + 1) .replace(")), ((", "#").replace("), (",
						 * "&").replace("(", "").replace(")", "") .replace(" ", ",").replace(",,", ";");
						 */

						tempShpFilesData.setShpcontentList(multiLineString);
						tempShpFilesData.setShptypeString("MultiLineString");
						tempShpFilesData.setFile(new File(path));
						MainApplication.myshpDatas.add(tempShpFilesData);
						// System.out.println("multiLineString:"+multiLineString);
					}
					if (pro.getValue() instanceof MultiPolygon) {
						String polygonString = pro.getValue().toString();

						if (!polygonString.contains("EMPTY")) {
							/*
							 * polygonString = polygonString .substring(polygonString.indexOf("("),
							 * polygonString.lastIndexOf(")") + 1) .replace(")), ((", "#").replace("), (",
							 * "&").replace("(", "").replace(")", "") .replace(" ", ",").replace(",,", ";");
							 */

							tempShpFilesData.setShpcontentList(polygonString);
							tempShpFilesData.setShptypeString("MultiPolygon");
							tempShpFilesData.setFile(new File(path));
							MainApplication.myshpDatas.add(tempShpFilesData);
						}

						// System.out.println(polygonString);
					}

				}

			}
			itertor.close();
			shpDataStore.dispose();// 使用之后必须关掉

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void writeSourceshpinfo(File[] Files) throws Exception {

		for (int i = 0; i < Files.length; i++) {
			File newShpFile = new File(Files[i].getParentFile().getAbsolutePath() + "\\Sourcenew" + Files[i].getName());
			if (newShpFile.exists()) {
				newShpFile.delete();
			}
		}

		for (int i = 0; i < MainApplication.myshpDatas.size()*Utilinterfere.Percentage; i++) {

			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("Point")) {

				String string = MainApplication.myshpDatas.get(i).getShpcontentList().replace("POINT (", "")
						.replace(")", "");
				// String string = shpfilecontents[i].getShpcontentList().get(j)[1];
				double x = Double.parseDouble(string.split(" ")[0]);
				double y = Double.parseDouble(string.split(" ")[1]);
				Coordinate coordinate = new Coordinate(x, y);
				Point newPoint = new GeometryFactory().createPoint(coordinate);
				writePointshpToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\Sourcenew"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						newPoint);

			}

			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiLineString")) {

				GeometryCreator gCreator = GeometryCreator.getInstance();

				String MPolygonWKT = MainApplication.myshpDatas.get(i).getShpcontentList();
				MultiLineString multiLineString = gCreator.createMLineByWKT(MPolygonWKT);
				writeMultiLineStringshpToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\Sourcenew"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						multiLineString);

			}
			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiPolygon")) {
				GeometryCreator gCreator = GeometryCreator.getInstance();

				String MPolygonWKT = MainApplication.myshpDatas.get(i).getShpcontentList();
				MultiPolygon multiPolygon = gCreator.createMulPolygonByWKT(MPolygonWKT);
				writePolygonSHPToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\Sourcenew"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						multiPolygon);

			}
		}

	}
	
	/**
	 * @return void
	 */
	public void writeshpinfo(File[] Files) throws Exception {

		for (int i = 0; i < Files.length; i++) {
			File newShpFile = new File(Files[i].getParentFile().getAbsolutePath() + "\\new" + Files[i].getName());
			if (newShpFile.exists()) {
				newShpFile.delete();
			}
		}

		for (int i = 0; i < MainApplication.myshpDatas.size(); i++) {

			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("Point")) {

				String string = MainApplication.myshpDatas.get(i).getShpcontentList().replace("POINT (", "")
						.replace(")", "");
				// String string = shpfilecontents[i].getShpcontentList().get(j)[1];
				double x = Double.parseDouble(string.split(" ")[0]);
				double y = Double.parseDouble(string.split(" ")[1]);
				Coordinate coordinate = new Coordinate(x, y);
				Point newPoint = new GeometryFactory().createPoint(coordinate);
				writePointshpToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\new"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						newPoint);

			}

			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiLineString")) {

				GeometryCreator gCreator = GeometryCreator.getInstance();

				String MPolygonWKT = MainApplication.myshpDatas.get(i).getShpcontentList();
				MultiLineString multiLineString = gCreator.createMLineByWKT(MPolygonWKT);
				writeMultiLineStringshpToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\new"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						multiLineString);

			}
			if (MainApplication.myshpDatas.get(i).getShptypeString().equals("MultiPolygon")) {
				GeometryCreator gCreator = GeometryCreator.getInstance();

				String MPolygonWKT = MainApplication.myshpDatas.get(i).getShpcontentList();
				MultiPolygon multiPolygon = gCreator.createMulPolygonByWKT(MPolygonWKT);
				writePolygonSHPToFile(
						new File(MainApplication.myshpDatas.get(i).getFile().getParentFile().getAbsolutePath() + "\\new"
								+ MainApplication.myshpDatas.get(i).getFile().getName()),
						multiPolygon);

			}
		}

	}

	/**
	 * @return void
	 */
	public void writePointshpToFile(File newshpfile, Geometry geometry) throws Exception {
		// TODO Auto-generated method stub
		ShapefileDataStore ds = new ShapefileDataStore(newshpfile.toURI().toURL());
		if (!newshpfile.exists()) {
			SimpleFeatureTypeBuilder tBuilder = new SimpleFeatureTypeBuilder();
			tBuilder.setCRS(DefaultGeographicCRS.WGS84);
			tBuilder.setName("Points");
			tBuilder.add("the_geom", Point.class);
			tBuilder.add("id", Long.class);
			SimpleFeatureType buildFeatureType = tBuilder.buildFeatureType();
			ds.createSchema(buildFeatureType);
		}

		ds.setCharset(Charset.forName("UTF-8"));
		String typeName = ds.getTypeNames()[0];
		FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriterAppend(typeName,
				Transaction.AUTO_COMMIT);
		SimpleFeature feature = writer.next();
		feature.setAttribute("the_geom", geometry);

		writer.write();
		writer.close();
		ds.dispose();
	}

	/**
	 * @return void
	 */
	public void writeMultiLineStringshpToFile(File newshpfile, Geometry geometry) throws Exception {
		// TODO Auto-generated method stub
		ShapefileDataStore ds = new ShapefileDataStore(newshpfile.toURI().toURL());
		if (!newshpfile.exists()) {
			SimpleFeatureTypeBuilder tBuilder = new SimpleFeatureTypeBuilder();
			tBuilder.setCRS(DefaultGeographicCRS.WGS84);
			tBuilder.setName("Points");
			tBuilder.add("the_geom", MultiLineString.class);
			tBuilder.add("id", Long.class);

			SimpleFeatureType buildFeatureType = tBuilder.buildFeatureType();
			ds.createSchema(buildFeatureType);
		}

		ds.setCharset(Charset.forName("UTF-8"));
		String typeName = ds.getTypeNames()[0];
		FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriterAppend(typeName,
				Transaction.AUTO_COMMIT);
		SimpleFeature feature = writer.next();
		feature.setAttribute("the_geom", geometry);

		writer.write();
		writer.close();
		ds.dispose();
	}

	/**
	 * @return void
	 * @param path
	 * @param geometry
	 * @param desc
	 * @throws Exception
	 */
	public void writePolygonSHPToFile(File newshpfile, Geometry geometry) throws Exception {

		ShapefileDataStore ds = new ShapefileDataStore(newshpfile.toURI().toURL());
		if (!newshpfile.exists()) {
			SimpleFeatureTypeBuilder tBuilder = new SimpleFeatureTypeBuilder();
			tBuilder.setCRS(DefaultGeographicCRS.WGS84);
			tBuilder.setName("shapefile");
			tBuilder.add("the_geom", MultiPolygon.class);
			tBuilder.add("osm_id", Long.class);
			tBuilder.add("name", String.class);
			tBuilder.add("des", String.class);
			SimpleFeatureType buildFeatureType = tBuilder.buildFeatureType();
			ds.createSchema(buildFeatureType);
		}

		ds.setCharset(Charset.forName("UTF-8"));

		String typeName = ds.getTypeNames()[0];
		FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriterAppend(typeName,
				Transaction.AUTO_COMMIT);
		SimpleFeature feature = writer.next();
		feature.setAttribute("the_geom", geometry);
		feature.setAttribute("osm_id", 1234567890l);
		feature.setAttribute("name", "Buildings");
		feature.setAttribute("des", "Buildings");
		writer.write();
		writer.close();
		ds.dispose();
	}

	/**
	 * @return void
	 * @param shpfilecontents
	 */
	public void SortFunction() {
		// TODO Auto-generated method stub
		boolean flag = false;
		for (int i = 0; i < MainApplication.myshpDatas.size() - 1; i++) {
			for (int j = 0; j < MainApplication.myshpDatas.size() - 1 - i; j++) {
				if (MainApplication.myshpDatas.get(j).information[3] < MainApplication.myshpDatas.get(j + 1).information[3]) {
					flag = true;
					ShpFilesData temp = MainApplication.myshpDatas.get(j);
					MainApplication.myshpDatas.set(j, MainApplication.myshpDatas.get(j + 1));
					MainApplication.myshpDatas.set(j + 1, temp);
				}
			}
			if (!flag) {
				break;
			} else {
				flag = false;
			}
		}
		/*
		for (int i = 0; i < 10; i++) {
			System.out.println(MainApplication.myshpDatas.get(i).getInformation()[3]);
		}*/
	}
	

	/**
	 * @return void
	 */
	public void setinformation() {
		
		//System.out.println("setinformation");
	
		// TODO Auto-generated method stub
		for (int i = 0; i < MainApplication.myshpDatas.size(); i++) {
			int times = (int) (Math.random() * 100);
			int locals = (int) (Math.random() * 100);
			int weights = (int) (Math.random() * 100);
			MainApplication.myshpDatas.get(i).setInformation(times, locals, weights);
		}
	}
	
	
}