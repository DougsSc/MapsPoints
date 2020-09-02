package br.univates.mapspoints.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {
	private static Utils instancia = new Utils();

	private Utils() {
	}

	public static Utils getInstance() {
		return (instancia);
	}

	public float getPerc2PxAXIS(final Context c, float perc, char axis) {
		float res = 0;
		char eaxis = 'x';
		float auxx = 0f;
		float auxy = 0f;
		float cont = 0;
		while (cont != 2) {
			cont++;
			if (eaxis == 'x') {
				Display display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int x = size.x;
				float dp = (x * perc) / 100;
				auxx = dp;
				res = dp;
				eaxis = 'y';
			} else if (eaxis == 'y') {
				Display display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int y = size.y;
				float dp = (y * perc) / 100;
				auxy = dp;
				res = dp;
				eaxis = 'x';
			} else if (axis == 'z') {
			}
		}

		if (auxx > auxy) {
			float auxiliar = auxx;
			auxx = auxy;
			auxy = auxiliar;
		}

		if (axis == 'x') {
			res = auxx;
		} else if (axis == 'y') {
			res = auxy;
		}
		return res;
	}
}

