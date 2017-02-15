package com.xpro.ebusalmoner.entity;

import java.io.File;

/**
 * Created by huangjh on 2017/2/13 0013 20:21
 */

public class ImageUploadEntity {
    private File file;
    private String type;
    private String functionName;
    private String persionId;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersionId() {
        return persionId;
    }

    public void setPersionId(String persionId) {
        this.persionId = persionId;
    }
}
