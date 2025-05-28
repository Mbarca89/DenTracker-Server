package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "health_questionnaires")
@Getter
@Setter
public class HealthQuestionnaire extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private boolean healthAlteration;
    private boolean healthChange;

    private LocalDate lastMedicalCheck;

    private boolean medicalAttention;
    private String medicalAttentionReason;
    private String medicInformation;
    private boolean majorSurgery;
    private String majorSurgeryReason;
    private boolean hospitalized;
    private String hospitalizedReason;

    private Double height;
    private Double weight;
    private Double maxBloodPressure;
    private Double minBloodPressure;
    private Double pulse;
    private String generalObservations;

    private boolean heartAttacks;
    private boolean rheumatic;
    private boolean chestPain;
    private boolean breath;
    private boolean ankle;
    private boolean pillow;
    private boolean pacemaker;
    private boolean bloodPressureProblems;
    private String hearthObservations;

    private boolean epilepsy;
    private boolean faints;
    private boolean seizures;
    private boolean emotionalAlteration;
    private boolean alterationsTreatment;
    private String nervousObservations;

    private boolean cough;
    private boolean tuberculosis;
    private boolean familyTuberculosis;
    private boolean sinusitis;
    private boolean asma;
    private String breathObservations;

    private boolean stomachUlcer;
    private boolean hepatitis;
    private boolean jaundice;
    private boolean liver;
    private boolean bloodVomit;
    private String digestiveObservations;

    private boolean diabetis;
    private boolean familyDiabetis;
    private boolean urinate;
    private boolean thirst;
    private boolean hypothyroidism;
    private boolean hyperthyroidism;
    private String endocrineObservations;

    private boolean bloodProblems;
    private boolean familyBloodProblems;
    private boolean hemophiliac;
    private boolean abnormalBlood;
    private boolean bloodTransfusion;
    private String bloodObservation;

    private boolean anestheticsAlergy;
    private boolean antibioticsAlergy;
    private boolean barbituratesAlergy;
    private boolean analgesicsAlergy;
    private boolean asthma;
    private boolean skin;
    private String alergyObservations;

    private boolean kidneyProblems;
    private boolean syphilis;
    private boolean hiv;
    private String genitourinaryObservations;

    private boolean tumors;
    private boolean quimio;
    private boolean xrays;
    private String neoplaciaObservations;

    private boolean alcohol;
    private boolean smoker;
    private Double smokeTimes;
    private String habitsObservations;

    private boolean antibiotics;
    private boolean anticoagulants;
    private boolean bloodMedicines;
    private boolean tranquillizers;
    private boolean hormones;
    private boolean aspirines;
    private boolean bisphosphonates;
    private String otherMeds;
    private String midicineObservations;

    private boolean pregnant;
    private boolean pregnantPosibilities;
    private boolean breastfeeding;
    private boolean menstrual;
    private boolean hormonalTreatment;
    private boolean menstrualDisease;
    private boolean otherDiseases;
    private String otherDiseasesReason;
    private String womanObservations;

    private String mainDentalProblem;
    private boolean pain;
    private boolean dentalAspect;
    private boolean eatingProblems;
    private boolean headache;
    private boolean sinuses;
    private boolean previousTreatmentProblems;
    private String previousTreatmentProblemsReason;
    private String dentalObservations;

    public HealthQuestionnaire(Patient patient) {
        this.patient = patient;
    }

}
