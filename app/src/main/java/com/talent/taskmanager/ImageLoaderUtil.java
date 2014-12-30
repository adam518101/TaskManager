package com.talent.taskmanager;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageLoaderUtil {
	
	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.ic_picture)
//			.showImageForEmptyUri(R.drawable.ic_picture)
//			.showImageOnFail(R.drawable.ic_picture)
			.cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .delayBeforeLoading(500)
			.cacheOnDisk(true)
			.considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(0, 0))
			//.displayer(new FadeRoundedBitmapDisplayer(10, 500, 1)/*new FadeRoundedBitmapDisplayer(10, 500)*/)
			.build();
	
	public static DisplayImageOptions getOption(){
		return options;
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
