package controller.signIn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import model.Account;
import model.AccountsDAO;
import static model.ConnectionPoolMySQL.EXCEPCIONES;
import model.Country;
import utilities.ControllerGeneralModel;

/**
 * FXML Controller class
 *
 * @author JorgeLPR
 */
public class SignInFormController implements Initializable {
    
    @FXML
    private TextField txtUserSignIn, txtPasswordSignInMask;
    
    @FXML
    private PasswordField txtPasswordSignIn;
    
    @FXML
    private CheckBox checkViewPassSignIn;
    
    @FXML
    private Button btnSignIn, btnClean;
    
    private Account account;
    private AccountsDAO modelUser = new AccountsDAO();
        
    public void cleanFields(){
        txtPasswordSignIn.setText("");
        txtPasswordSignInMask.setText("");
        txtUserSignIn.setText("");        
    }
    
    @FXML
    public void eventKey(KeyEvent e){
        
        String c = e.getCharacter();
        
        if(c.equalsIgnoreCase(" ")){
            e.consume();
        }
        
    }
    
    @FXML
    public void actionEvent(ActionEvent e){
        
        Object evt = e.getSource();
        
        if(btnSignIn.equals(evt)){                    
                         
            if(!txtUserSignIn.getText().isEmpty() && !txtPasswordSignIn.getText().isEmpty()){

                String filter;
                
                if(ControllerGeneralModel.validateEmail(txtUserSignIn.getText())){
                    filter = "email";
                }else{
                    filter = "user";                
                }
                
                account = modelUser.selectAccount(txtUserSignIn.getText(), filter);
                
                if(account != null){

                    if(account.getPassword().equals(txtPasswordSignIn.getText())){
                        JOptionPane.showMessageDialog(null, "Bienvenido", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);                    
                        cleanFields();
                    }else{
                        JOptionPane.showMessageDialog(null, "La Contraseña que ha ingresado no es la correcta", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);                                            
                    }

                }else{

                    if(EXCEPCIONES.size()>0){
                        JOptionPane.showMessageDialog(null, "Surgieron errores en el proceso de consulta, posibles errores:\n"+
                                                      ControllerGeneralModel.toString(EXCEPCIONES), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "El Usuario no existe en la Base de Datos", "SIN RESULTADOS", JOptionPane.ERROR_MESSAGE);
                    }

                }
            
            }else{
                JOptionPane.showMessageDialog(null, "Debe llenar los campos obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
                        
        }else if(btnClean.equals(evt)){        
            cleanFields();
        }
    
    }
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        maskPassword(txtPasswordSignIn, txtPasswordSignInMask, checkViewPassSignIn);
        
        /*
        txtUserSignIn.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {                
                if(event.getCharacter().equals(" ")){
                    event.consume();
                }                
            }
        });*/
        
    }    
    
    public void maskPassword(PasswordField pass, TextField text, CheckBox check){
    
        text.setVisible(false);
        text.setManaged(false);
            
        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());
        
        text.textProperty().bindBidirectional(pass.textProperty());
    
    }
    
    
    
}
