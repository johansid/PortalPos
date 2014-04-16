package cn.burgeon.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap.CompressFormat;
import android.preference.PreferenceManager;
import cn.burgeon.core.ic.ImageCacheManager;
import cn.burgeon.core.ic.ImageCacheManager.CacheType;
import cn.burgeon.core.net.RequestManager;

public class App extends Application {
	private SharedPreferences mPreferences;

	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so quality is ignored but must be provided

	@Override
	public void onCreate() {
		super.onCreate();

		init();
	}

	/**
	 * Intialize the request manager and the image cache
	 */
	private void init() {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		RequestManager.init(this);
		createImageCache();
	}

	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.
	 */
	private void createImageCache() {
		ImageCacheManager.getInstance().init(this, this.getPackageCodePath(), DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY, CacheType.MEMORY);
	}

	// ---------------------------------------------------- SharedPreferences begin
	public void cacheDataToSP(String key, String toCache) {
		Editor edit = mPreferences.edit();
		edit.putString(key, toCache);
		edit.commit();
	}

	public String getCachedDataFromSP(String key) {
		return mPreferences.getString(key, null);
	}

	public void removeCachedDataFromSP(String key) {
		Editor edit = mPreferences.edit();
		edit.remove(key);
		edit.commit();
	}
	// ---------------------------------------------------- SharedPreferences end
}
