package com.xpro.ebusalmoner.widget.scancode.android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Browser;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.widget.scancode.camera.CameraManager;
import com.xpro.ebusalmoner.widget.scancode.decoding.DecodeThread;
import com.xpro.ebusalmoner.widget.scancode.view.ViewfinderResultPointCallback;

import java.util.Collection;
import java.util.Map;

/**
 * Created by houyang on 2016/11/2.
 */
public  final class CaptureActivityHandler extends Handler {
    private static final String TAG = CaptureActivityHandler.class
            .getSimpleName();

    private final CaptureActivity activity;
    private final DecodeThread decodeThread;
    private State state;
    private final CameraManager cameraManager;

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public CaptureActivityHandler(CaptureActivity activity,
                                  Collection<BarcodeFormat> decodeFormats,
                                  Map<DecodeHintType, ?> baseHints, String characterSet,
                                  CameraManager cameraManager) {
        this.activity = activity;
        decodeThread = new DecodeThread(activity, decodeFormats, baseHints,
                characterSet, new ViewfinderResultPointCallback(
                activity.getViewfinderView()));
        decodeThread.start();
        state = State.SUCCESS;

        // Start ourselves capturing previews and decoding.
        // 寮�濮嬫媿鎽勯瑙堝拰瑙ｇ爜
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.restart_preview:
                // 閲嶆柊棰勮
                restartPreviewAndDecode();
                break;
            case R.id.decode_succeeded:
                // 瑙ｇ爜鎴愬姛
                state = State.SUCCESS;
                Bundle bundle = message.getData();
                Bitmap barcode = null;
                float scaleFactor = 1.0f;
                if (bundle != null) {
                    byte[] compressedBitmap = bundle
                            .getByteArray(DecodeThread.BARCODE_BITMAP);
                    if (compressedBitmap != null) {
                        barcode = BitmapFactory.decodeByteArray(compressedBitmap,
                                0, compressedBitmap.length, null);
                        // Mutable copy:
                        barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
                    }
                    scaleFactor = bundle
                            .getFloat(DecodeThread.BARCODE_SCALED_FACTOR);
                }
                activity.handleDecode((Result) message.obj, barcode, scaleFactor);
                break;
            case R.id.decode_failed:
                // We're decoding as fast as possible, so when one decode fails,
                // start another.
                // 灏藉彲鑳藉揩鐨勮В鐮侊紝浠ヤ究鍙互鍦ㄨВ鐮佸け璐ユ椂锛屽紑濮嬪彟涓�娆¤В鐮�
                state = State.PREVIEW;
                cameraManager.requestPreviewFrame(decodeThread.getHandler(),
                        R.id.decode);
                break;
            case R.id.return_scan_result:
                //鎵弿缁撴灉锛岃繑鍥濩aptureActivity澶勭悊
                activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
                activity.finish();
                break;
            case R.id.launch_product_query:
                String url = (String) message.obj;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.setData(Uri.parse(url));

                ResolveInfo resolveInfo = activity.getPackageManager()
                        .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                String browserPackageName = null;
                if (resolveInfo != null && resolveInfo.activityInfo != null) {
                    browserPackageName = resolveInfo.activityInfo.packageName;
                    Log.d(TAG, "Using browser in package " + browserPackageName);
                }

                // Needed for default Android browser / Chrome only apparently
                //闇�瑕侀粯璁ょ殑Android娴忚鍣ㄦ垨鑰匞oogle
                if ("com.android.browser".equals(browserPackageName)
                        || "com.android.chrome".equals(browserPackageName)) {
                    intent.setPackage(browserPackageName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID,
                            browserPackageName);
                }

                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                    Log.w(TAG, "Can't find anything to handle VIEW of URI " + url);
                }
                break;
        }
    }

    /**
     * 瀹屽叏閫�鍑�
     */
    public void quitSynchronously() {
        state = State.DONE;
        cameraManager.stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
        quit.sendToTarget();
        try {
            // Wait at most half a second; should be enough time, and onPause()
            // will timeout quickly
            decodeThread.join(500L);
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        //纭繚涓嶄細鍙戦�佷换浣曢槦鍒楁秷鎭�
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    public void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(),
                    R.id.decode);
            activity.drawViewfinder();
        }
    }
}
