package com.zoho.officeintegrator.v1.examples.writer;

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
import com.zoho.officeintegrator.util.StreamWrapper;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.FileBodyWrapper;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WatermarkParameters;
import com.zoho.officeintegrator.v1.WatermarkSettings;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class WatermarkDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(WatermarkDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			WatermarkParameters waterMarkParams = new WatermarkParameters();
			
//			waterMarkParams.setUrl("https://demo.office-integrator.com/zdocs/MS_Word_Document_v0.docx");
			
			String inputFilePath = "/Users/praba-2086/Downloads/MS_Word_Document_v0.docx";
			StreamWrapper documentStreamWrapper = new StreamWrapper(inputFilePath);
			
			waterMarkParams.setDocument(documentStreamWrapper);
			
			WatermarkSettings waterMarkSettings = new WatermarkSettings();

			waterMarkSettings.setType("text");
			waterMarkSettings.setFontSize(36);
			waterMarkSettings.setOpacity(70.00);
			waterMarkSettings.setFontName("Arial");
			waterMarkSettings.setFontColor("#000000");
			waterMarkSettings.setOrientation("horizontal");
			waterMarkSettings.setText("Sample Water Mark Text");
			
			waterMarkParams.setWatermarkSettings(waterMarkSettings);

			APIResponse<WriterResponseHandler> response = sdkOperations.createWatermarkDocument(waterMarkParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				FileBodyWrapper fileBodyWrapper = (FileBodyWrapper) response.getObject();
				String outputFilePath = System.getProperty("user.dir") + File.separator + fileBodyWrapper.getFile().getName();
				InputStream inputStream = fileBodyWrapper.getFile().getStream();
				OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
				
				IOUtils.copy(inputStream, outputStream);
				LOGGER.log(Level.INFO, "Water marked document saved in output file path - {0}", new Object[] { outputFilePath }); //No I18N
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
