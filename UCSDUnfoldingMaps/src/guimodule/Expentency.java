package guimodule;

import java.applet.Applet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class Expentency extends PApplet {
	Map<String, Float> life;
	List<Feature> countries;
	List<Marker> countryMarkers;
	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500,
				new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		life = result("LifeExpectancyWorldBankModule3.csv");

		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shade();
	}

	private Map<String, Float> result(String fileName) {
		Map<String, Float> map = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		for (String row : rows) {
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				map.put(columns[4], Float.parseFloat(columns[5]));
			}

		}
		return map;

	}
	private void shade(){
		for(Marker mk : countryMarkers){
			String countryid = mk.getId();
			if(life.containsKey(countryid)){
				float explife = life.get(countryid);
				int colors = (int) map(explife,40,80,90,255);
				mk.setColor(color((255-colors),100, colors));
			}
		}
	}
	public void draw(){
		background(0);
		map.draw();
	}

}
