package securityGeo;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeometryCreator {

	public static GeometryCreator geometryCreator = null;

	private GeometryFactory geometryFactory = new GeometryFactory();

	private GeometryCreator() {
	}

	public static GeometryCreator getInstance() {
		if (geometryCreator == null) {
			return new GeometryCreator();
		}
		return geometryCreator;
	}

	public Point createPoint(double x, double y) {
		Coordinate coord = new Coordinate(x, y);
		Point point = geometryFactory.createPoint(coord);
		return point;
	}

	public Point createPointByWKT(String PointWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		Point point = (Point) reader.read(PointWKT);
		return point;
	}

	public MultiPoint createMulPointByWKT(String MPointWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		MultiPoint mpoint = (MultiPoint) reader.read(MPointWKT);
		return mpoint;
	}

	public LineString createLine(double ax, double ay, double bx, double by) {
		Coordinate[] coords = new Coordinate[] { new Coordinate(ax, ay), new Coordinate(bx, by) };
		LineString line = geometryFactory.createLineString(coords);
		return line;
	}

	public LineString createLineByWKT(String LineStringWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		LineString line = (LineString) reader.read("LINESTRING(0 0, 2 0)");
		return line;
	}

	public MultiLineString createMLine(List<Coordinate[]> list) {

		MultiLineString ms = null;
		if (list == null) {
			return ms;
		}
		LineString[] lineStrings = new LineString[list.size()];
		int i = 0;
		for (Coordinate[] coordinates : list) {
			lineStrings[i] = geometryFactory.createLineString(coordinates);
		}
		ms = geometryFactory.createMultiLineString(lineStrings);
		return ms;
	}

	public MultiLineString createMLineByWKT(String MLineStringWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		MultiLineString line = (MultiLineString) reader.read(MLineStringWKT);
		return line;
	}

	public Polygon createPolygonByWKT(String PolygonWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		Polygon polygon = (Polygon) reader.read(PolygonWKT);
		return polygon;
	}

	public MultiPolygon createMulPolygonByWKT(String MPolygonWKT) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		MultiPolygon mpolygon = (MultiPolygon) reader.read(MPolygonWKT);
		return mpolygon;
	}

	public MultiPolygon createMulPolygonByPolygon(Polygon[] polygons) throws ParseException {
		return geometryFactory.createMultiPolygon(polygons);
	}

	public GeometryCollection createGeoCollect(Geometry[] geoArray) throws ParseException {
		GeometryCollection gc = geometryFactory.createGeometryCollection(geoArray);
		return gc;
	}

}
