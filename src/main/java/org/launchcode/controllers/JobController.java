package org.launchcode.controllers;


import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.launchcode.models.*; // Job won't work without this
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // redirect won't work w/o this

import java.util.ArrayList;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes details) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        //Form validation check
        if(errors.hasErrors()){
            return "new-job";
        }
        else{

            // New Job information
            String name_of_job = jobForm.getName();
            Employer name_of_employers = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location location_of_job = jobData.getLocations().findById(jobForm.getLocationId());
            CoreCompetency competencies = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());
            PositionType name_of_job_position = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

            // Make a new job
            Job newJob = new Job(name_of_job, name_of_employers, location_of_job,name_of_job_position, competencies);

            // Add new job to jobData
            jobData.add(newJob);

            // Add the new job id to redirect
            details.addAttribute("id", newJob.getId());

            // View new job details
            return "redirect:/job";
        }



    }
}
