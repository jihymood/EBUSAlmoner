package com.xpro.ebusalmoner.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xpro.ebusalmoner.baseapi.BaseLogic;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.constants.HttpUrls;
import com.xpro.ebusalmoner.entity.BindingTrailerEntity;
import com.xpro.ebusalmoner.entity.BoundTrailerEntity;
import com.xpro.ebusalmoner.entity.BoundTrailerRoot_M;
import com.xpro.ebusalmoner.entity.BreakdownInfoRoot_M;
import com.xpro.ebusalmoner.entity.BreakdownRoot_M;
import com.xpro.ebusalmoner.entity.BusDeviceParamsEntity;
import com.xpro.ebusalmoner.entity.CancelVehicleEntity;
import com.xpro.ebusalmoner.entity.CodeAndCarParse;
import com.xpro.ebusalmoner.entity.FaultDetailEntity;
import com.xpro.ebusalmoner.entity.FaultInformationEntity;
import com.xpro.ebusalmoner.entity.FaultInformationEntity2;
import com.xpro.ebusalmoner.entity.GetTaskEntity;
import com.xpro.ebusalmoner.entity.GetTrailersEntity;
import com.xpro.ebusalmoner.entity.HistoryInfoRoot;
import com.xpro.ebusalmoner.entity.HistoryManagerEntity;
import com.xpro.ebusalmoner.entity.HistoryRoot_M;
import com.xpro.ebusalmoner.entity.MainEntity;
import com.xpro.ebusalmoner.entity.NewVersionRoot;
import com.xpro.ebusalmoner.entity.PersonalRoot;
import com.xpro.ebusalmoner.entity.SaveReliefDetailEntity;
import com.xpro.ebusalmoner.entity.SignParamsEntity;
import com.xpro.ebusalmoner.entity.TempletBean;
import com.xpro.ebusalmoner.entity.TrailerTaskRoot_S;
import com.xpro.ebusalmoner.entity.UpLoadImageMainEntity;
import com.xpro.ebusalmoner.entity.WeatherMainEntity;
import com.xpro.ebusalmoner.entity.WeatherMainParse;
import com.xpro.ebusalmoner.utils.RequestParamUtils;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskLogic extends BaseLogic {

    private Context context;
    private Gson gson;
    private String jsonStr;
    private TempletBean templetBean;

    public TaskLogic(Handler handler, Context context) {
        // TODO Auto-generated constructor stub
        super(handler);
        this.context = context;
        gson = new Gson();
        templetBean = new TempletBean();
    }

    /**
     * 管理人员-待分配
     */
    public void faultInformation() {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "faultInformation");//接口名

        FaultInformationEntity faultInformationEntity = new FaultInformationEntity();
        faultInformationEntity.setState("0");
        jsonStr = gson.toJson(faultInformationEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 0);
    }

    /**
     * 已分配
     * doRequest(params, "get", 1);
     */
    public void faultInformation2() {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "faultInformation");//接口名（暂时用待分配借口）

        FaultInformationEntity2 faultInformation2 = new FaultInformationEntity2();
        faultInformation2.setState("1");
        jsonStr = gson.toJson(faultInformation2);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 1);
    }

    /**
     * 管理人员-已完成
     */
    public void hasCompleteTask(HistoryManagerEntity ManagerEntity) {
//    public void hasCompleteTask(String date) {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "faultInformation");//接口名

        jsonStr = gson.toJson(ManagerEntity);
        params.addBodyParameter("paramJson", jsonStr);

        doRequest(params, "get", 2);
    }

    /**
     * 实施人员-历史
     */
    public void hasCompleteTask1(String date) {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "faultInformation");//接口名

        templetBean.setId("0");
        templetBean.setDate(date);
        jsonStr = gson.toJson(templetBean);
        params.addBodyParameter("paramJson", jsonStr);

        doRequest(params, "get", 16);
    }

    /**
     * 管理人员待分配-拖车接口
     *
     * @return
     */
    public void getTrailers() {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "trailerInformation");//接口名

        GetTrailersEntity getTrailersEntity = new GetTrailersEntity();
        getTrailersEntity.setId("0");
        jsonStr = gson.toJson(getTrailersEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 3);
    }

    /**
     * 实施人员-任务接口
     */
    public void getTask(String trailerCode) {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "task");//接口名

        GetTaskEntity getTaskEntity = new GetTaskEntity();
        getTaskEntity.setTrailerCode(trailerCode);
        jsonStr = gson.toJson(getTaskEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 4);
    }


    /**
     * 我的模块
     */
    public void getPersonal() {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "basicData");
