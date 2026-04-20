package model.service.export;

import model.entity.Movie;
import model.repository.FileSaver;
import model.service.export.strategies.*;

import java.util.*;

public class ExportService {

    private final Map<ExportType, ExportStrategy> strategies = new HashMap<>();
    private final FileSaver fileSaver = FileSaver.getInstance();

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

    public void export(List<Movie> movies, ExportType type) {
        ExportStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("No strategy for type: " + type);
        }

        String data = strategy.export(movies);
        fileSaver.saveToFile(data, type.getExtension());
    }
}