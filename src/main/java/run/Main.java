package run;

import parse_data.CsvParse;

public class Main {

    public static void main(String[] args) {

        CsvParse csvParse = new CsvParse();
        csvParse.parseCsv();
    }
}
