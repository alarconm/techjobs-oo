package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.JobFieldType;
import org.launchcode.models.Location;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // Returns the form if the name is blank
        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }

        // Create a new job with the information from the form
        // Add job to jobData
        // Display job detail page for the newly created job
        //TODO Change to JobData.Employers() etc. to get info
        Job newJob = new Job(
                jobForm.getName(),
                jobForm.getEmployerById(jobForm.getEmployerId()),
                jobForm.getLocation(),
                jobForm.getPositionType(),
                jobForm.getCoreCompetency());

        jobData.add(newJob);
        model.addAttribute("job", newJob);

        return "job-detail";

    }
}
