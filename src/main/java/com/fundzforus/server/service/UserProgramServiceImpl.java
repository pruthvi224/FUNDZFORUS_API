package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.UserProgramBookingMapper;
import com.fundzforus.server.dao.mybatis.UserProgramMapper;
import com.fundzforus.server.domain.Program;
import com.fundzforus.server.domain.ProgramBooking;
import com.fundzforus.server.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserProgramServiceImpl implements IUserProgramService {

    public static final String DD_MMM_YY_HH_MM = "dd-MMM-yy HH:mm";

    @Autowired
    private UserProgramMapper userProgramMapper;

    @Autowired
    private UserProgramBookingMapper userProgramBookingMapper;

    @Override
    public List<Program> findAllProgramsByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userProgramMapper.findAllProgramsByTenantId(parameterMap);
    }

    @Override
    public List<Program> findTodayProgramsByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userProgramMapper.findTodayProgramsByTenantId(parameterMap);
    }

    @Override
    public List<Program> findTomorrowProgramsByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userProgramMapper.findTomorrowProgramsByTenantId(parameterMap);
    }

    @Override
    public List<Program> findOneWeekProgramsByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userProgramMapper.findOneWeekProgramsByTenantId(parameterMap);
    }

    @Override
    public Program getProgramById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        Program program = userProgramMapper.getProgramById(parameterMap);
        program.setProgramDateTimeField(program.getProgramDateTime().format(DateTimeFormatter.ofPattern(DD_MMM_YY_HH_MM)));
        return program;
    }

    @Override
    public int createProgram(Program program) {
        if (StringUtils.isBlank(program.getProgramName()) || StringUtils.isBlank(program.getProgramDescription()) ||
                program.getProgramDateTimeField() == null) {
            throw new MissingMandatoryFieldsException("Program Name, Description and ProgramDateTime can not be Empty");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MMM_YY_HH_MM);
            program.setProgramDateTime(LocalDateTime.parse(program.getProgramDateTimeField(), formatter));

            Map parameterMap = new HashMap();
            parameterMap.put("programName", program.getProgramName());
            parameterMap.put("tenantId", program.getTenantId());
            Program dbProgram = userProgramMapper.findProgramsByProgramName(parameterMap);
            if (dbProgram != null) {
                throw new ProgramAlreadyExistException("Program With Name ::" + program.getProgramName() + " already exist");
            }
            program.setCreatedBy("MOBILE_APP");
            program.setUpdatedBy("MOBILE_APP");
            return userProgramMapper.insertProgram(program);
        }
    }

    @Override
    public int updateProgram(Program program) {
        if (StringUtils.isBlank(program.getProgramName()) || StringUtils.isBlank(program.getProgramDescription()) ||
                program.getProgramDateTimeField() == null) {
            throw new MissingMandatoryFieldsException("Program Name, Description and ProgramDateTime can not be Empty");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MMM_YY_HH_MM);
            program.setProgramDateTime(LocalDateTime.parse(program.getProgramDateTimeField(), formatter));

            Map parameterMap = new HashMap();
            parameterMap.put("id", program.getId());
            Program dbProgram = userProgramMapper.getProgramById(parameterMap);
            if (dbProgram == null) {
                throw new ProgramNotFoundException("Program With Name ::" + program.getProgramName() + " does not exist");
            }
            program.setId(dbProgram.getId());
            program.setUpdatedBy("MOBILE_APP");
            return userProgramMapper.updateProgram(program);
        }
    }

    @Override
    public int deleteProgram(int programId) {
        if (programId == 0) {
            throw new MissingMandatoryFieldsException("Program Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("id", programId);
        Program dbProgram = userProgramMapper.getProgramById(parameterMap);
        if (dbProgram != null) {
            parameterMap.put("id", dbProgram.getId());
            return userProgramMapper.deleteProgramById(parameterMap);
        } else {
            throw new ProgramNotFoundException("Program with Id ::" + programId + " does not exist");
        }
    }

    @Override
    public List<ProgramBooking> findAllProgramBookings() {
        return userProgramBookingMapper.findAllProgramBookings();
    }

    @Override
    public ProgramBooking findProgramBookingByUserIdAndProgramId(int userId, int programId) {
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        parameterMap.put("programId", programId);
        return userProgramBookingMapper.findProgramBookingByUserIdAndProgramId(parameterMap);
    }

    @Override
    public List<ProgramBooking> findProgramBookingsByUserId(int userId) {
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        return userProgramBookingMapper.findProgramBookingsByUserId(parameterMap);
    }

    @Override
    public List<ProgramBooking> findProgramBookingsByProgramId(int programId) {
        Map parameterMap = new HashMap();
        parameterMap.put("programId", programId);
        return userProgramBookingMapper.findProgramBookingsByProgramId(parameterMap);
    }

    @Override
    public ProgramBooking getProgramBookingById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        return userProgramBookingMapper.getProgramBookingById(parameterMap);
    }

    @Override
    public int createProgramBooking(ProgramBooking programBooking) {
        if (programBooking.getProgramId() <= 0 || programBooking.getUserId() <= 0 ||
                programBooking.getNumberOfPersons() <= 0) {
            throw new MissingMandatoryFieldsException("ProgramId, UserId and NumberOfPersons can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("programId", programBooking.getProgramId());
            parameterMap.put("userId", programBooking.getUserId());
            ProgramBooking dbProgramBooking = userProgramBookingMapper.findProgramBookingByUserIdAndProgramId(parameterMap);
            if (dbProgramBooking != null) {
                throw new UserProgramBookingAlreadyExistException("Program With UserId ::" + programBooking.getUserId() +
                        " and ProgramId ::" + programBooking.getProgramId() + " already exist");
            }
            programBooking.setCreatedBy("MOBILE_APP");
            programBooking.setUpdatedBy("MOBILE_APP");
            return userProgramBookingMapper.insertProgramBooking(programBooking);
        }
    }

    @Override
    public int updateProgramBooking(ProgramBooking programBooking) {
        if (programBooking.getProgramId() <= 0 || programBooking.getUserId() <= 0 ||
                programBooking.getNumberOfPersons() <= 0) {
            throw new MissingMandatoryFieldsException("ProgramId, UserId and NumberOfPersons can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("programId", programBooking.getProgramId());
            parameterMap.put("userId", programBooking.getUserId());
            ProgramBooking dbProgramBooking = userProgramBookingMapper.findProgramBookingByUserIdAndProgramId(parameterMap);
            if (dbProgramBooking == null) {
                throw new UserProgramBookingNotFoundException("Program With UserId ::" + programBooking.getUserId() +
                        " and ProgramId ::" + programBooking.getProgramId() + " does not exist");
            }
            programBooking.setId(dbProgramBooking.getId());
            programBooking.setUpdatedBy("MOBILE_APP");
            return userProgramBookingMapper.updateProgramBooking(programBooking);
        }
    }

    @Override
    public int deleteProgramBooking(int bookingId) {
        if (bookingId == 0) {
            throw new MissingMandatoryFieldsException("Booking Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("id", bookingId);
        ProgramBooking dbProgramBooking = userProgramBookingMapper.getProgramBookingById(parameterMap);
        if (dbProgramBooking != null) {
            parameterMap.put("id", dbProgramBooking.getId());
            return userProgramBookingMapper.deleteProgramBookingById(parameterMap);
        } else {
            throw new UserProgramBookingNotFoundException("Booking with Id ::" + bookingId + " does not exist");
        }
    }
}
