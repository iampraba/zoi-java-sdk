package com.zoho.officeintegrator.v1.examples.writer;

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
import com.zoho.officeintegrator.v1.CreateDocumentParameters;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.DocumentMeta;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class GetDocumentInfo {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(GetDocumentInfo.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CreateDocumentParameters createDocumentParams = new CreateDocumentParameters();
			
			APIResponse<WriterResponseHandler> response = sdkOperations.createDocument(createDocumentParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse responseObj = (CreateDocumentResponse) response.getObject();
				String documentId = responseObj.getDocumentId();
				
				LOGGER.log(Level.INFO, "Document - {0} has been created to demonstrate the get document info api url.", new Object[] { documentId }); //No I18N
				
				response = sdkOperations.getDocumentInfo(documentId);
				
				responseStatusCode = response.getStatusCode();
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					DocumentMeta documentMeta = (DocumentMeta) response.getObject();

					LOGGER.log(Level.INFO, "Document id - {0}", new Object[] { documentMeta.getDocumentId() }); //No I18N
					LOGGER.log(Level.INFO, "Document Name - {0}", new Object[] { documentMeta.getDocumentName() }); //No I18N
					LOGGER.log(Level.INFO, "Document Type - {0}", new Object[] { documentMeta.getDocumentType() }); //No I18N
					LOGGER.log(Level.INFO, "Document Expires on- {0}", new Object[] { documentMeta.getExpiresOn() }); //No I18N
					LOGGER.log(Level.INFO, "Document Created on- {0}", new Object[] { documentMeta.getCreatedTime() }); //No I18N
					LOGGER.log(Level.INFO, "Active sessions count - {0}", new Object[] { documentMeta.getActiveSessionsCount() }); //No I18N
					LOGGER.log(Level.INFO, "Collaborators count - {0}", new Object[] { documentMeta.getCollaboratorsCount() }); //No I18N
				} else {
					InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();

					String errorMessage = invalidConfiguration.getMessage();
					
					/*Long errorCode = invalidConfiguration.getCode();
					String errorKeyName = invalidConfiguration.getKeyName();
					String errorParameterName = invalidConfiguration.getParameterName();*/
					
					LOGGER.log(Level.INFO, "Failed to get the document details for document id - {0} - Error message - {1}", new Object[] { documentId, errorMessage }); //No I18N
				}
				
			} else {
				InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating document session url - ", e); //No I18N
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
