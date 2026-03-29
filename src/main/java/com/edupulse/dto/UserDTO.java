package com.edupulse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private Long id;

    // ================= BASIC INFO =================
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    private String phone;

    // ================= BLOOD INFO =================
    private String bloodGroup;

    // ================= ACADEMIC INFO =================
    private String sscInstitution;
    private String sscGroup;
    private String sscRoll;
    private Integer sscPassingYear;

    private String hscInstitution;
    private String hscGroup;
    private String hscRoll;
    private Integer hscPassingYear;

    private String universityInstitution;
    private String universitySubject;
    private String universityRoll;
    private Integer universityPassingYear;

    private String batch;

    // ================= ADDRESS =================
    private String presentAddress;
    private String permanentAddress;

    // ================= PROFESSIONAL =================
    private String jobType;
    private String designation;

    // ================= PROFILE IMAGE =================
    private String profileImage;

    // ================= GETTERS & SETTERS =================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getSscInstitution() { return sscInstitution; }
    public void setSscInstitution(String sscInstitution) { this.sscInstitution = sscInstitution; }

    public String getSscGroup() { return sscGroup; }
    public void setSscGroup(String sscGroup) { this.sscGroup = sscGroup; }

    public String getSscRoll() { return sscRoll; }
    public void setSscRoll(String sscRoll) { this.sscRoll = sscRoll; }

    public Integer getSscPassingYear() { return sscPassingYear; }
    public void setSscPassingYear(Integer sscPassingYear) { this.sscPassingYear = sscPassingYear; }

    public String getHscInstitution() { return hscInstitution; }
    public void setHscInstitution(String hscInstitution) { this.hscInstitution = hscInstitution; }

    public String getHscGroup() { return hscGroup; }
    public void setHscGroup(String hscGroup) { this.hscGroup = hscGroup; }

    public String getHscRoll() { return hscRoll; }
    public void setHscRoll(String hscRoll) { this.hscRoll = hscRoll; }

    public Integer getHscPassingYear() { return hscPassingYear; }
    public void setHscPassingYear(Integer hscPassingYear) { this.hscPassingYear = hscPassingYear; }

    public String getUniversityInstitution() { return universityInstitution; }
    public void setUniversityInstitution(String universityInstitution) { this.universityInstitution = universityInstitution; }

    public String getUniversitySubject() { return universitySubject; }
    public void setUniversitySubject(String universitySubject) { this.universitySubject = universitySubject; }

    public String getUniversityRoll() { return universityRoll; }
    public void setUniversityRoll(String universityRoll) { this.universityRoll = universityRoll; }

    public Integer getUniversityPassingYear() { return universityPassingYear; }
    public void setUniversityPassingYear(Integer universityPassingYear) { this.universityPassingYear = universityPassingYear; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public String getPresentAddress() { return presentAddress; }
    public void setPresentAddress(String presentAddress) { this.presentAddress = presentAddress; }

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}