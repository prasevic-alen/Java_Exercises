package lu.uni;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named("loginBean")
public class LoginBean2 implements Serializable {

  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";
  private String name;
  private String password;
  private String inputname;
  private static final Logger logger = Logger.getLogger("loginBean");

  public String chooseAction() {
    if (name.startsWith("a")) {
      FacesContext.getCurrentInstance().addMessage("",
          new FacesMessage("Error: Account starts with 'a'"));
      return "";
    }

    if (name.equals("vmueller") && password.equals("vmueller")) {
      return SUCCESS;
    } else {
      return FAIL;
    }
  }

  public String donothing() {
    logger.log(Level.INFO, "Call of function 'donothing'");
    return SUCCESS;
  }

  public void ajaxreadInputName(AjaxBehaviorEvent event) {
    UIComponent uii = event.getComponent();
    String s = (String) uii.getAttributes().get("value");
    inputname = s + s;
    logger.log(Level.INFO, "Input ''{0}'' --> ''{1}''", new Object[] { s, inputname });
  }

  public LoginBean2() {
    name = null;
    password = null;
    inputname = null;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getInputname() {
    return inputname;
  }

  public void setInputname(String inputname) {
    this.inputname = inputname;
  }
}
