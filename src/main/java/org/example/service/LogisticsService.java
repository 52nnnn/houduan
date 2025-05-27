package org.example.service;

import org.example.dao.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class LogisticsService {

    @Autowired
    ServiceDao serviceDao;

    // 获取所有快递公司
    public List<Map<String,Object>> getAllExpressCompanies(){
        return serviceDao.queryAllExpressCompanies();
    }

    // 根据ID获取快递公司
    public Map<String,Object> getExpressCompanyById(int id){
        return serviceDao.queryCompanyById(id);
    }

    // 添加快递公司
    public int addExpressCompany(String name, String website, String createTime, String updateTime, String queryUrl){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("快递公司名称不能为空");
        }

        // 设置默认值
        if (website == null) website = "";
        if (queryUrl == null) queryUrl = "";
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (createTime == null) createTime = currentTime;
        if (updateTime == null) updateTime = currentTime;

        return serviceDao.addCompany(name, website, currentTime, currentTime, queryUrl);
    }


    // 更新快递公司信息
    public int updateExpressCompany(int id, String name, String website,
                                    String queryUrl) {
        // 数据校验
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("快递公司名称不能为空");
        }

        // 自动生成更新时间
        String updateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 设置默认值
        if (website == null) website = "";
        if (queryUrl == null) queryUrl = "";

        return serviceDao.updateCompany(id, name, website, updateTime, queryUrl);
    }





    // 删除快递公司
    public int deleteExpressCompany(int id){
        return serviceDao.deleteCompany(id);
    }

    // 获取快递查询URL(根据快递公司ID和快递单号)
    public String getTrackingUrl(int companyId, String trackingNumber){
        Map<String,Object> company = serviceDao.queryCompanyById(companyId);
        if(company != null && company.containsKey("queryUrl")){
            return company.get("queryUrl").toString()
                    .replace("{dvyFlowId}", trackingNumber);
        }
        return null;
    }
}
