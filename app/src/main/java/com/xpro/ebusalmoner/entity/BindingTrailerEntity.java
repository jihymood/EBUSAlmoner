package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/26.
 */
public class BindingTrailerEntity {
    private String id;
    private String driverName;
    private String driverTel;
    private String trailerCode;

    public String getTrailerCode() {
        return trailerCode;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }
}
