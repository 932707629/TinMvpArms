package me.soushin.higou.base.app.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.integration.AppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import razerdp.basepopup.BasePopupWindow;

/**
 * @auther SouShin
 * @time 2019/8/23 17:23
 */
public class AppUtils {

    /**
     * 避免快速重复点击
     *
     * @return
     */
    private static long lastClickTime = 0;//上次点击的时间
    private static int spaceTime = 600;//时间间隔

    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick=currentTime - lastClickTime > spaceTime;
        lastClickTime = currentTime;
        return !isAllowClick;
    }

    /**
     * 检查应用是否存在
     * @param packageName
     * @return
     */
    public static boolean checkApplication(Context context,String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 文本复制
     * @param context
     * @param text
     */
    public static void copyText(Context context,String text){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    /**
     * recyclerview滑动距离
     * 有header的情况
     * @param manager
     * @return
     */
    public static int getScrollYDistance(LinearLayoutManager manager, int topHeight) {
        int position = manager.findFirstVisibleItemPosition();
        View firstVisiableChildView = manager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        int height = (position) * itemHeight - firstVisiableChildView.getTop();
        return position == 0 ? height : topHeight;
    }

    /**
     * recyclerview滑动距离
     * 无header的情况
     * @param manager
     * @return
     */
    public static int getScrollYDistance(LinearLayoutManager manager) {
        int position = manager.findFirstVisibleItemPosition();
        View firstVisiableChildView = manager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        int height = (position) * itemHeight ;
        return height;
    }

    /**
     * 计算透明度
     *
     * @param minHeight
     * @param maxHeight
     * @param scrollY
     * @return
     */
    public static int computeAlpha(int minHeight, int maxHeight, int scrollY) {
        int mAlpha;
        if (scrollY <= minHeight) {
            mAlpha = 0;
        } else if (scrollY > maxHeight) {
            mAlpha = 255;
        } else {
            mAlpha = (scrollY - minHeight) * 255 / (maxHeight - minHeight);
        }
        return mAlpha;
    }

    /**
     * 这个方法要做子线程执行
     * @param bitmap
     * @param path
     */
    public static void saveBitmap(Bitmap bitmap, String path){
        File filePic;
        try {
            filePic = new File(path);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            updateGallery(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateGallery(String path) {
        try {
            File file=new File(path);
            Context context= AppManager.getAppManager().getCurrentActivity();
            MediaStore.Images.Media.insertImage(context.getContentResolver(),path,file.getName(),null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }catch (Exception e){
        }
    }

    @SuppressLint("MissingPermission")
    public static String getDeviceId(){
        TelephonyManager tm = (TelephonyManager)getThis().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            return tm.getImei();
        }
        return tm.getDeviceId();
    }

    public static String getAndroidId(){
        return Settings.System.getString(getThis().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 使用系统发送分享数据
     *
     * @param context 上下文
     * @param text    要分享的文本
     */
    public static void toShare(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    private static Context getThis(){
        return AppManager.getAppManager().getCurrentActivity().getApplicationContext();
    }

    public static String getString(TextView tv){
        return tv.getText().toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Collection list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Object o) {
        return o == null || isEmpty(o.toString());
    }

}
