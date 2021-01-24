package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.ProgramBooking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserProgramBookingMapper {
    List<ProgramBooking> findAllProgramBookings();

    List<ProgramBooking> findProgramBookingsByUserId(Map<String, String> map);

    List<ProgramBooking> findProgramBookingsByProgramId(Map<String, String> map);

    ProgramBooking findProgramBookingByUserIdAndProgramId(Map<String, String> map);

    ProgramBooking getProgramBookingById(Map<String, String> map);

    int insertProgramBooking(ProgramBooking programBooking);

    int updateProgramBooking(ProgramBooking programBooking);

    int deleteProgramBookingById(Map<String, String> map);
}