package com.gz.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.ActivityManagerUtils;
import com.gz.home.utils.BitmapUtils;
import com.gz.home.utils.ImageUtils;
import com.gz.home.utils.LogUtil;
import com.gz.home.utils.NetworkUtil;
import com.gz.home.utils.UpdataSubject;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

public class UserModifyActivity extends BasePageActivity {
    private User user;
    private AQuery aq;
    Uri imageCaptureUri;
    Bitmap newAvatar;

    @Override
    protected void initData() {
        this.user=(User)mBundle.getSerializable(Constant.USER.DATA);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_user_modify);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("个人信息");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("返回");
        aq.id(R.id.mdf_name).text(user.getName());
        aq.id(R.id.mdf_avater).image(user.getAvatar());
        aq.id(R.id.mdf_phone).text(user.getUsername());
        aq.id(R.id.mdf_address).text(user.getAddress());
        aq.id(R.id.mdf_detail).text(user.getDetail());
        if(user.getSex()==Constant.USER.MALE){
            aq.id(R.id.mdf_sex).text("男");
        }else {
            aq.id(R.id.mdf_sex).text("女");
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "onBackPressed");
        aq.id(R.id.btn_mdf_avater).clicked(this, "pickPhoto");
        aq.id(R.id.btn_mdf_name).clicked(this, "aq_mdf_name");
        aq.id(R.id.btn_mdf_detail).clicked(this, "aq_mdf_detail");
        aq.id(R.id.btn_mdf_logoff).clicked(this, "aq_logoff");
    }

    //调用系统相册
    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, Constant.CODE.PICK_FROM_FILE);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        imageCaptureUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", Constant.Count.UPLOAD_AVATAR_SIZE);
        intent.putExtra("outputY", Constant.Count.UPLOAD_AVATAR_SIZE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, Constant.CODE.ACTION_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case Constant.CODE.PICK_FROM_FILE:
                startPhotoZoom(data.getData());
                break;
            case Constant.CODE.ACTION_CROP:
                newAvatar = BitmapUtils.getBitmapFromUri(mContext, imageCaptureUri);
                upLoadAvater();
                break;
            case Constant.CODE.MDF_NAME:
                aq.id(R.id.mdf_name).text(data.getStringExtra(Constant.KeyValue.USER_MODIFI));
                user.setName(data.getStringExtra(Constant.KeyValue.USER_MODIFI));
                user.setMdfName(true);
                NetworkUtil.updateUser(this, user, new NetworkUtil.UserListener() {
                    @Override
                    public void onSuccess(User user) {
                        ShowToast("名字修改成功");
//                        UpdataSubject.getInstance().callUpdata(user);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ShowToast("名字修改失败");
                    }
                });
                break;
            case Constant.CODE.MDF_DETAIL:
                aq.id(R.id.mdf_detail).text(data.getStringExtra(Constant.KeyValue.USER_MODIFI));
                user.setDetail(data.getStringExtra(Constant.KeyValue.USER_MODIFI));
                NetworkUtil.updateUser(this, user, new NetworkUtil.UserListener() {
                    @Override
                    public void onSuccess(User user) {
                        ShowToast("个性签名修改成功");
//                        UpdataSubject.getInstance().callUpdata(user);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ShowToast("个性签名修改失败"+s);
                    }
                });
                break;
        }
    }

    private void upLoadAvater() {
        if (newAvatar != null) {
            String fileName = System.currentTimeMillis() + "tempAvatar.jpg";
            ImageUtils.saveBitmap(newAvatar, fileName);
            String path = Constant.Path.COMPLETE_PATH + fileName;
            final File file = new File(path);
            LogUtil.i("保存裁剪头像后--->path = " + path);
            LogUtil.i("保存裁剪头像后--->file.exists() = " + file.exists());
            NetworkUtil.upload(mContext, file.getPath(), new NetworkUtil.FileUploadListener() {
                @Override
                public void onError(int i, String s) {
                    ShowToast("头像上传失败"+s);
                    if(!newAvatar.isRecycled()){
                        newAvatar.recycle();
                        LogUtil.i("--tag--","avatar recycle");
                    }
                    file.delete();
                }

                @Override
                public void onSuccess(String s, String s1, String fileUrl) {
                    user.setAvatar(fileUrl);
                    aq.id(R.id.mdf_avater).image(user.getAvatar());
                    NetworkUtil.updateUser(mContext, user, new NetworkUtil.UserListener() {
                        @Override
                        public void onSuccess(User user) {
                            ShowToast("头像上传成功");
//                            UpdataSubject.getInstance().callUpdata(user);
                            if(!newAvatar.isRecycled()){
                                newAvatar.recycle();
                                LogUtil.i("--tag--", "avatar recycle");
                            }
                            file.delete();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ShowToast("头像上传失败"+s);
                            if(!newAvatar.isRecycled()){
                                newAvatar.recycle();
                                LogUtil.i("--tag--", "avatar recycle");
                            }
                            file.delete();
                        }
                    });
                }

                @Override
                public void onProgress(int i) {

                }
            });
        }
    }

    //修改名字页面
    public void aq_mdf_name(){
        if (user.isMdfName()){
            return;
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.USER.DATA, this.user);
        bundle.putString(Constant.USER.MDF_TYPE,Constant.KeyValue.USER_NAME_MDF);
        Intent intent=new Intent(UserModifyActivity.this,EditActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.CODE.MDF_NAME);
    }

    //修改个性签名页面
    public void aq_mdf_detail(){
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.USER.DATA, this.user);
        bundle.putString(Constant.USER.MDF_TYPE,Constant.KeyValue.USER_DETAIL_MDF);
        Intent intent=new Intent(UserModifyActivity.this,EditActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.CODE.MDF_DETAIL);
    }

    //退出登录
    public void aq_logoff(){
        BmobUser.logOut(this);
        User.clearFromDb(this);
        ActivityManagerUtils.getInstance().removeAllActivity();
        startActivity(new Intent(UserModifyActivity.this, LoginActivity.class));
        ShowToast("退出登录成功");
//        finish();
    }

}
