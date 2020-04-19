package com.zengze.demo_for_evaluate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Detail {
    private String id;
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
   // private String timestamp;

    public String printAllPara(){
        String prout="id = " + this.getId() + " , a_pkt_len_var = " + this.getA_pkt_len_var()
                + " , a_atv_avg = " + this.getA_atv_avg() + " , a_fw_iat_avg = " + this.getA_fw_iat_avg()
                + " , a_bw_iat_avg = " + this.getA_bw_iat_avg() + " , n_pkt_len_avg = " + this.getN_pkt_len_avg()
                + " , n_pkt_size_avg = " + this.getN_pkt_size_avg() + " , n_fl_byt_s = " + this.getN_fl_byt_s()
                + " , n_fl_pkt_s = " + this.getN_fl_pkt_s() + " , n_p_of_ab_fl = " + this.getN_p_of_ab_fl()
                + " , f_security_equipment_num = " + this.getF_security_equipment_num() + " , f_service_quantity = " + this.getF_service_quantity()
                + " , f_os_quantity = " + this.getF_os_quantity() + " , d_alert_num = " + this.getD_alert_num()
                + " , d_bw_usage_frequency = " + this.getD_bw_usage_frequency() + " , d_security_incidents_frequency = " + this.getD_security_incidents_frequency()
                + " , d_attack_ddos = " + this.getD_attack_ddos() + " , d_attack_ps = " + this.getD_attack_ps()
                + " , d_attack_vc = " + this.getD_attack_vc() + " , d_attack_ua = " + this.getD_attack_ua();                //+ " , timestamp = " + this.getTimestamp();
        return(prout);
    }

    public String[] getData(){
        String[] array={this.getId(),this.getA_pkt_len_var(),this.getA_atv_avg(),this.getA_fw_iat_avg(),this.getA_bw_iat_avg(),
                this.getN_pkt_len_avg(),this.getN_pkt_size_avg(),this.getN_fl_byt_s(),this.getN_fl_pkt_s(),this.getN_p_of_ab_fl(),
                this.getF_security_equipment_num(),this.getF_service_quantity(),this.getF_os_quantity(),
                this.getD_alert_num(),this.getD_bw_usage_frequency(),this.getD_security_incidents_frequency(),
                this.getD_attack_ddos(),this.getD_attack_ps(),this.getD_attack_vc(),this.getD_attack_ua()/*,this.getTimestamp()*/};
        return(array);
    }
}
