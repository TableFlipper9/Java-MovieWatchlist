package model.service.export.strategies;

import model.entity.Movie;
import model.service.export.ExportStrategy;
import model.service.export.ExportType;

import java.util.List;

public class JSONExportStrategy implements ExportStrategy {

    @Override
    public ExportType getType() {
        return ExportType.JSON;
    }

    @Override
    public String export(List<Movie> movies) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            sb.append("  {")
                    .append("\"title\":\"").append(m.getTitle()).append("\",")
                    .append("\"genre\":\"").append(m.getGenre()).append("\",")
                    .append("\"year\":").append(m.getYear())
                    .append("}");

            if (i < movies.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }
}