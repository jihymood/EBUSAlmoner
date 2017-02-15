package com.xpro.ebusalmoner.widget.scancode.encode;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by houyang on 2016/11/2.
 */
public class CodeCreator {
    /**
     * 鐢熸垚QRCode锛堜簩缁寸爜锛�
     *
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String url) throws WriterException {

        if (url == null || url.equals("")) {
            return null;
        }

        // 鐢熸垚浜岀淮鐭╅樀,缂栫爜鏃舵寚瀹氬ぇ灏�,涓嶈鐢熸垚浜嗗浘鐗囦互鍚庡啀杩涜缂╂斁,杩欐牱浼氭ā绯婂鑷磋瘑鍒け璐�
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, 300, 300);

        int width = matrix.getWidth();
        int height = matrix.getHeight();

        // 浜岀淮鐭╅樀杞负涓�缁村儚绱犳暟缁�,涔熷氨鏄竴鐩存í鐫�鎺掍簡
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
