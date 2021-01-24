package com.fundzforus.server.service;

import com.fundzforus.server.domain.Program;
import com.fundzforus.server.domain.ProgramBooking;

import java.util.List;

public interface IUserProgramService {
    List<Program> findAllProgramsByTenantId(int tenantId);

    List<Program> findTodayProgramsByTenantId(int tenantId);

    List<Program> findTomorrowProgramsByTenantId(int tenantId);

    List<Program> findOneWeekProgramsByTenantId(int tenantId);

    Program getProgramById(int id);

    int createProgram(Program program);

    int updateProgram(Program program);

    int deleteProgram(int programId);

    List<ProgramBooking> findAllProgramBookings();

    List<ProgramBooking> findProgramBookingsByUserId(int userId);

    List<ProgramBooking> findProgramBookingsByProgramId(int programId);

    ProgramBooking findProgramBookingByUserIdAndProgramId(int userId, int programId);

    ProgramBooking getProgramBookingById(int id);

    int createProgramBooking(ProgramBooking programBooking);

    int updateProgramBooking(ProgramBooking programBooking);

    int deleteProgramBooking(int bookingId);

}
