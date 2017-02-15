package com.xpro.ebusalmoner.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.constants.HttpUrls;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.PreferencesUtils;
import com.xpro.ebusalmoner.utils.ProgressDialogUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;
import com.xpro.ebusalmoner.widget.CustomDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

/**
 * 救济-->实施人员-->故障详情
 *
 * @author huangjh
 */
@ContentView(R.layout.adapter_routinemaintenance_s)
public class BreakdownActivity_S extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
    @ViewInject(R.id.save)
    private Button save;
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.driverTel)
    private ImageView driverTel;
    @ViewInject(R.id.takephoto)
    private ImageView takephoto;
    //	@ViewInject(R.id.imageView)
//	private ImageView imageView;
    @ViewInject(R.id.photo)
    private ImageView photo;
    @ViewInject(R.id.linearLayout)
    private LinearLayout linearLayout;
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    @ViewInject(R.id.editView)
    private EditText editView;
    @ViewInject(R.id.xcRadio)
    private RadioButton xcRadio;
    @ViewInject(R.id.jcRadio)
    private RadioButton jcRadio;
    @ViewInject(R.id.line)
    private TextView line;
    @ViewInject(R.id.lineNumber)
    private TextView lineNumber;
    @ViewInject(R.id.driverName)
    private TextView driverName;
    @ViewInject(R.id.lineTime)
    private TextView lineTime;
    @ViewInject(R.id.location)
    private TextView location;
    @ViewInject(R.id.checkdetails_hor) //横向滚动条里的LinearLayout
    private LinearLayout checkdetails_hor;

    private Uri imageUri;
    private CustomDialog choosePicDialog;
    private String lineStr, numberStr, driverNameStr, timeStr, telStr, cenpt1Addr;
    private String createBy, faultId, driverId;
    private String reason;//维修原因
    private String state;//状态
    private String photos;//图片地址

    private Bitmap head;// 头像Bitmap
    private static String IMAGE_CHECKDETAIL_FILE_NAME = System.currentTimeMillis() + ".jpg";
    private File outputImage;

    private MyHandler handler;
    private TaskLogic logic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getFromIntent();
        initListener();

        photos = "";
        state = "1";//默认state=1,即现场维修状态

        handler = new MyHandler(this);
        logic = new TaskLogic(handler, this);

        setTitle("故障详情");
    }

    private void initListener() {
        // TODO Auto-generated method stub
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        driverTel.setOnClickListener(this);
        takephoto.setOnClickListener(this);
//        photo.setOnClickListener(this);
//		imageView.setOnClickListener(this);
        editView.setOnClickListener(this);
        jcRadio.setOnCheckedChangeListener(this);
        xcRadio.setOnCheckedChangeListener(this);
    }

    public void getFromIntent() {
        Intent intent = getIntent();

        lineStr = intent.getStringExtra("lineStr");
        numberStr = intent.getStringExtra("numberStr");
        driverNameStr = intent.getStringExtra("driverNameStr");
        timeStr = intent.getStringExtra("timeStr");
        cenpt1Addr = intent.getStringExtra("cenpt1Addr");
        telStr = intent.getStringExtra("telStr");
        faultId = intent.getStringExtra("faultId");
        driverId = intent.getStringExtra("driverId");

        createBy = PreferencesUtils.getString(this, "LoginId");


        line.setText(lineStr);
        lineNumber.setText(numberStr);
        driverName.setText("驾驶员：" + driverNameStr);
        lineTime.setText(timeStr);
        location.setText(cenpt1Addr);
    }


    /**
     * 更换头像
     */
    private void getPhoto() {
        LinearLayout lyDlg;
        Button btnTakePhoto, btnAlbum, btnCancel;

        choosePicDialog = new CustomDialog(this, true);
        choosePicDialog.setCustomView(R.layout.dlg_choose_pic);

        Window window = choosePicDialog.getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        lyDlg = (LinearLayout) choosePicDialog.findViewById(R.id.dialog_layout);
        lyDlg.setPadding(0, 0, 0, 0);

        btnTakePhoto = (Button) choosePicDialog.findViewById(R.id.btnTakePhoto);
        btnAlbum = (Button) choosePicDialog.findViewById(R.id.btnAlbum);
        btnCancel = (Button) choosePicDialog.findViewById(R.id.btnCancel);

        // 拍照
        btnTakePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
                IMAGE_CHECKDETAIL_FILE_NAME = System.currentTimeMillis() + ".jpg";
                outputImage = new File(Environment.getExternalStorageDirectory(), IMAGE_CHECKDETAIL_FILE_NAME);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent_pz = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent_pz.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent_pz, PIC_TAKEPHOTO);
            }
        });

        // 相册
        btnAlbum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
                outputImage = new File(Environment.getExternalStorageDirectory(), IMAGE_CHECKDETAIL_FILE_NAME);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent_xc = new Intent(Intent.ACTION_PICK, imageUri);
