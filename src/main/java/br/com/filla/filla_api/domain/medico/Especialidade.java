package br.com.filla.filla_api.domain.medico;

public enum Especialidade {

  ORTOPEDIA("Ortopedia"),
  CARDIOLOGIA("Cardiologia"),
  GINECOLOGIA("Ginecologia"),
  DERMATOLOGIA("Dermatologia");

  private String label;

  private Especialidade(String label) {
    this.label = label;
  }

  public String label() {
    return this.label;
  }

}
