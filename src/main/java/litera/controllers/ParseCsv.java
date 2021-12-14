package controllers;

import entities.Application;
import entities.Candidate;
import entities.City;
import entities.Technology;
import entities.enums.InterviewResults;
import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;
import entities.enums.Sources;
import services.ApplicationModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ParseCsv {

    ApplicationModel parseCsv();

    ApplicationModel domainModelToEntities(List<Object> name);

    Application parseApplication(Object bean);

    PreSelectionStatuses parsePreSelectionStatuses(Object bean);

    SelectionResults parseSelectionResult(Object bean);

    InterviewResults parseInterviewResults(Object bean);

    Candidate parseCandidate(Object bean);

    Sources parseSource(Object bean);

    City parseCity(Object bean);

    Technology parseTechnology(Object bean);

    LocalDateTime parseDate(Object bean);
}
