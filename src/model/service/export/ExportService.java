package model.service.export;

import model.entity.Movie;
import model.service.export.strategies.*;

import java.util.*;

public class ExportService {

    private final Map<ExportType, ExportStrategy> strategies = new HashMap<>();

    public ExportService() {
        registerStrategies();
    }

    private void registerStrategies() {
        List<ExportStrategy> strategyList = List.of(
                new JSONExportStrategy(),
                new XMLExportStrategy(),
                new CSVExportStrategy()
        );

        for (ExportStrategy strategy : strategyList) {
            strategies.put(strategy.getType(), strategy);
        }
    }

    public String export(List<Movie> movies, ExportType type) {
        ExportStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("No strategy for type: " + type);
        }

        return strategy.export(movies);
    }
}