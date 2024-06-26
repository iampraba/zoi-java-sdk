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
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.SessionMeta;
import com.zoho.officeintegrator.v1.ShowResponseHandler;
import com.zoho.officeintegrator.v1.V1Operations;

public class GetSessionInfo {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(GetSessionInfo.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();
			
			V1Operations sdkOperations = new V1Operations();
			CreatePresentationParameters createSpreadsheetParams = new CreatePresentationParameters();
			
			APIResponse<ShowResponseHandler> response = sdkOperations.createPresentation(createSpreadsheetParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse showResponse = (CreateDocumentResponse) response.getObject();
				String sessionid = showResponse.getSessionId();

				LOGGER.log(Level.INFO, "Presentation document id - {0}", new Object[] { showResponse.getDocumentId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session id - {0}", new Object[] { showResponse.getSessionId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session url - {0}", new Object[] { showResponse.getDocumentUrl() }); //No I18N

				response = sdkOperations.getPresentationSession(sessionid);
				
				LOGGER.log(Level.INFO, "Get presentation details request status - {0}", new Object[] { response.getStatusCode() }); //No I18N
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					SessionMeta sessionMeta = (SessionMeta) response.getObject();
					
					LOGGER.log(Level.INFO, "Session status- {0}", new Object[] { sessionMeta.getStatus() }); //No I18N
					LOGGER.log(Level.INFO, "Session Expires on - {0}", new Object[] { sessionMeta.getInfo().getExpiresOn() }); //No I18N
				} else {
					InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();

					String errorMessage = invalidConfiguration.getMessage();
					
					/*Long errorCode = invalidConfiguration.getCode();
					String errorKeyName = invalidConfiguration.getKeyName();
					String errorParameterName = invalidConfiguration.getParameterName();*/
					
					LOGGER.log(Level.INFO, "Presentation configuration error - {0}", new Object[] { errorMessage }); //No I18N
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
			LOGGER.log(Level.INFO, "Exception in creating document session url - ", e); //No I18N
		}
		return status;
	}
}
