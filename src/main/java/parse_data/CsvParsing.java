package parse_data;

import java.util.List;

public interface CsvParsing {

    ApplicationModel parseCsv();
    ApplicationModel domainModelToEntities(List<Object> name);
}
