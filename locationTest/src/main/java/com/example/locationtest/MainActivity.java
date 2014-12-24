package com.example.locationtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.locationtest.util.FileOperationUtils;
import com.example.locationtest.util.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int REQ_CODE_CAPTURE_PICTURE = 101;
	public static final int REQ_CODE_SELECT_PICTURE = 102;
	public static final int REQ_CODE_RECORD_AUDIO = 103;
	public static final int REQ_CODE_SELECT_AUDIO = 104;

	private static final int BUTTON_CAPTURE_IMAGE = R.id.btn_capture_image;
	private static final int BUTTON_SELECT_IMAGE = R.id.btn_select_image;
	private static final int BUTTON_RECORD_AUDIO = R.id.btn_record_audio;
	private static final int BUTTON_SELECT_AUDIO = R.id.btn_select_audio;

	private static final String FILE_TYPE_IMAGE = "image/*";
	private static final String FILE_TYPE_AUDIO = "audio/*";

	private static final String TEMP_FILE_NAME = "image.jpeg";
	private static final String TAG = "Test";
	private LocationManager mLocationManager;
	private Button mBtnGetLocation = null;
	private TextView mTxtLocation = null;
	private Button mBtnCapture = null;
	private Button mBtnSelectImage = null;
	private Button mBtnSoundRecord = null;
	private Button mBtnSelectAudio = null;
	private ImageButton mAudioFile = null;
	private ImageView mImage = null;
	private List<String> mAudioPath = new ArrayList<String>();
	private Toast mtoast = null;
	private long mTaskId = 1228;		// Need to get specified task id.
	private String mTaskFilePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LocationUI mLocationUI = new LocationUI();
		mLocationManager = new LocationManager(getApplicationContext(),
				mLocationUI);

		mTxtLocation = (TextView) this.findViewById(R.id.text_location);

		mBtnGetLocation = (Button) this.findViewById(R.id.btn_get_location);
		mBtnGetLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Location loc = mLocationManager.getCurrentLocation();

				if (loc != null) {
					Log.d(TAG, "loc = " + loc);
					mTxtLocation.setText("Location: (" + loc.getLatitude()
							+ ", " + loc.getLongitude() + ")" + "\nTime: "
							+ Utils.convertTime(loc.getTime()));
				} else {
					mTxtLocation.setText("No result!");
					Log.d(TAG, "null");
				}
			}
		});

		ButtonOnclickListener buttonOnclickListener = new ButtonOnclickListener();
		// Capture a image
		mBtnCapture = (Button) this.findViewById(BUTTON_CAPTURE_IMAGE);
		mBtnCapture.setOnClickListener(buttonOnclickListener);
		// Select a image
		mBtnSelectImage = (Button) findViewById(BUTTON_SELECT_IMAGE);
		mBtnSelectImage.setOnClickListener(buttonOnclickListener);
		// Record an audio
		mBtnSoundRecord = (Button) this.findViewById(BUTTON_RECORD_AUDIO);
		mBtnSoundRecord.setOnClickListener(buttonOnclickListener);
		// Select an audio
		mBtnSelectAudio = (Button) findViewById(BUTTON_SELECT_AUDIO);
		mBtnSelectAudio.setOnClickListener(buttonOnclickListener);

		mAudioFile = (ImageButton) this.findViewById(R.id.audio_1);
		mAudioFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (mAudioPath.size() > 0) {
					File file = new File(mAudioPath.get(0));
					if (file != null && file.exists()) {
						FileOperationUtils.openFile(file, getApplication());
					} else {
						mtoast = Toast.makeText(getApplication(),
								"Audio is not exist", Toast.LENGTH_SHORT);
						mtoast.show();
					}
				}
			}
		});

		mImage = (ImageView) findViewById(R.id.image_1);
		mImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(
						Uri.fromFile(new File(mImage.getTag().toString())),
						FILE_TYPE_IMAGE);
				startActivity(intent);
