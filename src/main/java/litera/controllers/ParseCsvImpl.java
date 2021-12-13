package controllers;

import com.opencsv.bean.CsvToBeanBuilder;
import domain_model.DomainModel;
import entities.*;
import entities.enums.InterviewResults;
import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;
import entities.enums.Sources;
import repositories.Seeder;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseCsvImpl implements ParseCsv {

    private ApplicationModel applications;

    public ParseCsvImpl() {
        applications = new ApplicationModel();
    }

    public ApplicationModel parseCsv() {
        try {
            //domain model
            List<Object> beans = new CsvToBeanBuilder<>(new FileReader("src/main/resources/litera-candidates-data.csv"))
                    .withType(DomainModel.class)
                    .build()
                    .parse();

            domainModelToEntities(beans);
            return applications;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationModel domainModelToEntities(List<Object> beans) {
        for (Object bean : beans) {
            Candidate candidate = parseCandidate(bean);
            Application application = parseApplication(bean);
            application.setCandidate(candidate);
            applications.addApplication(application);
        }
        return applications;
    }

    private Application parseApplication(Object bean) {
        Application application = new Application();

        PreSelectionStatuses preSelectionStatuses = parsePreSelectionStatuses(bean);
        application.setPreSelectionStatus(preSelectionStatuses);

        SelectionResults selectionResults = parseSelectionResult(bean);
        application.setSelectionResult(selectionResults);

        InterviewResults interviewResults = parseInterviewResults(bean);
        application.setInterviewResult(interviewResults);

        application.setTechnology(parseTechnology(bean));

        LocalDateTime localDateTime = parseDate(bean);
        application.setInterviewDateAndTime(localDateTime);

        return application;
    }

    private PreSelectionStatuses parsePreSelectionStatuses(Object bean) {
        String preSelectionStatusString = (((DomainModel) bean).getPreSelectionStatus()).replaceAll(" ",
                "_").toUpperCase();
        if (!(preSelectionStatusString.isEmpty())) {
            return PreSelectionStatuses.valueOf(preSelectionStatusString);
        }
        return null;
    }

    private SelectionResults parseSelectionResult(Object bean) {
        String selectionResultString = (((DomainModel) bean).getSelectionResult()).toUpperCase();
        if (!(selectionResultString.isEmpty())) {
            return SelectionResults.valueOf(selectionResultString);
        }
        return null;
    }

    private InterviewResults parseInterviewResults(Object bean) {
        String interviewResultString = (((DomainModel) bean).getInterviewResult()).toUpperCase();
        if (!(interviewResultString.isEmpty())) {
            return InterviewResults.valueOf(interviewResultString);
        }
        return null;
    }

    private Candidate parseCandidate(Object bean) {
        Candidate candidate = new Candidate();
        candidate.setName(((DomainModel) bean).getName());
        candidate.setSurName(((DomainModel) bean).getSurName());
        candidate.setCity(parseCity(bean));
        candidate.setEmail(((DomainModel) bean).getEmail());
        candidate.setPhone(((DomainModel) bean).getPhone());
        candidate.setSource(parseSource(bean));

        return candidate;
    }

    private Sources parseSource(Object bean) {
        String sourcesString = (((DomainModel) bean).getSource()).toUpperCase();
        return Sources.valueOf(sourcesString);
    }

    private City parseCity(Object bean) {
        return new City(((DomainModel) bean).getCity());
    }

    private Technology parseTechnology(Object bean) {
        return new Technology(((DomainModel) bean).getTechnology());
    }

    private LocalDateTime parseDate(Object bean) {
        String date = ((DomainModel) bean).getInterviewDateAndTime();
        String regex = "(?<day>\\d{2})(/|-)(?<month>\\d{2})\\2(?<year>\\d{2})?((\\D*)(?<hour>\\d{2}):(?<minute>\\d{2}))?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        if (matcher.find()) {
            int day = Integer.parseInt(matcher.group("day"));
            int month = Integer.parseInt(matcher.group("month"));
            String yearGroup = matcher.group("year");
            String hourGroup = matcher.group("hour");
            String minuteGroup = matcher.group("minute");

            int year = yearGroup != null
                    ? Integer.parseInt("20" + yearGroup)
                    : LocalDate.now().getYear();

            if (hourGroup != null && minuteGroup != null) {
                int hour = Integer.parseInt(hourGroup);
                int minute = Integer.parseInt(minuteGroup);
                return LocalDateTime.of(year, month, day, hour, minute);
            }
            return LocalDateTime.of(year, month, day, 0, 0);
        }
        return LocalDateTime.now();
    }
}
