package com.tian.project.chabaike.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CommonDialog {
	public static void confirm(ConfirmObject confirmObject){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(confirmObject.context);
		builder.setTitle(confirmObject.title);
		builder.setMessage(confirmObject.message);
		builder.setPositiveButton(confirmObject.positiveText, confirmObject.positiveListener);
		builder.setNegativeButton(confirmObject.negativeText, confirmObject.negativeListener);
		builder.show();
	}
	
	public class ConfirmObject{
		public Context context;
		public String title;
		public String message;
		public String positiveText;
		public DialogInterface.OnClickListener positiveListener;
		public String negativeText;
		public DialogInterface.OnClickListener negativeListener;
	}
}
