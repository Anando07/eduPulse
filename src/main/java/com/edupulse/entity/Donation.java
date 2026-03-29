package com.edupulse.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Donor: each donation belongs to a user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User donor;

    // Donation date
    @Column(nullable = false)
    private LocalDate donationDate;

    // Location (city, hospital, or coordinates)
    private String location;

    // Status using Enum for type safety
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus status = DonationStatus.AVAILABLE;

    // Optional notes
    @Column(length = 500)
    private String notes;

    // Requester (who requested the donation, optional)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    private User requester;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }

    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }
}