//        params.addBodyParameter("userId", "1");
        templetBean.setId("1");
        jsonStr = gson.toJson(templetBean);
        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 5);
    }

    /**
     * 检查更新
     */
    public void getNewVersion() {
        RequestParams params = new RequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "versionUpdate");
//        params.addBodyParameter("version", "1");
        templetBean.setId("1");
        jsonStr = gson.toJson(templetBean);
        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 6);
    }

    /**
     * 对比二维码验证，codeAndCar
     */
    public void codeAndCar(String info, String busCode, String type) {
        BusDeviceParamsEntity busDeviceParamsEntity = new BusDeviceParamsEntity();
        busDeviceParamsEntity.setBusCode(busCode);
        busDeviceParamsEntity.setIdCode(info);
        busDeviceParamsEntity.setType(type);
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "busDevice");
        params.addBodyParameter(Constants.PARAMJSON, new Gson().toJson(busDeviceParamsEntity));
        doRequest(params, "post", 7);
    }

    /**
     * 对比蓝牙验证
     */
    public void bluetoothAndCar(String info, String busCode, String type) {
        BusDeviceParamsEntity busDeviceParamsEntity = new BusDeviceParamsEntity();
        busDeviceParamsEntity.setBusCode(busCode);
        busDeviceParamsEntity.setIdCode(info);
        busDeviceParamsEntity.setType(type);
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "busDevice");
        params.addBodyParameter(Constants.PARAMJSON, new Gson().toJson(busDeviceParamsEntity));
        doRequest(params, "post", 10);
    }

    /**
     * 签到接口
     */
    public void sign(String userId, String taskId) {
        SignParamsEntity signParamsEntity = new SignParamsEntity();
        signParamsEntity.setCode(userId);
        signParamsEntity.setTaskId(taskId);
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "sign");
        params.addBodyParameter(Constants.PARAMJSON, new Gson().toJson(signParamsEntity));
        doRequest(params, "post", 8);
    }

    /**
     * 获取天气
     */
    public void getWeather() {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.WEATHER);
        params.addHeader("apikey", "9a295b0853265e82d6b96e9008ec18b5");
        params.addBodyParameter("city", "南京");
        doRequest(params, "get", 9);
    }


    public void getWeatherChina() {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.WEATHER_CHINA);
        doRequest(params, "get", 9);
    }

    /**
     * 救济实施人和车绑定拖车
     */
    public void boundTrailer(String driverId, String driverName, String driverTel, String trailerId) {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "boundTrailer");
        BoundTrailerEntity boundTrailerEntity = new BoundTrailerEntity();
        boundTrailerEntity.setDriverId(driverId);
        boundTrailerEntity.setDriverTel(driverTel);
        boundTrailerEntity.setDriverName(driverName);
        boundTrailerEntity.setTrailerCode(trailerId);

        jsonStr = gson.toJson(boundTrailerEntity);
        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 11);
    }

    /**
     * 管理 故障公交车和拖车绑定
     */
    public void bindingTrailer(String id, String trailerCode) {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "bindingTrailer");

        BindingTrailerEntity bindingTrailerEntity = new BindingTrailerEntity();
        bindingTrailerEntity.setId(id);
        bindingTrailerEntity.setTrailerCode(trailerCode);
        jsonStr = gson.toJson(bindingTrailerEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 12);
    }

    /**
     * 实施人员 已分配-->取消接口
     */
    public void cancelFaultVehicle(String id, String trailerCode) {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "cancelFaultVehicle");

        CancelVehicleEntity vehicleEntity = new CancelVehicleEntity();
        vehicleEntity.setId(id);
        vehicleEntity.setTrailerId(trailerCode);
        jsonStr = gson.toJson(vehicleEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 13);
    }

    /**
     * 保存故障信息接口(实施人员)
     */
    public void saveReliefDetail(String faultId, String reason, String state, String photos, String personId, String
            driverId) {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "saveReliefDetail");

        SaveReliefDetailEntity saveReliefDetailEntity = new SaveReliefDetailEntity();
        saveReliefDetailEntity.setFaultId(faultId); //对应任务接口中的id
        saveReliefDetailEntity.setReason(reason);
        saveReliefDetailEntity.setState(state);
        saveReliefDetailEntity.setPhotos(photos);
        saveReliefDetailEntity.setCreateBy(personId);//对应登陆接口里的personId
        saveReliefDetailEntity.setDriverId(driverId); //对应任务接口中的personId
        jsonStr = gson.toJson(saveReliefDetailEntity);

        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "get", 14);
    }

    /**
     * 图片上传
     * type:文件类型 1:图片
     * path:文件路径
     * login_name：人员工号
     * functionName文件上传的位置（类型），在例保项中上传为checkItem
     */
    public void uploadImage(File file, String type, String functionName, String userId) {

        Log.e("file", "file" + file);
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.imageUrl(context));

