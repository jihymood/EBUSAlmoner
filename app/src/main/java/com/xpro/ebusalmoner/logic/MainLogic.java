package com.xpro.ebusalmoner.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.xpro.ebusalmoner.baseapi.BaseLogic;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.constants.HttpUrls;
import com.xpro.ebusalmoner.entity.LoginMainEntity;
import com.xpro.ebusalmoner.entity.LoginParmsEntity;
import com.xpro.ebusalmoner.entity.LoginParse;
import com.xpro.ebusalmoner.utils.RequestParamUtils;

import org.xutils.http.RequestParams;


/**
 * Created by houyang on 2016/12/20.
 */
public class MainLogic extends BaseLogic {
    Context context;

    public MainLogic(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    /**
     * login,登陆接口
     */
    public void login(String phoneNum, String miei) {
        LoginParmsEntity loginParmsEntity = new LoginParmsEntity();
        loginParmsEntity.setSerial(miei.toUpperCase());
        loginParmsEntity.setSimInfo(phoneNum.toUpperCase());
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "login");
        params.setAsJsonContent(true);
        params.addBodyParameter(Constants.PARAMJSON, new Gson().toJson(loginParmsEntity));
        doRequest(params, "post", 0);
    }

    @Override
    public void doResponse(String result, int flag) {
        super.doResponse(result, flag);
        Message message = new Message();
        switch (flag) {
            case 0:
                LoginMainEntity loginMainEntity = LoginParse.loginMainParse(result);
                Log.e("loginMainEntity", "loginMainEntity解析成功:" + loginMainEntity.toString());
                Log.e("loginMainEntity", "result:"+result);
                //返回正常
                if (Constants.ERRORCODE_SUCCESS.equals(loginMainEntity.getErrorCode())) {
                    message.what = Constants.HANDLER_LOGIN_SUCCESS;
                    message.obj = loginMainEntity.getBody().getDriver();
                    handler.sendMessage(message);
                } else if (Constants.ERRORCODE_FAIL.equals(loginMainEntity.getErrorCode())) {
                    //返回失败
                    message.what = Constants.HANDLER_LOGIN_FAIL;
                    message.obj = loginMainEntity.getMsg();
                    handler.sendMessage(message);
                }
                break;
        }
    }
}
