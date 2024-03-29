package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.List;

import modele.Information;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileAdminModificationActivity extends Activity {
	private FacadeView facade;
	private TextView pwd;
	private TextView pwdVerif;
	private TextView email;
	private Information info ;

	/**
	 * Crée la page de consultation du profile admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_modif_profile_page);
		facade = FacadeView.getInstance(this);
		pwd = (EditText) findViewById(R.id.mot_de_passe);
		pwdVerif = (EditText) findViewById(R.id.mdp2);
		email = (EditText) findViewById(R.id.email);	

		info = facade.getAdminInformation(facade.getLogin());

		//On met la valeur actuelle des infos dans les champs correspondant
		email.setText(info.getEmail());
	}

	/**
	 * Envoie les informations saisies si elles sont bonnes vers la base de donnée
	 *  @param v vue de l'application
	 */
	public void onModifierButtonClick(View v) {
		boolean pwdOk = pwd.getText().toString().equals(pwdVerif.getText().toString());

		/** Si toutes les infos on bien été rentrées on envoit le nouvel utilisateur */
		if (!pwdOk || 
				email.getText().toString().equals("")){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			// set le titre
			alertDialogBuilder.setTitle("Echec de la modification");
			if (!pwdOk){
				alertDialogBuilder
				.setMessage("Mot de passe invalide ");
			}
			else {
				alertDialogBuilder
				.setMessage("Veuillez remplir tous les champs demandés ");			
			}

			// set le message du dialogue
			alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// si le boutton est clicker, ferme seulement la box
					dialog.cancel();
				}
			});

			// crée la alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// l'affiche
			alertDialog.show();
		}else{

			String password = pwd.getText().toString();
			if (password.equals("")){
				password = null;
			}

			this.info = new Information(facade.getLogin(),password,
					email.getText().toString());
			facade.performAdminProfileModificationRequest (info);


		}
	}


	/**
	 * Renvoit à la page du profil
	 * @param v vue de l'application
	 */
	public void onAnnulerButtonClick(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set le titre
		alertDialogBuilder.setTitle("Echec de la modification");

		// set le message du dialogue
		alertDialogBuilder
		.setMessage("Voulez vous quittez la page?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// si le boutton est cliquer fermer l'activity actuelle
				// et ouvre la Connecting activity
				ProfileAdminModificationActivity.this.finish();
			}
		})

		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// si le boutton est clicker, ferme seulement la box
				dialog.cancel();
			}
		});

		// crée la alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// l'affiche
		alertDialog.show();
	}

	/**
	 * Fonction d'initialisation des spinner heure aller et heure retour
	 * @param spin
	 */
	private void InitHeure(Spinner spin) {
		String heure;
		List<String> list = new ArrayList<String>();

		for(int i=7; i< 20; i++) {
			for(int j=0; j<4; j++) {
				if (j==0){
					heure = i + ":00";
				} else {
					heure = i + ":" + j*15;
				}
				list.add(heure);
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(dataAdapter);
	}	
}