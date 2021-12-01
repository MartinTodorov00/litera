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

public class ParseData {

    private Applications applications;

    public ParseData() {
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

            Application application = new Application();
            Candidate candidate = new Candidate();
            candidate.setName(((DomainModel) bean).getName());
            candidate.setSurName(((DomainModel) bean).getSurName());
            candidate.setCity(new City(((DomainModel) bean).getCity()));
            candidate.setEmail(((DomainModel) bean).getEmail());
            candidate.setPhone(((DomainModel) bean).getPhone());
            switch (((DomainModel) bean).getSource()) {
                case "InMail":
                    candidate.setSource(Sources.INMAIL);
                    break;
                case "Facebook":
                    candidate.setSource(Sources.FACEBOOK);
                    break;
                case "Referral":
                    candidate.setSource(Sources.REFERRAL);
                    break;
            }

            switch (((DomainModel) bean).getPreSelectionStatus()) {
                case "LOW SCORE":
                    application.setPreSelectionStatus(PreSelectionStatuses.LOW_SCORE);
                    break;
                case "OVERDUE":
                    application.setPreSelectionStatus(PreSelectionStatuses.OVERDUE);
                    break;
                case "TO BE INVITED":
                    application.setPreSelectionStatus(PreSelectionStatuses.TO_BE_INVITED);
                    break;
            }
            switch (((DomainModel) bean).getSelectionResult()) {
                case "REFUSED":
                    application.setSelectionResult(SelectionResults.REFUSED);
                    break;
                case "ACCEPTED":
                    application.setSelectionResult(SelectionResults.ACCEPTED);
                    break;
                case "REJECTED":
                    application.setSelectionResult(SelectionResults.REJECTED);
                    break;
                case "invalid":
                    application.setSelectionResult(SelectionResults.INVALID);
                    break;
            }
            application.setTechnology(new Technology(((DomainModel) bean).getTechnology()));

            String date = ((DomainModel) bean).getInterviewDateAndTime();
            LocalDateTime localDateTime = parseDate(date);
            application.setInterviewDateAndTime(localDateTime);

            switch (((DomainModel) bean).getInterviewResult()) {
                case "REFUSED":
                    application.setInterviewResult(InterviewResults.REFUSED);
                    break;
                case "PASSED":
                    application.setInterviewResult(InterviewResults.PASSED);
                    break;
                case "FAILED":
                    application.setInterviewResult(InterviewResults.FAILED);
                    break;
            }
            application.setCandidate(candidate);
            applications.addApplication(application);
        }
    }

    private static LocalDateTime parseDate(String date) {
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
