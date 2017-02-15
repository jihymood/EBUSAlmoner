package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/26.
 */
public class SignParamsEntity {
    private String code;
    private String taskId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "SignParamsEntity{" +
                "code='" + code + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
