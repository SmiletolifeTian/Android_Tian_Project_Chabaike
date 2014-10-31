package com.tian.project.chabaike.task;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

import com.tian.project.chabaike.common.CommonDialog;
import com.tian.project.chabaike.common.CommonDialog.ConfirmObject;
import com.tian.project.chabaike.defaulthandler.UpdateHandler;
import com.tian.project.chabaike.entity.Update;

public class UpdateTask extends AsyncTask<String, Void, Void> {
	private Context context;
	private Update update;
	
	public UpdateTask(Context context){
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			UpdateHandler handler = new UpdateHandler();
			parser.parse(params[0], handler);
			
			update = handler.getUpdate();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		try {
			int currentVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
			if (update.getVersion() > currentVersion) {
				ConfirmObject confirmObject = new ConfirmObject();
				confirmObject.context = context;
				confirmObject.message = update.getMessage();
				confirmObject.title = update.getName();
				confirmObject.positiveText = "Éý¼¶";
				confirmObject.positiveListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Éý¼¶
						
					}
				};
				confirmObject.negativeText = "ºöÂÔ";
				confirmObject.negativeListener = new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//ºöÂÔ
						
					}
				};
				CommonDialog.confirm(confirmObject);

			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	

}
