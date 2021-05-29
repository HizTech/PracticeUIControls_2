/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.signUp;

import controller.MainViewController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import model.Account;
import model.AccountDAO;
import model.Country;
import model.CountryDAO;
import utilities.ControllerGeneralModel;

/**
 * FXML Controller class
 *
 * @author JorgeLPR
 */
public class SignUpFormController implements Initializable {

    @FXML
    private TextField txtEmailSignUp, txtUserSignUp, txtPasswordSignUp, txtConfirmPasswordSignUp;
    
    @FXML
    private Button btnFormSignUp, btnCleanSignUp;
    
    @FXML
    private ComboBox<Country> cbCountrySignUp;
    
    protected MainViewController rootController;
    
    private CountryDAO modelCountry;
    private ArrayList<Country> listCountry;
    
    private AccountDAO modelAccount;
    
    public void getControllerRoot(MainViewController rootController){
        this.rootController = rootController;
    }    

    @FXML
    protected void keyEvent(KeyEvent e){
        String c = e.getCharacter();
        if(c.equalsIgnoreCase(" ")){
            e.consume();
        }    
    }

    @FXML
    protected void actionEvent(ActionEvent e){
    
        Object evt = e.getSource();
        
        if(evt.equals(btnFormSignUp)){

            if(!txtEmailSignUp.getText().isEmpty() && !txtUserSignUp.getText().isEmpty() && 
               !txtConfirmPasswordSignUp.getText().isEmpty() && !txtPasswordSignUp.getText().isEmpty()){
            
               Country country = cbCountrySignUp.getSelectionModel().getSelectedItem();
               
               if(country!=null){
                   
                   if(txtConfirmPasswordSignUp.getText().equals(txtPasswordSignUp.getText())){
                       
                       Account account = new Account();
                       account.setEmail(txtEmailSignUp.getText());
                       account.setPassword(txtPasswordSignUp.getText());
                       account.setUser(txtUserSignUp.getText());
                       account.setCountry(country);
                       
                       if(modelAccount.insertAccount(account)){

                           JOptionPane.showMessageDialog(null, "El Usuario ha sido registrado de manera éxitosa", "OPERACIÓN ÉXITOSA", JOptionPane.INFORMATION_MESSAGE);

                       }else{

                           if(AccountDAO.EXCEPCIONES.size()>0){
                               JOptionPane.showMessageDialog(null, "Surgieron errores en el proceso, posibles errores: \n"
                                       + ControllerGeneralModel.toString(AccountDAO.EXCEPCIONES));
                           }

                       }
                       
                   }else{
                       JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden, por favor verifique e intente nuevamente", "ERROR", JOptionPane.ERROR_MESSAGE);
                   }
                   
               }else{
                   JOptionPane.showMessageDialog(null, "Surgieron errores al conectar con la Base de Datos", "ERROR", JOptionPane.ERROR_MESSAGE);
               }
                
            }else{
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
            
        }else if(evt.equals(btnCleanSignUp)){
            
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
        modelCountry = new CountryDAO();
        modelAccount = new AccountDAO();
        listCountry = modelCountry.selectCountry();
        fillCombobox(listCountry);        
    }    
    
    public void fillCombobox(ArrayList<Country> list){
        if(list.size()>0){
            cbCountrySignUp.getItems().addAll(list);
            cbCountrySignUp.setConverter(new StringConverter<Country>() {
                @Override
                public String toString(Country country) {
                    return country.getCountry();
                }

                @Override
                public Country fromString(String string) {
                    return null;
                }
            });
            cbCountrySignUp.getSelectionModel().select(0);
            
        }
    }

    private void cleanFields() {
        txtEmailSignUp.setText("");
        txtPasswordSignUp.setText("");
        txtConfirmPasswordSignUp.setText("");
        txtUserSignUp.setText("");
        if(cbCountrySignUp.getItems().size()>0){
            cbCountrySignUp.getSelectionModel().select(0);            
        }else{
            listCountry = modelCountry.selectCountry();
            fillCombobox(listCountry);        
        }        
    }
    
}
