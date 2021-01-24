package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.Program;
import com.fundzforus.server.domain.ProgramBooking;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.IUserProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes("user")
public class UserProgramsController {

    @Autowired
    private IUserProgramService programServiceImpl;

    @GetMapping("/newProgram")
    public String newProgram(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            userForm.setNewProgram(new Program());
            model.addAttribute("userForm", userForm);
            return "add-program";
        }
    }

    @PostMapping("/newProgram")
    public String newProgramSubmitted(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Program newProgram = userForm.getNewProgram();
            newProgram.setTenantId(userForm.getUserTenantId());
            programServiceImpl.createProgram(newProgram);
            List<Program> programs = programServiceImpl.findAllProgramsByTenantId(Integer.parseInt(newProgram.getTenantId()));
            userForm.setPrograms(programs);
            model.addAttribute("userForm", userForm);
            return "programs";
        }
    }

    @GetMapping("/updateProgram")
    public String updateProgram(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int programId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Program newProgram = programServiceImpl.getProgramById(programId);
            userForm.setNewProgram(newProgram);
            model.addAttribute("userForm", userForm);
            return "update-program";
        }
    }

    @PostMapping("/updateProgram")
    public String updateProgramSubmitted(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int programId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Program newProgram = userForm.getNewProgram();
            programServiceImpl.updateProgram(userForm.getNewProgram());
            List<Program> programs = programServiceImpl.findAllProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setPrograms(programs);
            model.addAttribute("userForm", userForm);
            return "programs";
        }
    }

    @GetMapping("/deleteProgram")
    public String deleteProgram(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int programId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            programServiceImpl.deleteProgram(programId);
            List<Program> programs = programServiceImpl.findAllProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setPrograms(programs);
            model.addAttribute("userForm", userForm);
            return "programs";
        }
    }

    @GetMapping("/programs")
    public String programs(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            if (userForm.getUserTenantId() != null) {
                List<Program> programs = programServiceImpl.findAllProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setPrograms(programs);
            }
            model.addAttribute("userForm", userForm);
            return "programs";
        }
    }

    @GetMapping("/todayprograms")
    public String todayPrograms(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            if (userForm.getUserTenantId() != null) {
                List<Program> programs = programServiceImpl.findTodayProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setPrograms(programs);
            }
            model.addAttribute("userForm", userForm);
            return "todayprograms";
        }
    }

    @GetMapping("/tomorrowprograms")
    public String tomorrowPrograms(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            if (userForm.getUserTenantId() != null) {
                List<Program> programs = programServiceImpl.findTomorrowProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setPrograms(programs);
            }
            model.addAttribute("userForm", userForm);
            return "tomorrowprograms";
        }
    }

    @GetMapping("/thisweekprograms")
    public String thisWeekPrograms(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            if (userForm.getUserTenantId() != null) {
                List<Program> programs = programServiceImpl.findOneWeekProgramsByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setPrograms(programs);
            }
            model.addAttribute("userForm", userForm);
            return "oneweekprograms";
        }
    }

    @GetMapping("/programDetails")
    public String programDetails(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int programId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Program program = programServiceImpl.getProgramById(programId);
            userForm.setProgram(program);
            model.addAttribute("userForm", userForm);
            return "programDetails";
        }
    }

    @GetMapping("/program-confirmation")
    public String programConfirmation(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int programId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Program program = programServiceImpl.getProgramById(programId);

            userForm.setProgram(program);
            ProgramBooking programBooking = new ProgramBooking();
            programBooking.setProgramId(program.getId());
            programBooking.setUserId(userForm.getUserId());
            userForm.setProgramBooking(programBooking);
            model.addAttribute("userForm", userForm);
            return "program-confirmation";
        }
    }

    @PostMapping("/program-confirmed")
    public String programConfirmed(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            ProgramBooking dbProgramBooking = programServiceImpl.findProgramBookingByUserIdAndProgramId(userForm.getUserId(),
                    userForm.getProgramBooking().getProgramId());
            Program dbProgram = programServiceImpl.getProgramById(userForm.getProgramBooking().getProgramId());

            ProgramBooking programBooking = userForm.getProgramBooking();
            Program program = userForm.getProgram();
            int numberOfBookings = dbProgram.getBookings();

            int dbNumberOfPersons = 0;
            if (dbProgramBooking != null) {
                // Update Number Of Bookings in Program Table
                dbNumberOfPersons = dbProgramBooking.getNumberOfPersons();
                program.setBookings(numberOfBookings - dbNumberOfPersons + programBooking.getNumberOfPersons());
                programServiceImpl.updateProgramBooking(programBooking);
                programServiceImpl.updateProgram(program);
            } else {
                // Update Number Of Bookings in Program Table
                program.setBookings(numberOfBookings + programBooking.getNumberOfPersons());
                programServiceImpl.createProgramBooking(programBooking);
                programServiceImpl.updateProgram(program);
            }

            // Getting Booking Id
            dbProgramBooking = programServiceImpl.findProgramBookingByUserIdAndProgramId(userForm.getUserId(),
                    userForm.getProgramBooking().getProgramId());
            programBooking.setId(dbProgramBooking.getId());

            model.addAttribute("userForm", userForm);
            return "program-confirmed";
        }
    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
