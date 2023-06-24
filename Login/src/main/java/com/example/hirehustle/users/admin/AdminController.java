package com.example.hirehustle.users.admin;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.jobPosts.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final JobPostService jobPostService;

    @PostMapping("/addAdmin")
    public String addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestBody Admin admin){
        return adminService.adminLogin(admin);
    }

    @GetMapping("/AllJobPosts")
    public List<JobPost> getAllJobPosts(){
        return jobPostService.fetchAllJobPosts();
    }

}
