package cn.sharesdk.demo;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

public class UriUtil {

    public static String convertUriToPath(Context context, Uri uri) {
        if (uri == null)
            return null;
        String schema = uri.getScheme();
        if (TextUtils.isEmpty(schema) || ContentResolver.SCHEME_FILE.equals(schema)) {
            return uri.getPath();
        }
        if ("http".equals(schema))
            return uri.toString();
        if (ContentResolver.SCHEME_CONTENT.equals(schema)) {
            String[] projection = new String[]{MediaStore.MediaColumns.DATA};
            Cursor cursor = null;
            String filePath = "";
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(0);
                    }

                    cursor.close();
                }
            } catch (Exception e) {
                // do nothing
            } finally {
                try {
                    if (null != cursor) {
                        cursor.close();
                    }
                } catch (Exception e2) {
                    // do nothing
                }
            }
            if (TextUtils.isEmpty(filePath)) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    String selection = MediaStore.Images.Media._ID + "= ?";
                    String id = uri.getLastPathSegment();
                    if (Build.VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(id) && id.contains(":")) {
                        id = id.split(":")[1];
                    }
                    String[] selectionArgs = new String[]{id};
                    cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(0);
                    }
                    if (null != cursor) {
                        cursor.close();
                    }

                } catch (Exception e) {
                    // do nothing
                } finally {
                    try {
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            }
            return filePath;
        }
        return null;
    }

    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

}
