package vn.agileviet.quoc2020.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 7:09 AM
 */

@Document
public class Parent {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Student> getChildren() {
        return children;
    }

    public void setChildren(List<Student> children) {
        this.children = children;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;
    private String address;
    private String phone;
    private String mail;

//    /**
//     * Chứa một danh sách mã số các sinh viên.
//     */
//    private List<String> children;

    /**
     * Chứa một danh sách mã số các sinh viên.
     * https://docs.spring.io/spring-data/data-mongodb/docs/current-SNAPSHOT/reference/html/#mapping-usage-references
     */
    @Field("children")
    private @DBRef List<Student> children;

    public List<String> getListIdChildren() {
        return listIdChildren;
    }

    public void setListIdChildren(List<String> listIdChildren) {
        this.listIdChildren = listIdChildren;
    }

    @Field("listIdChildren")
    private List<String> listIdChildren;
    /**
     * Không được để trống
     * và phải duy nhất
     */
    @NotEmpty
    @Indexed(unique = true)
    private String userId;

}
