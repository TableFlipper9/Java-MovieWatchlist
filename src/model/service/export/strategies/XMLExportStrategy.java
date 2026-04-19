package model.service.export.strategies;

import model.entity.Movie;
import model.service.export.ExportStrategy;
import model.service.export.ExportType;

import java.util.List;

public class XMLExportStrategy implements ExportStrategy {

    @Override
    public ExportType getType() {
        return ExportType.XML;
    }

    @Override
    public String export(List<Movie> movies) {
        StringBuilder sb = new StringBuilder();
        sb.append("<movies>\n");

        for (Movie m : movies) {
            sb.append("<movie>\n")
                    .append("<title>").append(m.getTitle()).append("</title>\n")
                    .append("<genre>").append(m.getGenre()).append("</genre>\n")
                    .append("<year>").append(m.getYear()).append("</year>\n")
                    .append("</movie>\n");
        }

        sb.append("</movies>");
        return sb.toString();
    }
}