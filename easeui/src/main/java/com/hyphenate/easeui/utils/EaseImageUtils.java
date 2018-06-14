/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.hyphenate.util.PathUtil;

public class EaseImageUtils extends com.hyphenate.util.ImageUtils{
	
	public static String getImagePath(String remoteUrl)
	{
		String imageName= remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1, remoteUrl.length());
		String path =PathUtil.getInstance().getImagePath()+"/"+ imageName;
        EMLog.d("msg", "image path:" + path);
        return path;
		
	}
	
	public static String getThumbnailImagePath(String thumbRemoteUrl) {
		String thumbImageName= thumbRemoteUrl.substring(thumbRemoteUrl.lastIndexOf("/") + 1, thumbRemoteUrl.length());
		String path =PathUtil.getInstance().getImagePath()+"/"+ "th"+thumbImageName;
        EMLog.d("msg", "thum image path:" + path);
        return path;
    }

    public static Bitmap getScaleImage(String filePath, int destw, int desth) {
		BitmapFactory.Options options = ImageUtils.getBitmapOptions(filePath);

		int width = options.outWidth;
		int height = options.outHeight;

		float scaleRate = 1f;//缩小的比例
		if(destw/(width*1f) > desth/(height*1f)) {
			scaleRate = desth/(height*1f);
		} else {
			scaleRate = destw/(width*1f);
		}

		Bitmap bitmap = BitmapFactory.decodeFile(filePath, null);;
		if(scaleRate>1.1f || scaleRate<0.9f) {
			Matrix matrix = new Matrix();
			matrix.postScale(scaleRate, scaleRate);
			Bitmap newIcon = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			bitmap.recycle();
			bitmap = newIcon;
		}

		int var6 = readPictureDegree(filePath);
		if(bitmap != null && var6 != 0) {
			Bitmap bitmap1 = rotateImageView(var6, bitmap);
			bitmap.recycle();
			return bitmap1;
		} else {
			return bitmap;
		}

	}
	
}
