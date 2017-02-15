package com.xpro.ebusalmoner.constants;

import android.os.Environment;

/**
 * @ClassName: Constants
 * @Description: handler定义常量
 * @author: houyang
 * @date: 2016年9月21日 上午9:31:13
 */
public class Constants {
    public static final String sys_pref = "pref_ibus";

    public static final int HANDLE_WHAT_NET_ERROR = 10001;

    public static final int notice = 999;

    //蓝牙信息
    public static final String BLUETOOTHINFO = null;

    /**
     * 管理人员待分配获取成功
     */
    public static final int MANAGER_WAIT_ALLOT_SUCCESS = 1000;
    /**
     * 管理人员待分配获取失败
     */
    public static final int MANAGER_WAIT_ALLOT_FAIL = 1001;
    /**
     * 管理人员已分配获取成功
     */
    public static final int MANAGER_COMPLETE_ALLOT_SUCCESS = 1002;
    /**
     * 管理人员已分配获取失败
     */
    public static final int MANAGER_COMPLETE_ALLOT_FAIL = 1003;
    /**
     * 管理人员-已完成获取成功
     */
    public static final int MANAGER_COMPLETE_SUCCESS = 1004;
    /**
     * 管理人员-已完成获取失败
     */
    public static final int MANAGER_COMPLETE_FAIL = 1005;
    /**
     * 管理人员拖车数据获取成功
     */
    public static final int GET_TRAILERS_SUCCESS = 1006;
    /**
     * 管理人员拖车数据获取失败
     */
    public static final int GET_TRAILERS_FAIL = 1007;
    /**
     * 实施人员-任务接口获取成功
     */
    public static final int GET_TASK_SUCCESS = 1008;
    /**
     * 实施人员-任务接口获取失败
     */
    public static final int GET_TASK_FAIL = 1009;
    /**
     * 个人资料成功
     */
    public static final int GET_PERSONAL_SUCCESS = 1010;
    /**
     * 个人资料失败
     */
    public static final int GET_PERSONAL_FAIL = 1011;
    /**
     * 检查更新成功
     */
    public static final int GET_NEWVERSION_SUCCESS = 1012;
    /**
     * 检查更新失败
     */
    public static final int GET_NEWVERSION_FAIL = 1013;
    /**
     * 签到接口返回成功
     */
    public static final int HANDLER_SIGN_SUCCESS = 1014;
    /**
     * 签到接口返回失败
     */
    public static final int HANDLER_SIGN_FAIL = 1015;

    //扫描二维码验证成功
    public static final int HANDLER_CODEANDCAR_SUCCESS = 1016;

    //扫描二维码验证失败
    public static final int HANDLER_CODEANDCAR_FAIL = 1017;

    //任务模块天气数据返回成功
    public static final int HANDLER_GETWEATHER_SUCCESS = 1018;

    //任务模块天气数据返回失败
    public static final int HANDLER_GETWEATHER_FAIL = 1019;

    //下拉刷新完成
    public static final int REFRESH_COMPLETE = 100002;

    //任务列表数据返回成功
    public static final int HANDLER_TODAY_TASK_SUCCESS = 1020;

    //任务列表数据返回失败
    public static final int HANDLER_TODAY_TASK_FAIL = 1021;

    //获得详细详情返回成功
    public static final int HANDLER_TASKINFO_SUCCESS = 1022;

    //获得详细详情返回失败
    public static final int HANDLER_TASKINFO_FAIL = 1023;

    //登陆接口返回成功
    public static final int HANDLER_LOGIN_SUCCESS = 1024;

    //登陆接口返回失败
    public static final int HANDLER_LOGIN_FAIL = 1025;

    //蓝牙验证成功
    public static final int HANDLER_BLUETOOTHANDCAR_SUCCESS = 1026;

    //蓝牙验证失败
    public static final int HANDLER_BLUETOOTHANDCAR_FAIL = 1027;

    //人和救济车绑定成功
    public static final int BOUNDTRAILER_SUCCESS = 1028;

    //人和救济车绑定失败
    public static final int BOUNDTRAILER_FAIL = 1029;

    //救济管理：故障公交车和拖车绑定成功
    public static final int BUS_BOUNDTRAILER_SUCCESS = 1030;

    //救济管理：故障公交车和拖车绑定失败
    public static final int BUS_BOUNDTRAILER_FAIL = 1031;

    //取消救济任务成功
    public static final int CANCELFAULTVEHICLE_SUCCESS = 1032;

    //取消救济任务失败
    public static final int CANCELFAULTVEHICLE_FAIL = 1033;

    //实施人员保存故障信息成功
    public static final int SAVERELIEFDETAIL_SUCCESS = 1034;

    //实施人员保存故障信息失败
    public static final int SAVERELIEFDETAIL_FAIL = 1035;

    //取证文件图片上传成功
    public static final int HANDLER_IMAGE_SUCCESS = 1036;

    //取证文件图片上传失败
    public static final int HANDLER_IMAGE_FAIL = 1037;

    //获取历史详情成功
    public static final int FAULTDETAIL_SUCCESS = 1038;

    //获取历史详情失败
    public static final int FAULTDETAIL_FAIL = 1039;











    /**
     * 公共JSON参数
     */
    public static final String PARAMJSON = "paramJson";
    /**
     * 接口返回成功码
     */

    public static final String ERRORCODE_SUCCESS = "200";
    /**
     * 接口返回失败码
     */
    public static final String ERRORCODE_FAIL = "-1";
    /**
     * 地图缩放等级
     */
    public static final int HOMEPAGEDISTANCE = 10009;

    //shiftdetail中获得地图缩放等级
    public static final int SHIFTDETAILDISTANCE = 10010;

    //隐藏拍照缓存路径
    public static final String SDCARDEBUSPHOTOGRAPH = Environment.getExternalStorageDirectory() + "/ebus/photograph";
}
