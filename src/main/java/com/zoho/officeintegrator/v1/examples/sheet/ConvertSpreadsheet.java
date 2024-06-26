package com.zoho.officeintegrator.v1.examples.sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;

import com.zoho.api.authenticator.Auth;
import com.zoho.api.authenticator.Token;
import com.zoho.officeintegrator.Initializer;
import com.zoho.officeintegrator.dc.USDataCenter;
import com.zoho.officeintegrator.logger.Logger;
import com.zoho.officeintegrator.logger.Logger.Levels;
import com.zoho.officeintegrator.util.APIResponse;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.FileBodyWrapper;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.SheetConversionOutputOptions;
import com.zoho.officeintegrator.v1.SheetConversionParameters;
import com.zoho.officeintegrator.v1.SheetResponseHandler;
import com.zoho.officeintegrator.v1.V1Operations;

public class ConvertSpreadsheet {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ConvertSpreadsheet.class.getName());

	public static void main(String args[]) {
		
		try {
			//Initializing SDK once is enough. Calling here since code sample will be tested standalone. 
	        //You can place SDK initializer code in you application and call once while your application start-up. 
			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			SheetConversionParameters conversionParameters = new SheetConversionParameters();
			
			//Either use url as document source or attach the document in request body use below methods
			conversionParameters.setUrl("https://demo.office-integrator.com/samples/sheet/Contact_List.xlsx");

			/*
			String inputFilePath = "Absolute input file path"
			StreamWrapper documentStreamWrapper = new StreamWrapper(inputFilePath);

			conversionParameters.setDocument(documentStreamWrapper); */

			SheetConversionOutputOptions outputOptions = new SheetConversionOutputOptions();
			
			outputOptions.setFormat("pdf");
			outputOptions.setDocumentName("ConvertedSheetFile.pdf");
			
			conversionParameters.setOutputOptions(outputOptions);
			
			//TODO: Need to check where to pass the password parameter
			//conversionParameters.setPassword("****");
			
			APIResponse<SheetResponseHandler> response = sdkOperations.convertSheet(conversionParameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				FileBodyWrapper fileBodyWrapper = (FileBodyWrapper) response.getObject();
				String outputFilePath = System.getProperty("user.dir") + File.separator + fileBodyWrapper.getFile().getName();
				InputStream inputStream = fileBodyWrapper.getFile().getStream();
				OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
				
				IOUtils.copy(inputStream, outputStream);
				LOGGER.log(Level.INFO, "Converted sheet saved in output file path - {0}", new Object[] { outputFilePath }); //No I18N
			} else {
				InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();
				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in creating document session url - ", e); //No I18N
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
