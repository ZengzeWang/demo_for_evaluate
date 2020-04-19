package com.zengze.demo_for_evaluate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "item_score")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Item_score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String a_pkt_len_var;
    private String a_atv_avg;
    private String a_fw_iat_avg;
    private String a_bw_iat_avg;
    private String n_pkt_len_avg;
    private String n_pkt_size_avg;
    private String n_fl_byt_s;
    private String n_fl_pkt_s;
    private String n_p_of_ab_fl;
    private String f_security_equipment_num;
    private String f_service_quantity;
    private String f_os_quantity;
    private String d_alert_num;
    private String d_bw_usage_frequency;
    private String d_security_incidents_frequency;
    private String d_attack_ddos;
    private String d_attack_ps;
    private String d_attack_vc;
    private String d_attack_ua;
    //private String timestamp;

    public void setData(String [] data,String t){
        this.setA_pkt_len_var(data[0]);
        this.setA_atv_avg(data[1]);
        this.setA_fw_iat_avg(data[2]);
        this.setA_bw_iat_avg(data[3]);
        this.setN_pkt_len_avg(data[4]);
        this.setN_pkt_size_avg(data[5]);
        this.setN_fl_byt_s(data[6]);
        this.setN_fl_pkt_s(data[7]);
        this.setN_p_of_ab_fl(data[8]);
        this.setF_security_equipment_num(data[9]);
        this.setF_service_quantity(data[10]);
        this.setF_os_quantity(data[11]);
        this.setD_alert_num(data[12]);
        this.setD_bw_usage_frequency(data[13]);
        this.setD_security_incidents_frequency(data[14]);
        this.setD_attack_ddos(data[15]);
        this.setD_attack_ps(data[16]);
        this.setD_attack_vc(data[17]);
        this.setD_attack_ua(data[18]);
        //this.setTimestamp(t);
    }

}
