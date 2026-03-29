package com.edupulse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC INFO =================
    @NotBlank
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String password;

    private String phone;

    // User active or not
    private boolean enabled = false;

    // ================= ROLE RELATION =================
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;

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
    @Column(length = 500)
    private String presentAddress;

    @Column(length = 500)
    private String permanentAddress;

    // ================= PROFESSIONAL =================
    private String jobType;
    private String designation;

    // ================= PROFILE IMAGE =================
    @Column(name = "profile_image")
    private String profileImage; // store filename or URL

    // ================= VERIFICATION =================
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    @Column(length = 100)
    private String emailVerificationToken;

    @Column(length = 10)
    private String phoneVerificationCode;

    // ================= DONATION RELATION =================
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Donation> donations;

    // ================= BUSINESS LOGIC =================
    public void updateVerificationStatus() {
        // Enable user if ANY one is verified
        if (this.emailVerified || this.phoneVerified) {
            this.enabled = true;
        }
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

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

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public boolean isPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getEmailVerificationToken() { return emailVerificationToken; }
    public void setEmailVerificationToken(String emailVerificationToken) { this.emailVerificationToken = emailVerificationToken; }

    public String getPhoneVerificationCode() { return phoneVerificationCode; }
    public void setPhoneVerificationCode(String phoneVerificationCode) { this.phoneVerificationCode = phoneVerificationCode; }

    public List<Donation> getDonations() { return donations; }
    public void setDonations(List<Donation> donations) { this.donations = donations; }
}