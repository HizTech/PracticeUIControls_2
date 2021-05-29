package controller;

import controller.signIn.SignInFormController;
import controller.signUp.SignUpFormController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author JorgeLPR
 */
public class MainViewController implements Initializable {
    
    private SignInFormController signInController;
    private SignUpFormController signUpController;
        
    protected StackPane signInForm, signUpForm;
    
    @FXML
    private StackPane containerForm;
    
    @FXML
    private Button btnSignIn, btnSignUp;
    
    @FXML
    protected void actionEvent(ActionEvent e){
        
        Object evt = e.getSource();
        
        if(evt.equals(btnSignIn)){
            signInForm.setVisible(true);
            signUpForm.setVisible(false);
        }else if(evt.equals(btnSignUp)){
            signInForm.setVisible(false);
            signUpForm.setVisible(true);        
        }
        
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{            
            loadChild("/main/signIn/SignInForm.fxml", "Sign In");
            loadChild("/main/signUp/SignUpForm.fxml", "Sign Up");  
            containerForm.getChildren().addAll(signInForm, signUpForm);
            signInForm.setVisible(true);
            signUpForm.setVisible(false);
            signInController.getControllerRoot(this);
            signUpController.getControllerRoot(this);            
        }catch(IOException ex){
            System.out.println("Error al cargar modulo "+ex.getMessage());
        }
        
    }    
    
    public void loadChild(String url, String type) throws IOException{        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));        
        switch(type){
            case "Sign In":
                signInForm = (StackPane) loader.load();                
                signInController = loader.getController();
            break;
            case "Sign Up":
                signUpForm = (StackPane) loader.load();                                
                signUpController = loader.getController();
            break;
        }        
    }
    
    
}
