package model.service.export;

import model.entity.Movie;
import java.util.List;

public interface ExportStrategy {
    ExportType getType();
    String export(List<Movie> movies);
}