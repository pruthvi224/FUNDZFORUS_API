package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Program;
import com.fundzforus.server.domain.ProgramBooking;
import com.fundzforus.server.domain.Response;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.ProgramAlreadyExistException;
import com.fundzforus.server.exception.ProgramNotFoundException;
import com.fundzforus.server.service.IUserProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class UserProgramsRestApiController {

    @Autowired
    private IUserProgramService programServiceImpl;

    @GetMapping(value = "/rest/allProgramsByTenantId", produces = "application/json")
    public List<Program> getAllPrograms(@RequestHeader String tenantId) {
        return programServiceImpl.findAllProgramsByTenantId(Integer.parseInt(tenantId));
    }

    @GetMapping(value = "/rest/todayProgramsByTenantId", produces = "application/json")
    public List<Program> getTodayPrograms(@RequestHeader String tenantId) {
        return programServiceImpl.findTodayProgramsByTenantId(Integer.parseInt(tenantId));
    }

    @GetMapping(value = "/rest/tomorrowProgramsByTenantId", produces = "application/json")
    public List<Program> getTomorrowPrograms(@RequestHeader String tenantId) {
        return programServiceImpl.findTomorrowProgramsByTenantId(Integer.parseInt(tenantId));
    }

    @GetMapping(value = "/rest/oneWeekProgramsByTenantId", produces = "application/json")
    public List<Program> getOneWeekPrograms(@RequestHeader String tenantId) {
        return programServiceImpl.findOneWeekProgramsByTenantId(Integer.parseInt(tenantId));
    }

    @PostMapping(value = "/rest/program", produces = "application/json", consumes = "application/json")
    public Response createProgram(@RequestBody Program program) {
        int rowsInserted = 0;
        try {
            rowsInserted = programServiceImpl.createProgram(program);
        } catch (ProgramAlreadyExistException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/program", produces = "application/json")
    public Response updateProgram(@RequestBody Program program) {
        int rowsUpdated = 0;
        try {
            rowsUpdated = programServiceImpl.updateProgram(program);
        } catch (ProgramNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/program", produces = "application/json")
    public Program getProgram(@RequestHeader String programId) {
        Program response = programServiceImpl.getProgramById(Integer.parseInt(programId));
        return response;
    }

    @DeleteMapping(value = "/rest/program", produces = "application/json")
    public Response deleteProgram(@RequestHeader String programId) {
        int rowsDeleted = programServiceImpl.deleteProgram(Integer.parseInt(programId));
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/allProgramBookings", produces = "application/json")
    public List<ProgramBooking> getAllProgramBookings() {
        return programServiceImpl.findAllProgramBookings();
    }

    @GetMapping(value = "/rest/programBookingsByUserId", produces = "application/json")
    public List<ProgramBooking> getAllProgramBookingsByUserId(@RequestHeader String userId) {
        return programServiceImpl.findProgramBookingsByUserId(Integer.parseInt(userId));
    }

    @GetMapping(value = "/rest/programBookingsByProgramId", produces = "application/json")
    public List<ProgramBooking> getAllProgramBookingsByProgramId(@RequestHeader String programId) {
        return programServiceImpl.findProgramBookingsByProgramId(Integer.parseInt(programId));
    }

    @GetMapping(value = "/rest/programBookingsByUserIdAndProgramId", produces = "application/json")
    public ProgramBooking getAllProgramBookingsByProgramId(@RequestHeader String userId, @RequestHeader String programId) {
        return programServiceImpl.findProgramBookingByUserIdAndProgramId(Integer.parseInt(userId), Integer.parseInt(programId));
    }

    @PostMapping(value = "/rest/programBooking", produces = "application/json", consumes = "application/json")
    public Response createProgramBooking(@RequestBody ProgramBooking programBooking) {
        int rowsInserted = 0;
        try {
            rowsInserted = programServiceImpl.createProgramBooking(programBooking);
        } catch (ProgramAlreadyExistException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/programBooking", produces = "application/json")
    public Response updateProgramBooking(@RequestBody ProgramBooking programBooking) {
        int rowsUpdated = 0;
        try {
            rowsUpdated = programServiceImpl.updateProgramBooking(programBooking);
        } catch (ProgramNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @DeleteMapping(value = "/rest/programBooking", produces = "application/json")
    public Response deleteProgramBooking(@RequestHeader String bookingId) {
        int rowsDeleted = 0;
        try {
            rowsDeleted = programServiceImpl.deleteProgramBooking(Integer.parseInt(bookingId));
        } catch (ProgramNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}
