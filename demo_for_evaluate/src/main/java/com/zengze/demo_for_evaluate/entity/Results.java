package com.zengze.demo_for_evaluate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Results {
    private double fragile_index;
    private double dangerous_index;
    private double asset_index;
    private double network_index;
    private double comprehensive_score;
    private String record_time;

    public Results setNewItem(double[] data){
        this.fragile_index=data[1];
        this.dangerous_index=data[2];
        this.asset_index=data[3];
        this.network_index=data[4];
        this.comprehensive_score=data[5];
        return this;
    }



}
