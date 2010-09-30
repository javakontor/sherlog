package org.javakontor.sherlog.ui.loadwizard;

public class ValidationResult {

  private final String _errorMessage;

  public ValidationResult() {
    this(null);
  }

  public ValidationResult(String errorMessage) {
    super();
    this._errorMessage = errorMessage;
  }

  public boolean hasError() {
    return (this._errorMessage != null);
  }

  public boolean isValid() {
    return (this._errorMessage == null);
  }

  public String getErrorMessage() {
    return this._errorMessage;
  }

}
