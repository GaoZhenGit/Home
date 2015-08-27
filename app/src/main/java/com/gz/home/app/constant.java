package com.gz.home.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by host on 2015/8/19.
 */
public interface Constant {
    class USER{
        public static final String DATA="user_data";
        public static final int MALE=1;
        public static final int FEMALE=0;
    }
    class ID{
        public static final String BMOBID="a8a2c2f172412c4400bbaee31f54851a";
    }
    class CODE{
        public static final int PICK_FROM_FILE=541;
        public static final int ACTION_CROP = 566;
    }
    class Count{
        public static final int UPLOAD_AVATAR_SIZE = 128;
    }
    class Path{
        public static final String SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator;
        //默认的文件夹名称
        public static final String DEFAULT_DIR_NAME = "Home";
        public static final String DIR_WITH_SEPARATE = DEFAULT_DIR_NAME + File.separator;
        public static final String DIR_WITHOUT_SEPARATE = DEFAULT_DIR_NAME;
        public static final String COMPLETE_PATH = SDCardRoot + DIR_WITH_SEPARATE;
    }
    class KeyValue{
        public static final String USER_MODIFI= "user_modifi";
        public static final String USER_NAME_MDF="user_name_mdf";
        public static final String USER_DETAIL_MDF="user_detail_mdf";
    }
}
