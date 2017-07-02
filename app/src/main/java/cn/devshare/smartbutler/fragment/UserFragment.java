package cn.devshare.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.User;
import cn.devshare.smartbutler.ui.CourierActivity;
import cn.devshare.smartbutler.ui.LoginActivity;
import cn.devshare.smartbutler.ui.PhoneActivity;
import cn.devshare.smartbutler.utils.ToastUtil;
import cn.devshare.smartbutler.utils.UtilTools;
import cn.devshare.smartbutler.view.CustomDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.fragment
 * Class describe:个人中心
 * Author: cheng
 * Create time: 2017/6/26 19:46
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    private Button exitUserBt;
    private TextView editUser;//点击更新用户信息

    private EditText usernameEt;
    private EditText sexEt;
    private EditText ageEt;
    private EditText descEt;
    //更新按钮
    private Button updateOkBt;
    //圆形头像
    private CircleImageView profile_image;
    private CustomDialog dialog;

    private Button cameraBt;
    private Button pictureBt;
    private Button cancelBt;

    //快递查询
    private TextView courierTv;
    //归属地查询
    private TextView phoneTv;
    private boolean isEnable=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        exitUserBt = (Button) view.findViewById(R.id.exit_user_bt);
        exitUserBt.setOnClickListener(this);
        editUser = (TextView) view.findViewById(R.id.user_edit_tv);
        editUser.setOnClickListener(this);
        courierTv = (TextView) view.findViewById(R.id.courier_tv);
        courierTv.setOnClickListener(this);
        phoneTv = (TextView) view.findViewById(R.id.phone_tv);
        phoneTv.setOnClickListener(this);

        usernameEt = (EditText) view.findViewById(R.id.username_et);
        sexEt = (EditText) view.findViewById(R.id.sex_et);
        ageEt = (EditText) view.findViewById(R.id.age_et);
        descEt = (EditText) view.findViewById(R.id.desc_et);

        updateOkBt = (Button) view.findViewById(R.id.update_ok_bt);
        updateOkBt.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        dialog=new CustomDialog(getContext(),0, 0,R.layout.dialog_photo,R.style.pop_anim_style, Gravity.BOTTOM,0);
        cameraBt= (Button) dialog.findViewById(R.id.camera_bt);
        pictureBt= (Button) dialog.findViewById(R.id.picture_bt);
        cancelBt= (Button) dialog.findViewById(R.id.cancel_bt);
        cameraBt.setOnClickListener(this);
        pictureBt.setOnClickListener(this);
        cancelBt.setOnClickListener(this);

        setEnableEdit(false);
        User userInfo= BmobUser.getCurrentUser(User.class);
        usernameEt.setText(userInfo.getUsername());
        sexEt.setText(userInfo.isSex()?"男":"女");
        ageEt.setText(userInfo.getAge()+"");
        descEt.setText(userInfo.getDesc());

    }

    //设置是否可以修改
    private void setEnableEdit(boolean isEdit) {
        usernameEt.setEnabled(isEdit);
        sexEt.setEnabled(isEdit);
        ageEt.setEnabled(isEdit);
        descEt.setEnabled(isEdit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_user_bt:
                exitLogin();
                break;
            case R.id.user_edit_tv:
                if(!isEnable){
                    setEnableEdit(true);
                    isEnable=true;
                    updateOkBt.setVisibility(View.VISIBLE);
                }else {
                    setEnableEdit(false);
                    isEnable=false;
                    updateOkBt.setVisibility(View.GONE);
                }
                break;
            case R.id.update_ok_bt:
                udpate();
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.camera_bt:
                toCamera();
                break;
            case R.id.picture_bt:
                toPicture();
                break;
            case R.id.cancel_bt:
                dialog.dismiss();
                break;
            case R.id.courier_tv:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.phone_tv:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转到相机
    private void toCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);

    }
    //跳转到相册
    private void toPicture(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=getActivity().RESULT_CANCELED){
            switch (requestCode){
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile=new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    if(data!=null){
                        setImageToView(data);
                        if(tempFile!=null){
                            tempFile.delete();
                        }
                    }
                    break;

            }
        }
    }

    //设置头像
    private void setImageToView(Intent data) {
        Bundle bundle=data.getExtras();
        if(bundle!=null){
            Bitmap bitmap=bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    private void startPhotoZoom(Uri data) {
        if (data != null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);

        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //退出登录
    private void exitLogin(){
        User.logOut();
        User user=BmobUser.getCurrentUser(User.class);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    //更新用户信息
    private void udpate() {
        String username = usernameEt.getText().toString();
        String age = ageEt.getText().toString();
        String sex = sexEt.getText().toString();
        String desc = descEt.getText().toString();
        if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
            User user=new User();
            user.setUsername(username);
            user.setAge(Integer.parseInt(age));
            if(sex.equals(R.string.boy)){
                user.setSex(true);
            } else {
                user.setSex(false);
            }
            if (!TextUtils.isEmpty(desc)) {
                user.setDesc(desc);
            } else {
                user.setDesc(getString(R.string.text_nothing));
            }

            BmobUser bmobUser=BmobUser.getCurrentUser();
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        setEnableEdit(false);
                        updateOkBt.setVisibility(View.GONE);
                        ToastUtil.TS(getContext(),"更新成功");
                    }else {
                        ToastUtil.TS(getContext(),"更新失败");

                    }
                }
            });


        }else{
            ToastUtil.TS(getContext(),"输入内容不能为空");

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToSharePre(getContext(),profile_image);
    }
}
