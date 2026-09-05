package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Employee;
import com.ut.nlSystemAPi.model.EmployeeAchievement;
import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.EmployeeMistake;
import com.ut.nlSystemAPi.model.base.BaseFile;
import com.ut.nlSystemAPi.model.request.ApplyEmployeeTypesRequest;
import com.ut.nlSystemAPi.model.request.EmployeeTerminateSessionRequest;
import com.ut.nlSystemAPi.model.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesMapper {

    List<EmployeesResponse> getList(@Param("filter") EmployeeFilter filter);

    Long countList(@Param("filter") EmployeeFilter filter);

    List<EmployeesResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("nameKh") String nameKh, @Param("idCard") String idCard, @Param("id") Long id);

    Boolean insert(@Param("employee") Employee employee);

    Boolean update(@Param("employee") Employee employee);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertEmpWorkshift(@Param("empId") Long empId, @Param("workShiftId") Long workShiftId, @Param("status") Integer status, @Param("creatorId") Long creatorId);

    Boolean deleteEmpWorkshift(@Param("empId") Long empId, @Param("status") Integer status, @Param("deletorId") Long deletorId);

    List<EmployeeWorkshiftResponse> listEmpWorkshift(@Param("empId") Long empId);

    List<EmployeeSecondWorkshiftResponse> listEmpSecondWorkshift(@Param("empId") Long empId);

    List<EmployeeDocumentResponse> listEmpDocuments(@Param("empId") Long empId);

    List<EmployeePositionHistory> listEmpPositonHistory(@Param("employeesId") Long employeesId);

    List<EmployeeDepartmentHistory> listEmpDepartmentHistory(@Param("employeesId") Long employeesId);

    List<EmployeeDocumentResponse> listEmployeeLocationGroups(@Param("employeeId") Long employeeId);

    List<EmployeeDocumentResponse> listEmployeeSalesReps(@Param("employeeId") Long employeeId);

    BaseFile getProfilePhoto(@Param("id") Long id);

    List<EmployeeGroupResponse> getEmployeeGroup(@Param("employeeId") Long employeeId);

    Boolean deleteEmpDocuments(@Param("empId") Long empId);

    Boolean insertEmpDocument(@Param("empId") Long empId, @Param("fileName") String fileName, @Param("fileUrl") String fileUrl);

    Long getUserIdByEmployeeId(@Param("employeeId") Long employeeId);

    List<EmployeeConnectedDeviceResponse> getConnectedDevice(@Param("employeeId") Long employeeId);

    Boolean terminateSession(@Param("employeeTerminateSessionRequest") EmployeeTerminateSessionRequest employeeTerminateSessionRequest);

    List<EmployeesResponse> getReportStaffProfile(@Param("filter") EmployeeFilter filter, @Param("userId") Long userId);

    Boolean updateUser(@Param("userId") Long userId, @Param("id") Long id);

    Boolean updateQrCode(@Param("urlQRCode") String urlQRCode, @Param("employeeId") Long employeeId);

    Boolean insertEmployeeEgroup(@Param("groupId") Long groupId, @Param("employeeId") Long employeeId);


    Boolean deleteEmployeeEgroup(@Param("employeeId") Long employeeId);

    Boolean insertEmployeeTypes(@Param("applyEmployeeTypesRequest") ApplyEmployeeTypesRequest applyEmployeeTypesRequest);

    Boolean insertEmployeeStatus(@Param("applyEmployeeTypesRequest") ApplyEmployeeTypesRequest applyEmployeeTypesRequest);

    Boolean insertPositionHistory(@Param("employeesId") Long employeesId, @Param("positionId") Long positionId, @Param("createdBy") Long createdBy);

    Boolean insertDepartmentHistory(@Param("employeesId") Long employeesId, @Param("departmentId") Long departmentId, @Param("createdBy") Long createdBy);

    List<EmployeeAchievement> getEmployeeAchievement(@Param("id") Long employeesId);

    List<EmployeeMistake> getEmployeeMistake(@Param("id") Long employeesId);

    Long checkUserEditAchievement(@Param("userId") Long userId);

    Long checkUserEditMistake(@Param("userId") Long userId);

    Boolean insertEmployeeLocationGroup(@Param("employeeId") Long employeeId, @Param("locationGroupId") Long locationGroupId);

    Boolean deleteEmployeeLocationGroups(@Param("employeeId") Long employeeId);

    Boolean insertEmployeeSalesRep(@Param("employeeId") Long employeeId, @Param("salesRepId") Long salesRepId);

    Boolean deleteEmployeeSalesRep(@Param("employeeId") Long employeeId);

    Long checkDuplicateUsername(@Param("username") String username, @Param("id") Long id);
}
