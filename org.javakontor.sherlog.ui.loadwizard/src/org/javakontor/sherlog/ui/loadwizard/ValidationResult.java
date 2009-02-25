package org.javakontor.sherlog.ui.loadwizard;

public class ValidationResult {

  private final String _errorMessage;

  public ValidationResult() {
    this(null);
  }

  public ValidationResult(String errorMessage) {
    super();
    _errorMessage = errorMessage;
  }

  public boolean hasError() {
    return (_errorMessage != null);
  }

  public String getErrorMessage() {
    return this._errorMessage;
  }

}