//        ImageUploadEntity imageUploadEntity = new ImageUploadEntity();
//        imageUploadEntity.setFile(file);
//        imageUploadEntity.setType(type);
//        imageUploadEntity.setFunctionName(functionName);
//        imageUploadEntity.setPersionId(userId);

        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("file", file));
        list.add(new KeyValue("persionId", userId));
        list.add(new KeyValue("functionName", functionName));
        list.add(new KeyValue("type", type));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);

//        jsonStr = gson.toJson(imageUploadEntity);
//        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "post", 15);
    }

    /**
     * 历史详情
     */
    public void faultDetail(String faultId) {
        RequestParams params = RequestParamUtils.getRequestParams(HttpUrls.baseUrl(context));
        params.addBodyParameter("messageType", "faultDetail");

        FaultDetailEntity faultDetailEntity = new FaultDetailEntity();
        faultDetailEntity.setFaultId(faultId);

        jsonStr = gson.toJson(faultDetailEntity);
        params.addBodyParameter("paramJson", jsonStr);
        doRequest(params, "post", 17);
    }



    @Override
    public void doResponse(String result, int flag) {
        // TODO Auto-generated method stub
        super.doResponse(result, flag);
        Message msg = new Message();
        switch (flag) {
            case 0:
                Log.e("BreakdownRoot_M", "result:" + result);
                BreakdownRoot_M root0 = JSON.parseObject(result, BreakdownRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root0.getErrorCode())) {
                    //获得待分配数据成功
                    msg.what = Constants.MANAGER_WAIT_ALLOT_SUCCESS;
                    msg.obj = root0.getBody().getData();
                    handler.sendMessage(msg);
                } else {
                    //获得待分配数据失败
                    msg.what = Constants.MANAGER_WAIT_ALLOT_FAIL;
                    msg.obj = root0.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            case 1:
                BreakdownRoot_M root1 = JSON.parseObject(result, BreakdownRoot_M.class);
                Log.e("BreakdownRoot_MM", result);
                if (Constants.ERRORCODE_SUCCESS.equals(root1.getErrorCode())) {
                    //获得已分配数据成功
                    msg.what = Constants.MANAGER_COMPLETE_ALLOT_SUCCESS;
                    msg.obj = root1.getBody().getData();
                    handler.sendMessage(msg);
                } else {
                    //获得已分配数据失败
                    msg.what = Constants.MANAGER_COMPLETE_ALLOT_FAIL;
                    msg.obj = root1.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            case 2:
                Log.e("HistoryRoot_M", result);
                HistoryRoot_M root2 = JSON.parseObject(result, HistoryRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root2.getErrorCode())) {
                    msg.what = Constants.MANAGER_COMPLETE_SUCCESS;
                    msg.obj = root2.getBody().getData();
                    handler.sendMessage(msg);
                    break;
                } else {
                    //获得待分配数据失败
                    msg.what = Constants.MANAGER_COMPLETE_FAIL;
                    msg.obj = root2.getMsg();
                    handler.sendMessage(msg);
                }

                break;
            case 3:
                Log.e("BreakdownRoot", result);
                BreakdownInfoRoot_M root3 = JSON.parseObject(result, BreakdownInfoRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root3.getErrorCode())) {
                    msg.what = Constants.GET_TRAILERS_SUCCESS;
                    msg.obj = root3.getBody().getList();
                    handler.sendMessage(msg);
                } else {
                    msg.what = Constants.GET_TRAILERS_FAIL;
                    msg.obj = root3.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            case 4:
                Log.e("TrailerTaskRoot_S", result);
                TrailerTaskRoot_S root4 = JSON.parseObject(result, TrailerTaskRoot_S.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root4.getErrorCode())) {
                    msg.what = Constants.GET_TASK_SUCCESS;
                    msg.obj = root4.getBody().getData();
                    handler.sendMessage(msg);
                } else {
                    msg.what = Constants.GET_TASK_FAIL;
                    msg.obj = root4.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 5:
                PersonalRoot root5 = JSON.parseObject(result, PersonalRoot.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root5.getErrorCode())) {
                    msg.what = Constants.GET_PERSONAL_SUCCESS;
                    msg.obj = root5.getBody();
                    handler.sendMessage(msg);
                } else {
                    msg.what = Constants.GET_PERSONAL_FAIL;
                    msg.obj = root5.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            case 6:
                NewVersionRoot root6 = JSON.parseObject(result, NewVersionRoot.class);
                if (Constants.ERRORCODE_SUCCESS.equals(root6.getErrorCode())) {
                    msg.what = Constants.GET_NEWVERSION_SUCCESS;
                    msg.obj = root6.getBody();
                    handler.sendMessage(msg);
                } else {
                    msg.what = Constants.GET_NEWVERSION_FAIL;
                    msg.obj = root6.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            //对比二维码验证接口
            case 7:
                MainEntity mainEntity = CodeAndCarParse.mainParse(result);
                Log.e("mainEntity", "二维码解析成功：" + result);
                if (Constants.ERRORCODE_SUCCESS.equals(mainEntity.getErrorCode())) {
                    //返回成功
                    msg.what = Constants.HANDLER_CODEANDCAR_SUCCESS;
                    msg.obj = mainEntity.getMsg();
                    handler.sendMessage(msg);
                } else if (Constants.ERRORCODE_FAIL.equals(mainEntity.getErrorCode())) {
                    //返回失败
                    msg.what = Constants.HANDLER_CODEANDCAR_FAIL;
                    msg.obj = mainEntity.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            //对比蓝牙验证接口
            case 10:
                MainEntity mainEntity1 = CodeAndCarParse.mainParse(result);
                Log.e("mainEntity", "蓝牙解析成功：" + result);
                if (Constants.ERRORCODE_SUCCESS.equals(mainEntity1.getErrorCode())) {
                    //返回成功
                    msg.what = Constants.HANDLER_BLUETOOTHANDCAR_SUCCESS;
                    msg.obj = mainEntity1.getMsg();
                    handler.sendMessage(msg);
                } else if (Constants.ERRORCODE_FAIL.equals(mainEntity1.getErrorCode())) {
                    //返回失败
                    msg.what = Constants.HANDLER_CODEANDCAR_FAIL;
                    msg.obj = mainEntity1.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            //签到接口
            case 8:
                MainEntity mainEntitySign = CodeAndCarParse.mainParse(result);
                Log.e("mainEntity", "mainEntity解析成功：" + mainEntitySign.toString());
                if (Constants.ERRORCODE_SUCCESS.equals(mainEntitySign.getErrorCode())) {
                    //签到返回成功
                    msg.what = Constants.HANDLER_SIGN_SUCCESS;
                    msg.obj = mainEntitySign.getMsg();
                    handler.sendMessage(msg);
                } else {
                    //签到返回失败
                    msg.what = Constants.HANDLER_SIGN_FAIL;
                    msg.obj = mainEntitySign.getMsg();
                    handler.sendMessage(msg);
                }
                break;
            case 9:
                WeatherMainEntity weatherMainEntity = WeatherMainParse.weatherMainParse(result);
                if (!"".equals(weatherMainEntity.getWeatherinfo().getWeather())) {
                    //天气返回成功
                    msg.what = Constants.HANDLER_GETWEATHER_SUCCESS;
                    msg.obj = weatherMainEntity.getWeatherinfo().getWeather();
                    handler.sendMessage(msg);
                } else {
                    //天气返回失败
                    msg.what = Constants.HANDLER_GETWEATHER_FAIL;
                    msg.obj = "网络不稳定,天气获取失败,请稍后再试";
                    handler.sendMessage(msg);
                }
                break;
            case 11:
                Log.e("mainEntity", "result:" + result);
                BoundTrailerRoot_M boundTrailerRoot = JSON.parseObject(result, BoundTrailerRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(boundTrailerRoot.getErrorCode())) {
                    //救济实施：人和拖车绑定成功
                    msg.what = Constants.BOUNDTRAILER_SUCCESS;
                    msg.obj = boundTrailerRoot.getBody();
                    handler.sendMessage(msg);
                } else {
                    //救济实施：人和拖车绑定失败
                    msg.what = Constants.BOUNDTRAILER_FAIL;
                    msg.obj = boundTrailerRoot.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 12:
                Log.e("bindingTrailer", "result:" + result);
                BoundTrailerRoot_M boundTrailerRoot1 = JSON.parseObject(result, BoundTrailerRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(boundTrailerRoot1.getErrorCode())) {
                    //救济管理：故障公交车和拖车绑定成功
                    msg.what = Constants.BUS_BOUNDTRAILER_SUCCESS;
                    msg.obj = boundTrailerRoot1.getMsg();
                    handler.sendMessage(msg);
                } else {
                    //救济管理：故障公交车和拖车绑定失败
                    msg.what = Constants.BUS_BOUNDTRAILER_FAIL;
                    msg.obj = boundTrailerRoot1.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 13:
                Log.e("cancelFaultVehicle", "result:" + result);
                BoundTrailerRoot_M boundTrailerRoot2 = JSON.parseObject(result, BoundTrailerRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(boundTrailerRoot2.getErrorCode())) {
                    //救济管理：取消救济任务成功
                    msg.what = Constants.CANCELFAULTVEHICLE_SUCCESS;
                    msg.obj = boundTrailerRoot2.getMsg();
                    handler.sendMessage(msg);
                } else {
                    //救济管理：取消救济任务失败
                    msg.what = Constants.CANCELFAULTVEHICLE_FAIL;
                    msg.obj = boundTrailerRoot2.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 14:
                Log.e("saveReliefDetail", "result:" + result);
                BoundTrailerRoot_M boundTrailerRoot3 = JSON.parseObject(result, BoundTrailerRoot_M.class);
                if (Constants.ERRORCODE_SUCCESS.equals(boundTrailerRoot3.getErrorCode())) {
                    //实施人员保存故障信息成功
                    msg.what = Constants.SAVERELIEFDETAIL_SUCCESS;
                    msg.obj = boundTrailerRoot3.getMsg();
                    handler.sendMessage(msg);
                } else {
                    //实施人员保存故障信息失败
                    msg.what = Constants.SAVERELIEFDETAIL_FAIL;
                    msg.obj = boundTrailerRoot3.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 15:
                Log.e("upLoadImageMainEntity", "result:"+result);
                UpLoadImageMainEntity upLoadImageMainEntity = JSON.parseObject(result, UpLoadImageMainEntity.class);
                Log.e("upLoadImageMainEntity", "upLoadImageMainEntity：" + upLoadImageMainEntity.toString());
                //图片上传成功
                if (Constants.ERRORCODE_SUCCESS.equals(upLoadImageMainEntity.getResult())) {
                    msg.what = Constants.HANDLER_IMAGE_SUCCESS;
                    msg.obj = upLoadImageMainEntity.getPath();
                    handler.sendMessage(msg);
                } else if ("400".equals(upLoadImageMainEntity.getResult())) {
                    //上传失败
                    msg.what = Constants.HANDLER_IMAGE_FAIL;
                    msg.obj = upLoadImageMainEntity.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            case 17:
                Log.e("HistoryInfo", "result:"+result);
                HistoryInfoRoot historyInfoRoot = JSON.parseObject(result, HistoryInfoRoot.class);
                //获取历史详情成功
                if (Constants.ERRORCODE_SUCCESS.equals(historyInfoRoot.getErrorCode())) {
                    msg.what=Constants.FAULTDETAIL_SUCCESS;
                    msg.obj=historyInfoRoot.getBody().getData();
                    handler.sendMessage(msg);
                }else{
                    //获取历史详情失败
                    msg.what=Constants.FAULTDETAIL_FAIL;
                    msg.obj=historyInfoRoot.getMsg();
                    handler.sendMessage(msg);
                }
                break;

            default:
                break;
        }

    }


}
