import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * This example uses a local MBTiles file. Thus, it does not need an Internet connection to load tiles.
 */
public class OfflineMapApp extends PApplet {

	public static String mbTilesString = "blankLight-1-3.mbtiles";

	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);

		map = new UnfoldingMap(this, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		map.setZoomRange(1, 3);
		
		Location obj = new Location(22.4f, 88.3f);
		PointFeature point = new PointFeature(obj);
		point.addProperty("year", 1900);
		point.addProperty("magnitude", 9.5);
		
		PointFeature point1 = new PointFeature(new Location(3.30f,95.78f));
		point1.addProperty("year", 2000);
		point1.addProperty("magnitude", 6.5);
		
		PointFeature point2 = new PointFeature(new Location(61.02f,-147.65f));
		point2.addProperty("year", 2002);
		point2.addProperty("magnitude", 9.7);
		
		PointFeature point3 = new PointFeature(new Location(-38.14f,-73.03f));
		point3.addProperty("year", 2004);
		point3.addProperty("magnitude", 8.5);
		
		List<PointFeature> lists = new ArrayList<PointFeature>();
		lists.add(point);
		lists.add(point1);
		lists.add(point2);
		lists.add(point3);
		
		List<Marker> marks = new ArrayList<Marker>();
		for(PointFeature eq : lists){
			marks.add(new SimplePointMarker(eq.getLocation(),eq.getProperties()));
		}
		map.addMarkers(marks);
		int red = color(255,0,0);
		int yellow = color(255,209,0);
		for(Marker mk : marks){
			try {
				
				System.out.println(" >>>  " + mk.getProperty("magnitude"));
				if ((Double) mk.getProperty("magnitude") > 9) {
					mk.setColor(red);
				} else {
					mk.setColor(yellow);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		//SimplePointMarker val = new SimplePointMarker(obj);
		//map.addMarker(val);
	}
	

	public void draw() {
		background(0);
		map.draw();
		addkey();
	}

	private void addkey() {
		// TODO Auto-generated method stub
		
	}

}