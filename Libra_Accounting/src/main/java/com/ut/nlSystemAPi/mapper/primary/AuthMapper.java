package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.CheckUserLogin;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.Login.AccessTokenRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.ut.nlSystemAPi.model.response.AccessTokenResponse;

import java.util.List;


@Repository
public interface AuthMapper {

    Boolean insertClientId(@Param("clientId") String clientId, @Param("clientName") String clientName, @Param("clientSecure") String clientSecure, @Param("tokenValidate") int tokenValidate, @Param("refreshTokenValidate") int refreshTokenValidate);

    Long checkUserValid(@Param("username") String username);

    Long checkStatusUserLogin(@Param("username") String username);

    String checkUserLogout(@Param("username") String username, @Param("clientId") String clientId);

    Boolean clearUserToken(@Param("username") String username, @Param("clientId") String clientId, @Param("tokenId") String tokenId);

    Boolean updateUser(@Param("qrCode") String qrCode, @Param("username") String username, @Param("secretKey") String secretKey);

    String getSecretKey(@Param("username") String username);

    Long checkLoginVerifyToken(@Param("username") String username, @Param("loginVerifyToken") String loginVerifyToken);

    List<AccessTokenResponse> getListAccessToken(@Param("filter") Filter filter);

    Long updateStatusLogin(@Param("username") String username);

    Long checkIpAddress(@Param("hostName") String hostName, @Param("hostAddress") String hostAddress);

    Boolean updateCheckUserLogin(@Param("hostName") String hostName, @Param("hostAddress") String hostAddress);

    Boolean insertCheckUserLogin(@Param("checkUserLogin") CheckUserLogin checkUserLogin);

    Long countNumberOfLoginByIpAddress(@Param("hostName") String hostName, @Param("hostAddress") String hostAddress);

    Long checkUserLoginOrNot(@Param("username") String username, @Param("loginVerifyToken") String loginVerifyToken);

    Boolean clearRechaptcha(@Param("hostName") String hostName, @Param("hostAddress") String hostAddress);

    Boolean insertAccessToken(@Param("accessTokenRequest") AccessTokenRequest accessTokenRequest);

    Boolean updateAccessToken(@Param("accessTokenRequest") AccessTokenRequest accessTokenRequest);

    Long checkDuplicateUsername(@Param("username") String username);

    Long updateUserLoginAuth(@Param("username") String username);

}