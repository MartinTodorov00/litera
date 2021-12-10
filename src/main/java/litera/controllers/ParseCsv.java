package controllers;

import entities.ApplicationModel;

import java.util.List;

public interface ParseCsv {

    ApplicationModel parseCsv();
    ApplicationModel domainModelToEntities(List<Object> name);
}
