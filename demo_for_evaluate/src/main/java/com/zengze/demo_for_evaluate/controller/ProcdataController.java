package com.zengze.demo_for_evaluate.controller;

import com.zengze.demo_for_evaluate.entity.Detail;
import com.zengze.demo_for_evaluate.entity.Index_weight;
import com.zengze.demo_for_evaluate.entity.Item_score;
import com.zengze.demo_for_evaluate.entity.Score;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.lang.*;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ProcdataController implements ApplicationRunner{

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private com.zengze.demo_for_evaluate.myinterface.Index_weightResitory Index_weightResitory;
    @Autowired
    private com.zengze.demo_for_evaluate.myinterface.Item_scoreResitory Item_scoreResitory;

    //项目启动后执行的方法
    @Override
    public void run(ApplicationArguments args) throws Exception {
        startProcessDataService();//
        System.out.println("Process Data over");
    }

    //定时器
    public void startProcessDataService() {
        System.out.println("====数据处理进程 启动！====");
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        System.out.println(time);
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                String sql = "select * from detail_info ";

                List<Detail> dataList = (List<Detail>) jdbcTemplate.query(sql, new RowMapper<Detail>() {

                    @Override
                    public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Detail data = new Detail();

                        data.setId(rs.getString("id"));
                        data.setA_pkt_len_var(rs.getString("a_pkt_len_var"));
                        data.setA_atv_avg(rs.getString("a_atv_avg"));
                        data.setA_fw_iat_avg(rs.getString("a_fw_iat_avg"));
                        data.setA_bw_iat_avg(rs.getString("a_bw_iat_avg"));
                        data.setN_pkt_len_avg(rs.getString("n_pkt_len_avg"));
                        data.setN_pkt_size_avg(rs.getString("n_pkt_size_avg"));
                        data.setN_fl_byt_s(rs.getString("n_fl_byt_s"));
                        data.setN_fl_pkt_s(rs.getString("n_fl_pkt_s"));
                        data.setN_p_of_ab_fl(rs.getString("n_p_of_ab_fl"));
                        data.setF_security_equipment_num(rs.getString("f_security_equipment_num"));
                        data.setF_service_quantity(rs.getString("f_service_quantity"));
                        data.setF_os_quantity(rs.getString("f_os_quantity"));
                        data.setD_alert_num(rs.getString("d_alert_num"));
                        data.setD_bw_usage_frequency(rs.getString("d_bw_usage_frequency"));
                        data.setD_security_incidents_frequency(rs.getString("d_security_incidents_frequency"));
                        data.setD_attack_ddos(rs.getString("d_attack_ddos"));
                        data.setD_attack_ps(rs.getString("d_attack_ps"));
                        data.setD_attack_vc(rs.getString("d_attack_vc"));
                        data.setD_attack_ua(rs.getString("d_attack_ua"));
                      //  data.setTimestamp(rs.getString("timestamp"));

                        return data;
                    }
                });

                //构造存储变量
                int data_num=dataList.size();
                double [][] para= new double[19][data_num];
                double [][] cacuData=new double[19][6];//大小（归一化）均方

                //重新填充数据
                int index= 0;
                for (Detail data : dataList) {
                    String [] data_tmp=data.getData();
                    for (int i = 0; i < 19; i++){
                        para[i][index]=Double.valueOf(data_tmp[i+1].toString());
                    }
                    index++;

                    String str = StringUtils.join(data.getData(), ","); // 数组转字符串(逗号分隔)(推荐)
                    System.out.println(str);
                }

                //计算最大最小值
                for(int j = 0; j < 19; j++){
                    cacuData[j][0]=getMax(para[j]);
                    cacuData[j][1]=getMin(para[j]);
                }

                //归一化处理
                for (int i = 0; i < 19; i++){
                    for(int j = 0; j < data_num; j++){
                        if((cacuData[i][0]-cacuData[i][1])==0){
                            para[i][j]=0;
                        }else{
                            para[i][j]=(para[i][j]-cacuData[i][1])/(cacuData[i][0]-cacuData[i][1]);
                        }
                        if(i==0||i==1||i==2||i==3||i==4||i==5||i==8||i==12||i==13||i==14||i==15||i==16||i==17||i==18){
                            para[i][j]=1-para[i][j];//正向化
                        }
                    }
                }

                //计算均值方差
                for(int j = 0; j < 19; j++){
                    cacuData[j][2]=getMax(para[j]);
                    cacuData[j][3]=getMin(para[j]);
                    cacuData[j][4]=getAverage(para[j]);
                    cacuData[j][5]=getVariance(para[j]);
                }

                //计算变异系数
                double [] height = new double[19];
                for (int i = 0; i < 19; i++){
                    height[i]=cacuData[i][5]/cacuData[i][4];
                }

                //计算打分
                double [] group = new double[5];
                group[0]=height[0]+height[1]+height[2]+height[3];
                group[1]=height[4]+height[5]+height[6]+height[7]+height[8];
                group[2]=height[9]+height[10]+height[11];
                group[3]=height[12]+height[13]+height[14]+height[15]+height[16]+height[17]+height[18];
                group[4]=group[0]+group[1]+group[2]+group[3];

                double [] score = new double[5];
                if(group[0]==0){
                    score[0]=0;
                }else{
                    score[0]= para[0][data_num-1]*height[0]/(group[0])+
                            para[1][data_num-1]*height[1]/(group[0])+
                            para[2][data_num-1]*height[2]/(group[0])+
                            para[3][data_num-1]*height[3]/(group[0]);
                }
                if(group[1]==0){
                    score[1]=0;
                }else{
                    score[1]= para[4][data_num-1]*height[4]/(group[1])+
                            para[5][data_num-1]*height[5]/(group[1])+
                            para[6][data_num-1]*height[6]/(group[1])+
                            para[7][data_num-1]*height[7]/(group[1])+
                            para[8][data_num-1]*height[8]/(group[1]);
                }
                if(group[2]==0){
                    score[2]=0;
                }else{
                    score[2]= para[9][data_num-1]*height[9]/(group[2])+
                            para[10][data_num-1]*height[10]/(group[2])+
                            para[11][data_num-1]*height[11]/(group[2]);
                }
                if(group[3]==0){
                    score[3]=0;
                }else{
                    score[3]= para[12][data_num-1]*height[12]/(group[3])+
                            para[13][data_num-1]*height[13]/(group[3])+
                            para[14][data_num-1]*height[14]/(group[3])+
                            para[15][data_num-1]*height[15]/(group[3])+
                            para[16][data_num-1]*height[16]/(group[3])+
                            para[17][data_num-1]*height[17]/(group[3])+
                            para[18][data_num-1]*height[18]/(group[3]);
                }
                if(group[4]==0){
                    score[4]=0;
                }else{
                    score[4]=para[0][data_num-1]*height[0]/(group[4])+
                            para[1][data_num-1]*height[1]/(group[4])+
                            para[2][data_num-1]*height[2]/(group[4])+
                            para[3][data_num-1]*height[3]/(group[4])+
                            para[4][data_num-1]*height[4]/(group[4])+
                            para[5][data_num-1]*height[5]/(group[4])+
                            para[6][data_num-1]*height[6]/(group[4])+
                            para[7][data_num-1]*height[7]/(group[4])+
                            para[8][data_num-1]*height[8]/(group[4])+
                            para[9][data_num-1]*height[9]/(group[4])+
                            para[10][data_num-1]*height[10]/(group[4])+
                            para[11][data_num-1]*height[11]/(group[4])+
                            para[12][data_num-1]*height[12]/(group[4])+
                            para[13][data_num-1]*height[13]/(group[4])+
                            para[14][data_num-1]*height[14]/(group[4])+
                            para[15][data_num-1]*height[15]/(group[4])+
                            para[16][data_num-1]*height[16]/(group[4])+
                            para[17][data_num-1]*height[17]/(group[4])+
                            para[18][data_num-1]*height[18]/(group[4]);
                }

                for (int i = 0; i < 5; i++){
                    System.out.println("第"+(i+1)+"个分数为："+score[i]*100+"分");
                }
                System.out.println("====分数计算完毕！====");

                String [] alldata=new String[5];
                for (int i = 0; i < 5; i++){
                    alldata[i]=String.valueOf(score[i]*100);
                }

                //完成当前时间
                String tt = (String.valueOf(System.currentTimeMillis())).substring(0,13);

                double [] score_to_save = new double[19];

                for (int i=0; i<19; i++){
                    score_to_save[i]=para[i][data_num-1];
                }

                //写入数据库
                writeCompScore(alldata,tt);
                writeIndexWeight(height,tt);
                writeItem_score(score_to_save,tt);

            }
        }, time, 1000 * 60 * 5);
    }

    public void writeItem_score(double [] score,String tt){
        Item_score temp = new Item_score();
        temp.setData(convertDtoS(score),tt);

        Item_scoreResitory.save(temp);
    }

    public void writeIndexWeight(double [] height,String tt){
        Index_weight temp = new Index_weight();
        temp.setData(convertDtoS(height),tt);

        Index_weightResitory.save(temp);
    }

    public String [] convertDtoS(double [] data){
        String [] results = new String[data.length];
        for (int i = 0; i < data.length; i++){
            results[i]=String.valueOf(data[i]);
        }
        return results;
    }

    public void writeCompScore(String [] alldata,String tt){
//        System.out.println((String.valueOf(System.currentTimeMillis())).substring(0,13));
        Score items=new Score(alldata[0],alldata[1],alldata[2],alldata[3],alldata[4],tt);
        String insert_sql = "insert into complex_score (F_s,D_s,A_s,N_s,Final_s,calculate_time) value (?,?,?,?,?,?)";
        Object args[] = {items.getFs(),items.getDs(),items.getAs(),items.getNs(),items.getFinals(),items.getCtime()};
        int temp = jdbcTemplate.update(insert_sql, args);
        if(temp > 0) {
            System.out.println("====数据库complex_score插入成功！====");
        }else{
            System.out.println("====写入数据库complex_score失败！====");
        }
    }

    public double getMax(double[] data){
        double max = data[0];
        for(int i = 1 ;i < data.length ; i++){
            if(max < data[i]){
                max = data[i];
            }
        }
        return (double)max;
    }

    public double getMin(double[] data){
        double min = data[0];
        for(int i = 1 ;i < data.length ; i++){
            if(min > data[i]){
                min = data[i];
            }
        }
        return (double)min;
    }

    public double getAverage(double[] data){
        double sum = 0;
        for(int i = 0;i < data.length;i++){
            sum += data[i];
        }
        return (double)(sum / data.length);
    }

    public double getVariance(double[] data){
        double diff = 0;
        for(int i = 0 ;i < data.length ; i++) {
            diff+=(data[i]-getAverage(data))*(data[i]-getAverage(data));
        }
        double number = Math.sqrt(diff);
        number/=(data.length-1);//标准差
        return (double)number;
    }
}
