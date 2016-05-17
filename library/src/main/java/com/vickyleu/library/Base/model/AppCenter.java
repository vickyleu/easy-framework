package com.vickyleu.library.Base.model;

import android.app.Application;
import android.content.Context;

import com.vickyleu.library.Base.model.Img.BaseImageCenter;
import com.vickyleu.library.Model.DataBaseCenter;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public abstract class AppCenter extends Application implements DbCallBack {
    protected static AppCenter instant;
    private String name;
    private int version;

    protected abstract void initApp();

    protected abstract void initImgFromApp(BaseImageCenter center);

    protected abstract String setName();

    protected abstract int setVersion();

    BaseImageCenter center;

    @Override
    public void onCreate() {
        super.onCreate();
        instant = this;
        initApp();
        setNameAndVersion();
        loadImageCenter();
        loadDBCenter();
    }

    private void loadDBCenter() {
        if (name==null||name.equals("")||version==0){
            name="app.db";
            version=1;
        }
        initDB(name, version);
    }

    private void loadImageCenter() {
        center = setImgCenter();
        if (center==null){
            center=new NormalImgCenter(this.getApplicationContext(),true);
        }else {
            initImgFromApp(center);
        }
    }


    protected abstract BaseImageCenter setImgCenter();


    protected void setNameAndVersion() {
        name = setName();
        version = setVersion();
    }


    protected void initDB(String name, int version) {
        DataBaseCenter.init(this, name, version);
    }

    public static synchronized AppCenter getInstant() {
        return instant;
    }


    public DbCallBack getCallback() {
        return this;
    }

    class NormalImgCenter extends BaseImageCenter{

        public NormalImgCenter(Context context, boolean useNoraml) {
            super(context, useNoraml);
        }

        @Override
        protected ImageLoaderConfiguration getImageConfig() {
            return null;
        }

        @Override
        protected void CustomerOtherImgFramework() {

        }
    }

}
