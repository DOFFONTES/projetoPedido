package com.davidFontes.servicos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.davidFontes.servicos.exception.ArquivoException;

@Service
public class S3Servico {
	
	private Logger LOG = LoggerFactory.getLogger(S3Servico.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String nomeArquivo = multipartFile.getOriginalFilename();
			InputStream is= multipartFile.getInputStream();
			String tipoArquivo = multipartFile.getContentType();
			return uploadFile(is, nomeArquivo, tipoArquivo);
		} catch (IOException e) {
			throw new ArquivoException("Erro de IO: " + e.getMessage());
		}
			
	}
	
	public URI uploadFile(InputStream is, String nomeArquivo, String tipoArquivo) {
		
		try {	
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(tipoArquivo);
			LOG.info("Iniciando upload");
			s3client.putObject(bucketName, nomeArquivo, is, meta);
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucketName, nomeArquivo).toURI();
		
		} catch (URISyntaxException e) {
			throw new ArquivoException("Erro ao converter URL para URI");
		}
	
	}
}
