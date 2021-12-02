package parse_data;

import com.opencsv.bean.CsvToBeanBuilder;
import domain_model.DomainModel;
import entities.Application;
import entities.Candidate;
import entities.City;
import entities.Technology;
import entities.enums.InterviewResults;
import entities.enums.PreSelectionStatuses;
import entities.enums.SelectionResults;
import entities.enums.Sources;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvParse implements CsvParsing {

    private Applications applications;

    public CsvParse() {
        applications = new Applications();
    }

    public void parseCsv() {
        try {
            //domain model
            List<Object> beans = new CsvToBeanBuilder<>(new FileReader("src/main/resources/litera-candidates-data.csv"))
                    .withType(DomainModel.class)
                    .build()
                    .parse();

            domainModelToEntities(beans);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void domainModelToEntities(List<Object> beans) {
        for (Object bean : beans) {

            Candidate candidate = parseCandidate(bean);
            Application application = parseApplication(bean);
            application.setCandidate(candidate);
            applications.addApplication(application);
        }
    }

    private Application parseApplication(Object bean) {
        Application application = new Application();

        String preSelectionStatusString = (((DomainModel) bean).getPreSelectionStatus()).replaceAll(" ", "_").toUpperCase();
        if (!(preSelectionStatusString.isEmpty())) {
            PreSelectionStatuses preSelectionStatuses = PreSelectionStatuses.valueOf(preSelectionStatusString);
            application.setPreSelectionStatus(preSelectionStatuses);
        }

        String selectionResultString = (((DomainModel) bean).getSelectionResult()).toUpperCase();
        if (!(selectionResultString.isEmpty())) {
            SelectionResults selectionResults = SelectionResults.valueOf(selectionResultString);
            application.setSelectionResult(selectionResults);
        }

        String interviewResultString = (((DomainModel) bean).getInterviewResult()).toUpperCase();
        if (!(interviewResultString.isEmpty())) {
            InterviewResults interviewResults = InterviewResults.valueOf(interviewResultString);
            application.setInterviewResult(interviewResults);
        }

        application.setTechnology(parseTechnology(bean));

        String date = ((DomainModel) bean).getInterviewDateAndTime();
        LocalDateTime localDateTime = parseDate(date);
        application.setInterviewDateAndTime(localDateTime);

        return application;
    }

    private Candidate parseCandidate(Object bean) {
        Candidate candidate = new Candidate();
        candidate.setName(((DomainModel) bean).getName());
        candidate.setSurName(((DomainModel) bean).getSurName());
        candidate.setCity(parseCity(bean));
        candidate.setEmail(((DomainModel) bean).getEmail());
        candidate.setPhone(((DomainModel) bean).getPhone());

        String sourcesString = (((DomainModel) bean).getSource()).toUpperCase();
        Sources sources = Sources.valueOf(sourcesString);
        candidate.setSource(sources);

        return candidate;
    }

    private City parseCity(Object bean) {
        return new City(((DomainModel) bean).getCity());
    }

    private Technology parseTechnology(Object bean) {
        return new Technology(((DomainModel) bean).getTechnology());
    }

    private LocalDateTime parseDate(String date) {
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
