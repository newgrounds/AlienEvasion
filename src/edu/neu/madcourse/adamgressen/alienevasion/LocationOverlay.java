package edu.neu.madcourse.adamgressen.alienevasion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import edu.neu.madcourse.adamgressen.R;

public class LocationOverlay extends Overlay{
	GeoPoint p;
	GeoPoint prev;
	int index;
	int pursuing;
	Bitmap playerImage;
	Evade evade;
	
	// Set up paint
	Paint paint = new Paint();
	Paint textPaint = new Paint();

	public LocationOverlay(Context context, GeoPoint p, int index) {
		this.p = p;
		this.evade = (Evade)context;
		pursuing = Evade.getPursuing();
		
		playerImage = BitmapFactory.decodeResource(evade.getResources(), R.drawable.player);
		
		paint.setStrokeWidth(4);
		paint.setARGB(80,0,0,255);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		textPaint.setStrokeWidth(4);
		textPaint.setTextSize(40);
		textPaint.setARGB(255, 0, 0, 255);
		textPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		super.draw(canvas, mapView, shadow);
		
		// Convert lat and long to screen coordinates
		Point point = new Point();
		Projection proj = mapView.getProjection();
		proj.toPixels(p, point);
		
		//System.out.println(index);
		
		// Draw line to previous GeoPoint
		if (index == 0)
			this.prev = null;
		else
			this.prev = evade.locPositions.get(index-1);
		
		if (this.prev != null) {
			Point previousPoint = new Point();
			GeoPoint previousGeoPoint = prev;
			proj.toPixels(previousGeoPoint, previousPoint);
			canvas.drawLine(previousPoint.x, previousPoint.y, point.x, point.y, paint);
		}

		if (this.index == evade.locPositions.size()-1) {
            canvas.drawBitmap(playerImage, point.x-(playerImage.getWidth()/2), point.y-(playerImage.getHeight()/2), null);
			//canvas.drawCircle(point.x, point.y, 20, paint);
			canvas.drawText(Evade.getDist()+" miles", point.x+20, point.y, textPaint);
		}

		return true;
	}

	public GeoPoint getPoint() {
		return this.p;
	}
}