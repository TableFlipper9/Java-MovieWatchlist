package model.service.export;

public enum ExportType {
    JSON("json"),
    XML("xml"),
    CSV("csv");

    private final String extension;

    ExportType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}