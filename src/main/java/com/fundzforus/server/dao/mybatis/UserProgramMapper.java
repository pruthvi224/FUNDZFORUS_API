package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.Program;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserProgramMapper {
    List<Program> findTodayProgramsByTenantId(Map<String, String> map);

    List<Program> findTomorrowProgramsByTenantId(Map<String, String> map);

    List<Program> findOneWeekProgramsByTenantId(Map<String, String> map);

    List<Program> findAllProgramsByTenantId(Map<String, String> map);

    Program getProgramById(Map<String, String> map);

    Program findProgramsByProgramName(Map<String, String> map);

    int insertProgram(Program program);

    int updateProgram(Program program);

    int deleteProgramById(Map<String, String> map);
}
