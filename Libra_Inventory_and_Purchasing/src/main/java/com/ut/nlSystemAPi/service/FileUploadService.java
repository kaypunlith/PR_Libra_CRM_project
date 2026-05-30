package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface FileUploadService {

  ResponseMessage<BaseResult> insert(MultipartFile file, HttpServletRequest httpServletRequest) throws UnknownHostException;

}