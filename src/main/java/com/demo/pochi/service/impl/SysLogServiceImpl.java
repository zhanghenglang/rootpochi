package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.SysLogDto;
import com.demo.pochi.pojo.SysLog;
import com.demo.pochi.repository.SysLogRepository;
import com.demo.pochi.service.SysLogService;
import com.demo.pochi.utils.DateUtils;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(SysLog sysLog) {
        sysLog.setLogId(idWorker.nextId()+"");
        sysLog.setCreatedBy("admin");
        sysLog.setCreatedDate(DateUtils.newDateTime());
        sysLogRepository.save(sysLog);
    }

    @Override
    public SysLog get(String id) {
        SysLog sysLog = sysLogRepository.findById(id).get();
        if (null != sysLog){
            return sysLog;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        boolean b = sysLogRepository.existsById(id);
        sysLogRepository.deleteById(id);
    }

    @Override
    public Page<SysLog> getByPage(SysLogDto sysLogDto) {
        //构建一个查询对象
        Query query = new Query();
        //排除列表页不需要的大字段
         query.fields().exclude("log_params");
         query.fields().exclude("log_result");
         query.fields().exclude("log_message");
         //设置查询参数
        if (StringUtils.isNotBlank(sysLogDto.getLogUrl())){
            //如果查询参数不为空
            query.addCriteria(Criteria.where("log_url").regex(sysLogDto.getLogUrl()));
        }

        if (sysLogDto.getLogStatus() != null) {
            //根据状态查询
            query.addCriteria(Criteria.where("log_status").is(sysLogDto.getLogStatus()));
        }
        //根据Controller查询
        if (StringUtils.isNotBlank(sysLogDto.getLogController())) {
            query.addCriteria(Criteria.where("log_controller").regex(sysLogDto.getLogController()));
        }
        //根据时间区间查询
        if (!CollectionUtils.isEmpty(sysLogDto.getCreatedDate())) {
            //创建时间集合不为空，就是用创建时间查询
            //同一个列出现很多次会报错，这里可以使用andOperator的方法
            query.addCriteria(Criteria.where("created_date").gt(sysLogDto.getCreatedDate().get(0))
                    .andOperator(Criteria.where("created_date").lt(sysLogDto.getCreatedDate().get(1))));
        }

        //处理分页条件
        Integer pageNumber = sysLogDto.getPageNumber();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            sysLogDto.setPageNumber(pageNumber);
        }
        Integer pageSize = sysLogDto.getPageSize();
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
            sysLogDto.setPageSize(pageSize);
        }
        //查询总条数
        long count = mongoTemplate.count(query, SysLog.class);
        //构造分页
        // 跳过多少3条
        query.skip((pageNumber - 1) * pageSize);
        // 取出多少条
        query.limit(pageSize);
        // 构造排序对象，默认根据创建时间倒序排序，并根据响应时间倒序排序
        Sort.Order dateOrder = new Sort.Order(Sort.Direction.DESC, "created_date");
        Sort.Order timeOrder = new Sort.Order(Sort.Direction.DESC, "log_time");
        // 设置排序对象
        query.with(Sort.by(dateOrder, timeOrder));
        //查询
        List<SysLog> sysLogs = mongoTemplate.find(query, SysLog.class);

        Page<SysLog> page = new Page<>();
        page.setList(sysLogs);
        page.setTotalCount((int) count);
        page.setTotalPage((int) Math.ceil(count * 1.0 / pageSize));
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }
}
