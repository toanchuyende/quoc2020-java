package vn.agileviet.quoc2020.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 8:46 AM
 */
@Document
public class Lecturer {
    @Id
    private String id;
    private String name;
    private String about;
    private String vnumail;
    private String mail;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getVnumail() {
        return vnumail;
    }

    public void setVnumail(String vnumail) {
        this.vnumail = vnumail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Không được để trống
     * và phải duy nhất
     */
    @NotEmpty
    @Indexed(unique = true)
    private String facultyId;

    /**
     * Không được để trống
     * và phải duy nhất
     */
    @NotEmpty
    @Indexed(unique = true)
    private String userId;


}