//                intent_xc.setType("image/*");
//                intent_xc.putExtra("crop", true);
//                intent_xc.putExtra("scale", true);
                intent_xc.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent_xc.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent_xc, PIC_ALBUM);
            }
        });

        // 取消
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
            }
        });
        choosePicDialog.setCancelable(true);
        choosePicDialog.show();
    }

    /**
     * 调用系统的裁剪功能
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PIC_ALBUM: //相册
                if (resultCode == RESULT_OK) {
//                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case PIC_TAKEPHOTO: //拍照
                if (resultCode == RESULT_OK) {
//                    cropPhoto(Uri.fromFile(outputImage));// 裁剪图片
                    ProgressDialogUtils.showProgressDialog("正在上传中", BreakdownActivity_S.this, false);
                    //图片上传接口
                    logic.uploadImage(outputImage, "1", "relief", PreferencesUtils.getString(BreakdownActivity_S.this,
                            "LoginId"));
                }
                break;
            case CROP_PHOTO:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);
//                        iv_photo.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
        }
    }

    public void setPicToView(Bitmap bitmap) {
        ProgressDialogUtils.showProgressDialog("正在上传中，请稍等", this, false);


        //图片上传接口
        logic.uploadImage(outputImage, "1", "relief", PreferencesUtils.getString(BreakdownActivity_S.this,
                "LoginId"));

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.save:
                showConfirmTip("确定保存", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        //保存接口
                        logic.saveReliefDetail(faultId, reason, state, photos, createBy, driverId);

                        finish();
                    }
                }, new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
                break;

            case R.id.driverTel://打电话
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_CALL);
                        intent1.setData(Uri.parse("tel:" + telStr));
                        startActivity(intent1);
                    }
                }, new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub

                    }
                });
                break;

            case R.id.takephoto://拍照
//                getPhoto();
                IMAGE_CHECKDETAIL_FILE_NAME = System.currentTimeMillis() + ".jpg";
                outputImage = new File(Environment.getExternalStorageDirectory(), IMAGE_CHECKDETAIL_FILE_NAME);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent_pz = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent_pz.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent_pz, PIC_TAKEPHOTO);
                break;

            /*case R.id.photo://图片
                String path = "http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg";
                initPopupWindow(linearLayout, path);
                break;*/

            case R.id.editView://输入框
//                editView.requestFocus();
//                editView.setFocusable(true);
//                editView.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInput(editView, 0);

                break;

            default:
                break;
        }
    }

    //监听onTouchEvent事件，关闭软键盘。
    //	getWindow().getDecorView()的意思是获取window的最前面的view。软键盘是phonewindow的跟view
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //关闭软键盘
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindow().getDecorView()
                .getWindowToken(), 0);

        return super.onTouchEvent(event);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.xcRadio:
                    editView.requestFocus();
                    editView.setHint("现场维修真实情况填写");
                    state = "1";
                    reason = editView.getText().toString();
                    break;
                case R.id.jcRadio:
                    editView.requestFocus();
                    editView.setHint("进场维修真实情况填写");
                    state = "2";
                    reason = editView.getText().toString();
                    break;

                default:
                    break;
            }
        }
    }

    public class MyHandler extends BaseHandler {
        public MyHandler(Context context) {
            super(context);
        }

        @Override
        public void doHandle(Message msg) {
            super.doHandle(msg);
            switch (msg.what) {
                //实施人员保存故障信息成功
                case Constants.SAVERELIEFDETAIL_SUCCESS:
//                    ToastUtils.showToast(BreakdownActivity_S.this, "" + msg.obj);
                    ToastUtils.showToast(BreakdownActivity_S.this, "保存成功");
                    break;

                //实施人员保存故障信息失败
                case Constants.SAVERELIEFDETAIL_FAIL:
                    ToastUtils.showToast(BreakdownActivity_S.this, "" + msg.obj);
                    break;

                //上传图片返回成功
                case Constants.HANDLER_IMAGE_SUCCESS:
                    String filepath = (String) msg.obj;
//                    upLoadPath.get(positionPhoto).add(path1);
                    Log.e("path1", filepath);
                    photos += filepath + ";";

                    showPhoto(filepath); //上传成功后将图片显示出来

                    break;

                //上传图片返回失败
                case Constants.HANDLER_IMAGE_FAIL:
                    ProgressDialogUtils.dismissProgressBar();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 上传成功后将图片显示出来
     *
     * @param filepath
     */
    public void showPhoto(String filepath) {
        final String path = HttpUrls.showImageUrl(BreakdownActivity_S.this) + filepath;

        LinearLayout linearLayouttest = new LinearLayout(this);
        LinearLayout linearLayout5 = new LinearLayout(this);
        linearLayout5.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params5.setMargins(10, 0, 5, 0);
        ViewGroup.LayoutParams params_imageview5 = new ViewGroup.LayoutParams(100, 120);
        final ImageView imageView5 = new ImageView(this);
        x.image().bind(imageView5, path);

        linearLayout5.addView(imageView5, params_imageview5);
        linearLayouttest.addView(linearLayout5, params5);
        checkdetails_hor.addView(linearLayouttest);

        ProgressDialogUtils.dismissProgressBar();

        //查看大图
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow(linearLayout, path);
            }
        });

        //长按删除
        imageView5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                initPopup_delete(linearLayout, imageView5);
                return true;
            }
        });

    }

}
