package org.example.servicecontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;



@RestController  // 关键修改：替换@Controller为@RestController
@RequestMapping("/delivery")
@Tag(name = "快递管理")
public class DeliveryController {
        @Autowired
        LogisticsService logisticsService;

        // 获取所有快递公司
        @GetMapping("/comAll")
        @Operation(summary = "获取所有快递公司信息")
        public List<Map<String, Object>> getAllCompanies() {
            return logisticsService.getAllExpressCompanies();
        }

        // 根据ID获取快递公司
        @GetMapping("/com/{id}")
        @Operation(summary = "根据ID获取快递公司信息")
        @Parameter(name = "id", description = "快递公司ID",in = ParameterIn.PATH)
        public Map<String, Object> getCompanyById(@PathVariable int id) {
            return logisticsService.getExpressCompanyById(id);
        }

        // 添加快递公司
        // 修改后的添加快递公司方法
        // 添加快递公司方法
        @PostMapping("/comAdd")
        @Operation(summary = "添加快递公司")
        @Parameters( {
                @Parameter(name = "name", description = "快递公司名称",in = ParameterIn.QUERY),
                @Parameter(name = "website", description = "快递公司网址",in = ParameterIn.QUERY),
                @Parameter(name = "createTime", description = "创建时间",in = ParameterIn.QUERY),
                @Parameter(name = "updateTime", description = "更新时间",in = ParameterIn.QUERY),
                @Parameter(name = "queryUrl", description = "查询接口URL",in = ParameterIn.QUERY)

        })
        public ResponseEntity<String> createCompany(
                @RequestParam String name,
                @RequestParam(required = false) String website,
                @RequestParam(required = false) String createTime,
                @RequestParam(required = false) String updateTime,
                @RequestParam(required = false) String queryUrl) {

            // 自动生成时间戳（如果未提供）
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime = LocalDateTime.now().format(formatter);
            String finalCreateTime = (createTime == null || createTime.isEmpty()) ? currentTime : createTime;
            String finalUpdateTime = (updateTime == null || updateTime.isEmpty()) ? currentTime : updateTime;

            // 设置默认值
            if (website == null) website = "";
            if (queryUrl == null) queryUrl = "";

            int result = logisticsService.addExpressCompany(
                    name,
                    website,
                    finalCreateTime,
                    finalUpdateTime,
                    queryUrl);

            return result > 0
                    ? ResponseEntity.ok("创建成功")
                    : ResponseEntity.badRequest().body("创建失败");
        }

    // 更新快递公司信息
    @PutMapping("/com/{id}")
    @Operation(summary = "更新快递公司信息")
    @Parameters( {
            @Parameter(name = "id", description = "快递公司ID",in = ParameterIn.PATH),
            @Parameter(name = "name", description = "快递公司名称",in = ParameterIn.QUERY),
            @Parameter(name = "website", description = "快递公司网址",in = ParameterIn.QUERY),
            @Parameter(name = "queryUrl", description = "查询接口URL",in = ParameterIn.QUERY)
    })
    public ResponseEntity<String> updateCompany(
            @PathVariable int id,
            @RequestParam String name,
            @RequestParam(required = false) String website,
            @RequestParam(required = false) String queryUrl) {

        int result = logisticsService.updateExpressCompany(id, name, website, queryUrl);
        return result > 0
                ? ResponseEntity.ok("更新成功")
                : ResponseEntity.badRequest().body("更新失败");
    }






    // 删除快递公司
        @DeleteMapping("/com/{id}")
        @Operation(summary = "删除快递公司")
        public ResponseEntity<String> deleteCompany(@PathVariable int id) {
            int result = logisticsService.deleteExpressCompany(id);
            return result > 0 ? ResponseEntity.ok("删除成功") : ResponseEntity.badRequest().body("删除失败");
        }

        // 获取快递查询链接
        @GetMapping("/{id}/tracking/{trackingNumber}")
        @Operation(summary = "获取快递查询链接")
        public ResponseEntity<String> getTrackingUrl(
                @PathVariable int id,
                @PathVariable String trackingNumber) {
            String url = logisticsService.getTrackingUrl(id, trackingNumber);
            return url != null ? ResponseEntity.ok(url) : ResponseEntity.notFound().build();
        }
}

