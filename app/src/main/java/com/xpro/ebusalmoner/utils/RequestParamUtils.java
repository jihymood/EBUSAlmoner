package com.xpro.ebusalmoner.utils;

import org.xutils.http.RequestParams;

/**
 *
 * @ClassName: RequestParamUtils
 * @Description: xutils仙剑params对象工具
 * @author: houyang
 * @date: 2016年9月23日 上午10:38:22
 */
public class RequestParamUtils {
	public static RequestParams getRequestParams(String url) {
		RequestParams requestParams = new RequestParams(url);
		return requestParams;
	}
}
