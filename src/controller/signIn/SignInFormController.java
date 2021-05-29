package controller.signIn;

import controller.MainViewController;
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
import model.AccountDAO;
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
    private Button btnFormSignIn, btnCleanSignIn;
    
    private AccountDAO modelUser;
    
    @FXML
    public void keyEvent(KeyEvent e){
        
        String c = e.getCharacter();
        
        if(c.equalsIgnoreCase(" ")){
            e.consume();
        }
                
    }
    
    @FXML
    public void actionEvent(ActionEvent e){
        
        Object evt = e.getSource();
        
        if(evt.equals(btnFormSignIn)){
            
            if(!txtUserSignIn.getText().isEmpty() && !txtPasswordSignIn.getText().isEmpty()){

                Account account = modelUser.selectAccount(txtUserSignIn.getText());
                
                if(account != null){

                    if(account.getPassword().equals(txtPasswordSignIn.getText())){
                        JOptionPane.showMessageDialog(null, "Bienvenido", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);                    
                    }else{
                        JOptionPane.showMessageDialog(null, "La Contraseña que ha ingresado no es la correcta", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);                                            
                    }

                }else{

                    if(AccountDAO.EXCEPCIONES.size()>0){
                        JOptionPane.showMessageDialog(null, "Surgieron errores en el proceso de consulta, posibles errores:\n"+
                                                      ControllerGeneralModel.toString(AccountDAO.EXCEPCIONES), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "El Usuario no existe en la Base de Datos", "SIN RESULTADOS", JOptionPane.ERROR_MESSAGE);
                    }

                }
            
            }else{
                JOptionPane.showMessageDialog(null, "Debe llenar los campos obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(evt.equals(btnCleanSignIn)){
            txtUserSignIn.setText("");
            txtPasswordSignIn.setText("");
            checkViewPassSignIn.setSelected(false);
        }
        
    }    
    
    protected MainViewController rootController;
    
    public void getControllerRoot(MainViewController rootController){
        this.rootController = rootController;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maskPassword(txtPasswordSignIn, txtPasswordSignInMask, checkViewPassSignIn);
        modelUser = new AccountDAO();
    }    
    
    public void maskPassword(PasswordField pass, TextField text, CheckBox check){

        text.setManaged(false);
        text.setVisible(false);

        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());

        pass.managedProperty().bind(check.selectedProperty().not());
        pass.visibleProperty().bind(check.selectedProperty().not());

        text.textProperty().bindBidirectional(pass.textProperty());
    
    }
    
    
}
