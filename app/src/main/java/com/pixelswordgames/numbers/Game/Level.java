package com.pixelswordgames.numbers.Game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Level {

    @SerializedName("lvl")
    @Expose
    private Integer lvl;
    @SerializedName("types")
    @Expose
    private String types;
    @SerializedName("patterns")
    @Expose
    private List<String> patterns;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("valuesTopLimit")
    @Expose
    private Double valuesTopLimit;
    @SerializedName("valuesBottomLimit")
    @Expose
    private Double valuesBottomLimit;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("failedTime")
    @Expose
    private Integer failedTime;
    @SerializedName("successTime")
    @Expose
    private Integer successTime;

    public Level() {
    }

    public Integer getLvl() {
        return lvl;
    }

    public String getTypes() {
        return types;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public Double getValuesTopLimit() {
        return valuesTopLimit;
    }

    public Double getValuesBottomLimit() {
        return valuesBottomLimit;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getFailedTime() {
        return failedTime;
    }

    public Integer getSuccessTime() {
        return successTime;
    }
}