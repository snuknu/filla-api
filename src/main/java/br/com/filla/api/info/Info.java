package br.com.filla.api.info;

import lombok.Getter;

@Getter
public class Info {

  private static Info instance;
  private String projectName;
  private String description;
  private String version;
  private String author;

  private Info(
      String projectName,
      String description,
      String version,
      String author) {
    this.projectName = projectName;
    this.description = description;
    this.version = version;
    this.author = author;
  }

  public static Info getInstance() {
    if (instance == null) {
      instance = new Info(
          "Filla API",
          "API for scheduling various services.",
          "0.0.1-SNAPSHOT",
          "Eric Azevedo Carvalho");
    }
    return instance;
  }

}
