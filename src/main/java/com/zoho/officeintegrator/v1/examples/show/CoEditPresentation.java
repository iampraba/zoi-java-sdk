package com.zoho.officeintegrator.v1.examples.show;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.zoho.api.authenticator.Auth;
import com.zoho.api.authenticator.Token;
import com.zoho.officeintegrator.Initializer;
import com.zoho.officeintegrator.dc.USDataCenter;
import com.zoho.officeintegrator.logger.Logger;
import com.zoho.officeintegrator.logger.Logger.Levels;
import com.zoho.officeintegrator.util.APIResponse;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.CreatePresentationParameters;
import com.zoho.officeintegrator.v1.DocumentInfo;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.ShowResponseHandler;
import com.zoho.officeintegrator.v1.UserInfo;
import com.zoho.officeintegrator.v1.V1Operations;

public class CoEditPresentation {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CoEditPresentation.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();
			
			V1Operations sdkOperations = new V1Operations();
			CreatePresentationParameters parameters = new CreatePresentationParameters();
			
			parameters.setUrl("https://demo.office-integrator.com/samples/show/Zoho_Show.pptx");
			
			UserInfo user1Info = new UserInfo();
			
			user1Info.setUserId("100");
			user1Info.setDisplayName("Praba");

			parameters.setUserInfo(user1Info);
			
			DocumentInfo documentInfo = new DocumentInfo();

			documentInfo.setDocumentId("1000");
			documentInfo.setDocumentName("Collaboration Testing Presentation");
			
			parameters.setDocumentInfo(documentInfo);
			
			APIResponse<ShowResponseHandler> response = sdkOperations.createPresentation(parameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse showResponse = (CreateDocumentResponse) response.getObject();

				LOGGER.log(Level.INFO, "Presentation document id - {0}", new Object[] { showResponse.getDocumentId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session 1 id - {0}", new Object[] { showResponse.getSessionId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session 1 url - {0}", new Object[] { showResponse.getDocumentUrl() }); //No I18N
				
				UserInfo user2Info = new UserInfo();
				
				user2Info.setUserId("200");
				user2Info.setDisplayName("Karan");

				parameters.setUserInfo(user2Info);
				
				response = sdkOperations.createPresentation(parameters);
				responseStatusCode = response.getStatusCode();
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					showResponse = (CreateDocumentResponse) response.getObject();

					LOGGER.log(Level.INFO, "Presentation document id - {0}", new Object[] { showResponse.getDocumentId() }); //No I18N
					LOGGER.log(Level.INFO, "Presentation session 2 id - {0}", new Object[] { showResponse.getSessionId() }); //No I18N
					LOGGER.log(Level.INFO, "Presentation session 2 url - {0}", new Object[] { showResponse.getDocumentUrl() }); //No I18N
					
					LOGGER.log(Level.INFO, "Use above to session url to test the collaboration");
				}
			} else {
				InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Presentation configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in creating presentation session url - ", e); //No I18N
		}
	}
	
	public static boolean initializeSdk() {
		boolean status = false;

		try {
			Logger logger = new Logger.Builder()
			        .level(Levels.INFO)
			        //.filePath("<file absolute path where logs would be written>") //No I18N
			        .build();

			List<Token> tokens = new ArrayList<Token>();
			Auth auth = new Auth.Builder().addParam("apikey", "2ae438cf864488657cc9754a27daa480").authenticationSchema(new Authentication.TokenFlow()).build();
			
			tokens.add(auth);
			
			new Initializer.Builder()
				.environment(new USDataCenter.Production())
				.tokens(tokens)
				.logger(logger)
				.initialize();
			
			status = true;
} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating presentation session url - ", e); //No I18N
		}
		return status;
	}
}
