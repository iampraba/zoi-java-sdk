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
import com.zoho.officeintegrator.util.StreamWrapper;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.CompareDocumentParameters;
import com.zoho.officeintegrator.v1.CompareDocumentResponse;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class CompareDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CompareDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//Initializing SDK once is enough. Calling here since code sample will be tested standalone. 
	        //You can place SDK initializer code in you application and call once while your application start-up. 
			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CompareDocumentParameters compareDocumentParameters = new CompareDocumentParameters();
			
//			compareDocumentParameters.setUrl1("https://demo.office-integrator.com/zdocs/MS_Word_Document_v0.docx");
//			compareDocumentParameters.setUrl2("https://demo.office-integrator.com/zdocs/MS_Word_Document_v1.docx");
            
			String file1Name = "MS_Word_Document_v0.docx";
			String file2Name = "MS_Word_Document_v1.docx";

			String inputFile1Path = "/Users/praba-2086/Downloads/MS_Word_Document_v1.docx";
			StreamWrapper file1StreamWrapper = new StreamWrapper(inputFile1Path);

			compareDocumentParameters.setDocument1(file1StreamWrapper);
			
			String inputFile2Path = "/Users/praba-2086/Downloads/MS_Word_Document_v0.docx";
			StreamWrapper file2StreamWrapper = new StreamWrapper(inputFile2Path);

			compareDocumentParameters.setDocument2(file2StreamWrapper);

			compareDocumentParameters.setLang("en");
            compareDocumentParameters.setTitle(file1Name + " vs " + file2Name);
			
			APIResponse<WriterResponseHandler> response = sdkOperations.compareDocument(compareDocumentParameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CompareDocumentResponse documentResponse = (CompareDocumentResponse) response.getObject();

				LOGGER.log(Level.INFO, "Document compare url - {0}", new Object[] { documentResponse.getCompareUrl() }); //No I18N
			} else {
				InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();
				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in comparing documents - ", e); //No I18N
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