//				File file = new File(mImage.getTag().toString());
//				if (file != null && file.exists()) {
//					FileOperationUtils.openFile(file, getApplication());
//				} else {
//					mtoast = Toast.makeText(getApplication(),
//							"Image is not exist", Toast.LENGTH_SHORT);
//					mtoast.show();
//				}
			}
		});

		mTaskFilePath = Storage.DIRECTORY + "/" + mTaskId;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mLocationManager != null)
			mLocationManager.recordLocation(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mLocationManager != null)
			mLocationManager.recordLocation(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQ_CODE_CAPTURE_PICTURE:
				captureImageResult(data);
				break;
			case REQ_CODE_SELECT_PICTURE:
				selectImageResult(data);
				break;
			case REQ_CODE_RECORD_AUDIO:
				recordAudioResult(data);
				break;
			case REQ_CODE_SELECT_AUDIO:
				selectAudioResult(data);
				break;
			default:
				break;
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private class LocationUI implements LocationManager.Listener {
		@Override
		public void showGpsOnScreenIndicator(boolean hasSignal) {
		}

		@Override
		public void hideGpsOnScreenIndicator() {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class ButtonOnclickListener implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (!isEnableOperation()) {
				return;
			}
			// Create folder if it is not exist.
			File dir = new File(mTaskFilePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// Add .nomedia file that will not be scanned
			File noMediaFile = new File(mTaskFilePath + "/" + ".nomedia");
			if (!noMediaFile.exists()) {
				try {
					noMediaFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			switch (arg0.getId()) {
			case BUTTON_CAPTURE_IMAGE:
				captureImage();
				break;
			case BUTTON_SELECT_IMAGE:
				selectImage();
				break;
			case BUTTON_RECORD_AUDIO:
				recordAudio();
				break;
			case BUTTON_SELECT_AUDIO:
				selectAudio();
				break;
			}
		}
	}

	/* Four actions: capture image, select image, record audio, and select audio */
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(mTaskFilePath, TEMP_FILE_NAME));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, REQ_CODE_CAPTURE_PICTURE);
	}

	private void selectImage() {
		Intent intent = new Intent();
		intent.setType(FILE_TYPE_IMAGE);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, REQ_CODE_SELECT_PICTURE);
	}

	private void recordAudio() {
		Intent intent = new Intent(Media.RECORD_SOUND_ACTION);
		startActivityForResult(intent, REQ_CODE_RECORD_AUDIO);
	}

	private void selectAudio() {
		Intent intent = new Intent();
		intent.setType(FILE_TYPE_AUDIO);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, REQ_CODE_SELECT_AUDIO);
	}

	/* Four corresponding results of actions above */
	private void captureImageResult(Intent data) {
		String name = Utils.getImageName(System.currentTimeMillis());
		File oldFile = new File(mTaskFilePath, TEMP_FILE_NAME);
		File newFile = new File(mTaskFilePath, name);
		oldFile.renameTo(newFile);
		MediaScannerConnection.scanFile(getApplication(), new String[] {
				mTaskFilePath, name }, null, null);
		mtoast = Toast.makeText(getApplication(), "Image has saved in folder "
				+ mTaskFilePath, Toast.LENGTH_SHORT);
		mtoast.show();

		String path = mTaskFilePath + "/" + name;
		Bitmap bitmap = FileOperationUtils.compressImageBySrc(path);
		if (bitmap != null) {
			// Delete original image, and save compressed image.
			newFile.delete();
			FileOperationUtils.saveBitmapToFile(bitmap, path);
		}
		mImage.setImageURI(Uri.parse(path));
		mImage.setTag(path);

		Log.d(TAG, "captureImageResult, path = " + path);
		// Bundle bundle = data.getExtras();
		// Bitmap bitmap = (Bitmap) bundle.get("data");
		// Storage.generateImage(Util.getTitile(System.currentTimeMillis()),
		// bitmap);
	}

	private void selectImageResult(Intent data) {
		Uri uri = data.getData();
		String oldPath = FileOperationUtils.getFilePathByUri(uri, this);
		if (FILE_TYPE_IMAGE.equals(FileOperationUtils.getMIMEType(new File(oldPath)))) {
			Bitmap bitmap = FileOperationUtils.compressImageBySrc(oldPath);
			String name = Utils.getImageName(System.currentTimeMillis());
			String newPath = mTaskFilePath + "/" + name;
			FileOperationUtils.saveBitmapToFile(bitmap, newPath);
			mImage.setImageURI(Uri.parse(newPath));
			mImage.setTag(newPath);
			MediaScannerConnection.scanFile(getApplication(),
					new String[] { mTaskFilePath }, null, null);
			Log.d(TAG, "selectImageResult, path = " + newPath);
		} else {
			mtoast = Toast.makeText(getApplication(), "Invalid image file!",
					Toast.LENGTH_SHORT);
			mtoast.show();
		}
	}

	private void recordAudioResult(Intent data) {
		Uri uri = data.getData();
		String oldPath = FileOperationUtils.getFilePathByUri(uri, this);
		String newPath = mTaskFilePath + "/"
				+ Utils.getAudioName(System.currentTimeMillis());
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		oldFile.renameTo(newFile);
		MediaScannerConnection.scanFile(getApplication(),
				new String[] { mTaskFilePath }, null, null);

		if (mAudioPath.size() != 0)
			mAudioPath.remove(0);
		mAudioPath.add(newPath);
		mtoast = Toast.makeText(getApplication(), "Audio has saved in folder "
				+ mTaskFilePath, Toast.LENGTH_SHORT);
		mtoast.show();
		Log.d(TAG, "recordAudioResult, path = " + newPath);
	}

	private void selectAudioResult(Intent data) {
		Uri uri = data.getData();
		String oldPath = FileOperationUtils.getFilePathByUri(uri, this);
		if (FILE_TYPE_AUDIO.equals(FileOperationUtils.getMIMEType(new File(oldPath)))) {
			String newPath = mTaskFilePath + "/"
					+ Utils.getAudioName(System.currentTimeMillis());
			FileOperationUtils.copyFile(oldPath, newPath);
			MediaScannerConnection.scanFile(getApplication(),
					new String[] { mTaskFilePath }, null, null);
			
			if (mAudioPath.size() != 0)
				mAudioPath.remove(0);
			mAudioPath.add(newPath);
			Log.d(TAG, "selectAudioResult, path = " + newPath);
		} else {
			mtoast = Toast.makeText(getApplication(), "Invalid audio file!", Toast.LENGTH_SHORT);
			mtoast.show();
		}
	}
	
	private boolean isEnableOperation() {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			Log.i(TAG, "SD card is not avaiable now.");
			mtoast = Toast.makeText(getApplication(), "SD card is not avaiable now!", Toast.LENGTH_SHORT);
			mtoast.show();
			return false;
		}
		return true;
	}
}
