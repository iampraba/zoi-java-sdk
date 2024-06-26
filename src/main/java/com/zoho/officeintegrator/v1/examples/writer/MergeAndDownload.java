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
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.FileBodyWrapper;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.MergeAndDownloadDocumentParameters;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class  MergeAndDownload {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(MergeAndDownload.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			//SDK Initialisation code ends.
			
			V1Operations sdkOperations = new V1Operations();
			MergeAndDownloadDocumentParameters parameters = new MergeAndDownloadDocumentParameters();

            parameters.setFileUrl("https://demo.office-integrator.com/zdocs/OfferLetter.zdoc");
            parameters.setMergeDataJsonUrl("https://demo.office-integrator.com/data/candidates.json");
            
            /* String filesHomeDirectory = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
            String documentFilePath = filesHomeDirectory + File.separator + "sample_documents" + File.separator + "OfferLetter.zdoc";
            String mergeJsonDataFilePath = filesHomeDirectory + File.separator + "sample_documents" + File.separator + "candidates.json";
            
			StreamWrapper documentStreamWrapper = new StreamWrapper(documentFilePath);
			StreamWrapper mergeDataStreamWrapper = new StreamWrapper(mergeJsonDataFilePath);
			
			parameters.setFileContent(documentStreamWrapper);
			parameters.setMergeDataJsonContent(mergeDataStreamWrapper); */

            
            parameters.setPassword("***");
            parameters.setOutputFormat("pdf");
			
			APIResponse<WriterResponseHandler> response = sdkOperations.mergeAndDownloadDocument(parameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				FileBodyWrapper fileBodyWrapper = (FileBodyWrapper) response.getObject();
				String outputFilePath = System.getProperty("user.dir") + File.separator + fileBodyWrapper.getFile().getName();
				InputStream inputStream = fileBodyWrapper.getFile().getStream();
				OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
				
				IOUtils.copy(inputStream, outputStream);
				LOGGER.log(Level.INFO, "Merged document saved in output file path - {0}", new Object[] { outputFilePath }); //No I18N
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
