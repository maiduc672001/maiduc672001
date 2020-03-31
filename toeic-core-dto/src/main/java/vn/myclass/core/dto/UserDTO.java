package vn.myclass.core.dto;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UserDTO implements Serializable {
    private Integer userId;
    private String name;
    private String password;
    private String fullName;
    private Timestamp createdDate;
    private RoleDTO roleDTO;
    private UserImportDTO userImportDTO;

    public List<ResultDTO> getResultDTOS() {
        return resultDTOS;
    }

    public void setResultDTOS(List<ResultDTO> resultDTOS) {
        this.resultDTOS = resultDTOS;
    }

    private List<ResultDTO> resultDTOS;

    public UserImportDTO getUserImportDTO() {
        return userImportDTO;
    }

    public void setUserImportDTO(UserImportDTO userImportDTO) {
        this.userImportDTO = userImportDTO;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }
}
