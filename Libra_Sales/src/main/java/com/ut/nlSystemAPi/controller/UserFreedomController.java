package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.Users.UserFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import com.ut.nlSystemAPi.service.UserFreedomService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/user-freedom")
@Api(tags = "02. User Freedom", description = "User Freedom Resource")
@Timed
public class UserFreedomController {

    @Autowired
    private UserFreedomService userFreedomService;

    @PostMapping("/list")
    @ApiOperation(value = "List user by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody UserFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return userFreedomService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return userFreedomService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Add new user", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "firstName", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "lastName", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "expireDate", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "sex", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "dob", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "address", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "telephone", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nationalityId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "freedomWarehouse", dataType = "long", paramType = "form", required = true, allowMultiple = true),
            @ApiImplicitParam(name = "username", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "userGroup", dataType = "string", paramType = "form", required = true, allowMultiple = true)
    })
    public ResponseMessage<BaseResult> add(@ApiIgnore User user, BindingResult bindingResult, @RequestPart(name = "file_signature", required = false) MultipartFile file, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return userFreedomService.insert(user, file, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Update user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "firstName", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "lastName", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "expireDate", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "sex", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "dob", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "address", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "telephone", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nationalityId", dataType = "long", paramType = "form", required = true),
            @ApiImplicitParam(name = "freedomWarehouse", dataType = "long", paramType = "form", required = true, allowMultiple = true),
            @ApiImplicitParam(name = "username", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "userGroup", dataType = "string", paramType = "form", required = true, allowMultiple = true)
    })
    public ResponseMessage<BaseResult> update(@ApiIgnore User user, BindingResult bindingResult, @RequestPart(name = "file_signature", required = false) MultipartFile file, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return userFreedomService.update(user, file, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<String> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return userFreedomService.delete(id, httpServletRequest);
    }

    @PostMapping("/me")
    @ApiOperation(value = "Get logged in user", notes = "Photo Type: 0. by upload; 1. by webcam; 2. by java; employeeGender: F. Female, M. Male", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<UserResponse> me(HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return userFreedomService.me(httpServletRequest);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Change password", notes = "Change password", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "newPassword", dataType = "string", paramType = "form", required = true)
    })
    public ResponseMessage<String> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return userFreedomService.changePassword(oldPassword, newPassword,httpServletRequest);
    }

}
