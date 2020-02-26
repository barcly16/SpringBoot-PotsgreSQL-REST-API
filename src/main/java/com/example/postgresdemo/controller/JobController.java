package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Employee;
import com.example.postgresdemo.model.Job;
import com.example.postgresdemo.repository.EmployeeRepository;
import com.example.postgresdemo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobs")
    public Page<Job> getJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @PostMapping("/job")
    public Job createJob(@Valid @RequestBody Job job) {
        return jobRepository.save(job);
    }

    @PutMapping("/job/{jobId}")
    public Job updateJob(@PathVariable Integer jobId,
                                   @Valid @RequestBody Job jobRequest) {
        return jobRepository.findById(jobId)
                .map(job -> {
                    job.setName(jobRequest.getName());
                    job.setDescription(jobRequest.getDescription());
                    return jobRepository.save(job);
                }).orElseThrow(() -> new ResourceNotFoundException("Job not found with id " + jobId));
    }

    @DeleteMapping("/jon/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable Integer jobId) {
        return jobRepository.findById(jobId)
                .map(job -> {
                    jobRepository.delete(job);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Job not found with id " + jobId));
    }

}
