import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;

public class GoogleMapsPanel extends MapView {

	private DatabaseMapDataSet[] mapDataSet;

	public GoogleMapsPanel(MapViewOptions options, DatabaseMapDataSet[] DataSet) {

		super(options);
		this.mapDataSet = DataSet;

		setOnMapReadyHandler(new MapReadyHandler() {

			@Override
			public void onMapReady(MapStatus status) {
				if (status == MapStatus.MAP_STATUS_OK) {
					final Map map = getMap();
					map.setZoom(3.0);
					GeocoderRequest request = new GeocoderRequest(map);
					map.setCenter(new LatLng(map, 35.91466, 10.312499));
					for (DatabaseMapDataSet dataSet : mapDataSet) {
						request.setAddress(dataSet.getCityName());

						getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
							@Override
							public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
								if (status == GeocoderStatus.OK) {

									Marker marker = new Marker(map);
									marker.setPosition(result[0].getGeometry().getLocation());

									final InfoWindow window = new InfoWindow(map);
									window.setContent(
											dataSet.getCityName() + ": " + dataSet.getNumberofUsers() + " users");
									window.open(map, marker);

								}
							}
						});
					}

				}
			}
		});
	}
}