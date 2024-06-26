package com.zoho.officeintegrator.v1.examples.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.zoho.api.authenticator.Auth;
import com.zoho.api.authenticator.Token;
import com.zoho.officeintegrator.Initializer;
import com.zoho.officeintegrator.dc.USDataCenter;
import com.zoho.officeintegrator.logger.Logger;
import com.zoho.officeintegrator.logger.Logger.Levels;
import com.zoho.officeintegrator.util.APIResponse;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.CallbackSettings;
import com.zoho.officeintegrator.v1.CreateDocumentParameters;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.DocumentDefaults;
import com.zoho.officeintegrator.v1.DocumentInfo;
import com.zoho.officeintegrator.v1.EditorSettings;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.Margin;
import com.zoho.officeintegrator.v1.UiOptions;
import com.zoho.officeintegrator.v1.UserInfo;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class CreateDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CreateDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//Initializing SDK once is enough. Calling here since code sample will be tested standalone. 
	        //You can place SDK initializer code in you application and call once while your application start-up. 
			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CreateDocumentParameters createDocumentParams = new CreateDocumentParameters();
			
			DocumentInfo documentInfo = new DocumentInfo();
			
			documentInfo.setDocumentName("Untilted Document");
			//System time value used to generate unique document everytime. You can replace based on your application.
			documentInfo.setDocumentId("" + System.currentTimeMillis());
			
			createDocumentParams.setDocumentInfo(documentInfo);
			
			UserInfo userInfo = new UserInfo();
			
			userInfo.setUserId("1000");
			userInfo.setDisplayName("John");
			
			createDocumentParams.setUserInfo(userInfo);

			Margin margin = new Margin();
			
			margin.setTop("2in");
			margin.setBottom("2in");
			margin.setLeft("2in");
			margin.setRight("2in");

			DocumentDefaults documentDefault = new DocumentDefaults();
			
			documentDefault.setFontSize(14);
			documentDefault.setFontName("Arial");
			documentDefault.setPaperSize("Letter");
			documentDefault.setOrientation("portrait");
			documentDefault.setTrackChanges("disabled");
			
			documentDefault.setMargin(margin);
			createDocumentParams.setDocumentDefaults(documentDefault);
			
			EditorSettings editorSettings = new EditorSettings();
			
			editorSettings.setUnit("in");
			editorSettings.setLanguage("en");
			editorSettings.setView("pageview");
			
			createDocumentParams.setEditorSettings(editorSettings);
			
			UiOptions uiOptions = new UiOptions();
			
			uiOptions.setChatPanel("show");
			uiOptions.setDarkMode("show");
			uiOptions.setFileMenu("show");
			uiOptions.setSaveButton("show");
			
			createDocumentParams.setUiOptions(uiOptions);
			
			Map<String, Object> permissions = new HashMap<String, Object>();
			
			permissions.put("collab.chat", false);
            permissions.put("document.edit", true);
            permissions.put("review.comment", false);
            permissions.put("document.export", true);
			permissions.put("document.print", false);
			permissions.put("document.fill", false);
            permissions.put("review.changes.resolve", false);
            permissions.put("document.pausecollaboration", false);
            
			createDocumentParams.setPermissions(permissions);
			
			Map<String, Object> saveUrlParams = new HashMap<String, Object>();
			
			saveUrlParams.put("id", 123456789);
			saveUrlParams.put("auth_token", "oswedf32rk");
			
			
			CallbackSettings callbackSettings = new CallbackSettings();
			
			callbackSettings.setRetries(2);
			callbackSettings.setTimeout(10000);
			callbackSettings.setSaveFormat("docxz");
			callbackSettings.setHttpMethodType("post");
			callbackSettings.setSaveUrlParams(saveUrlParams);
			callbackSettings.setSaveUrl("https://officeintegrator.zoho.com/v1/api/webhook/savecallback/601e12157123434d4e6e00cc3da2406df2b9a1d84a903c6cfccf92c8286");
			
			createDocumentParams.setCallbackSettings(callbackSettings);
			
			APIResponse<WriterResponseHandler> response = sdkOperations.createDocument(createDocumentParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse documentResponse = (CreateDocumentResponse) response.getObject();

				LOGGER.log(Level.INFO, "Document id - {0}", new Object[] { documentResponse.getDocumentId() }); //No I18N
				LOGGER.log(Level.INFO, "Document session id - {0}", new Object[] { documentResponse.getSessionId() }); //No I18N
				LOGGER.log(Level.INFO, "Document session url - {0}", new Object[] { documentResponse.getDocumentUrl() }); //No I18N
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
