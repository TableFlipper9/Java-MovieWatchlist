package model.service.export;

import model.entity.Movie;
import java.util.List;

public class ExportService {

    private ExportStrategy strategy;

    public void setStrategy(ExportStrategy strategy) {
        this.strategy = strategy;
    }

    public String export(List<Movie> movies) {
        if (strategy == null) {
            throw new IllegalStateException("Export strategy not set");
        }
        return strategy.export(movies);
    }
}