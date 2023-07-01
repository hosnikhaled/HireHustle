package com.example.hirehustle.users.admin;

import com.example.hirehustle.jobPosts.JobPostService;
import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final JobPostService jobPostService;
    private final Gson gson = new Gson();

    @PostMapping("/addAdmin")
    public String addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestBody Admin admin){
        return adminService.adminLogin(admin);
    }

    @GetMapping("/allValidJobPosts")
    public Map<String, Object> getAllValidJobPosts(){
        JobPostAdditionResponse response = jobPostService.fetchAllValidJobPosts();
        return response.mapToArrangeGson();
    }

}
