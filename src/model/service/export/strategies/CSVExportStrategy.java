package model.service.export.strategies;

import model.entity.Movie;
import model.service.export.ExportStrategy;

import java.util.List;

public class CSVExportStrategy implements ExportStrategy {

    @Override
    public String export(List<Movie> movies) {
        StringBuilder sb = new StringBuilder();

        sb.append("Title,Genre,Year\n");

        for (Movie m : movies) {
            sb.append(m.getTitle()).append(",")
                    .append(m.getGenre()).append(",")
                    .append(m.getYear()).append("\n");
        }

        return sb.toString();
    }
}