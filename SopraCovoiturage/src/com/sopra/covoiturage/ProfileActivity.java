package com.sopra.covoiturage;

import modele.Information;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	private FacadeView facade;
	private TextView login;
	private TextView name;
	private TextView firstname;
	private TextView email;
	private TextView phone;
	private TextView postCode;
	private TextView workplace;
	private TextView goingTime;
	private TextView returningTime;
	private TextView workingDays;
	private TextView modify;
	private TextView delete;
	private TextView back;
	private TextView conductor;
	private TextView notification;
	private Information info ;

	/**
	 * Crée la page de consultation du profile utilisateur
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);
		
		facade = FacadeView.getInstance(this);

		login=(TextView) findViewById(R.id.login);
		name = (TextView) findViewById(R.id.nom);
		firstname = (TextView) findViewById(R.id.prenom);
		email = (TextView) findViewById(R.id.email);
		phone = (TextView) findViewById(R.id.telephone);
		postCode = (TextView) findViewById(R.id.code_postal);
		workingDays = (TextView) findViewById(R.id.jours);
		conductor = (TextView) findViewById(R.id.conducteur);
		notification = (TextView) findViewById(R.id.notification);	
		goingTime = (TextView) findViewById(R.id.heure_aller);
		returningTime = (TextView) findViewById(R.id.heure_retour);
		workplace = (TextView) findViewById(R.id.lieu_de_travail);	
		modify = (Button) findViewById(R.id.modifier);
		back = (Button) findViewById(R.id.retour);
		delete = (Button) findViewById(R.id.suppress);
	}
	
	public void onResume() {
		super.onResume();
		/** on récupère les infos du user */
		info = facade.getProfileInformation(facade.getProfileLogin());	
		
		// si on est admin, on n'affiche pas les boutons modifier et back
		if (!facade.getLogin().equals(facade.getProfileLogin())){
			modify.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);
		}
			

		login.setText(info.getLogin());
		name.setText(info.getName());
		firstname.setText(info.getFirstname());
		email.setText(info.getEmail());
		phone.setText(info.getPhone());
		//town.setText(info.getText);
		postCode.setText(info.getPostcode());
		workingDays.setText(info.daysToString());
		if (info.isConducteur())
			conductor.setText("oui");
		else conductor.setText("non");
		goingTime.setText(info.getMorning());
		returningTime.setText(info.getEvening());
		workplace.setText(info.getWorkplace());	
		if (info.isNotifie())
			notification.setText("oui");
		else notification.setText("non");
	}

	/**
	 * Renvoit à la page de modification du profile utilisateur
	 * @param v vue de l'application
	 */
	public void onModificationButtonClick(View v) {
		facade.changeActivity(ProfileModificationActivity.class);
	}

	/**
	 * Supprime l'utilisateur
	 * @param v vue de l'application
	 */
	public void onSuppressButtonClick(View v) {
		facade.performDeletion(info.getLogin());
		this.setResult(RESULT_OK);
		this.finish();
	}
	

}
