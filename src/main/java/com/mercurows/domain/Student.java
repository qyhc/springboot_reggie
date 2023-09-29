package com.mercurows.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

// 当成员类名与数据库名字不一致时使用该注解
@TableName("Student")
public class Student {
    // 数据库默认的主键自增方式
    // @TableId(type = IdType.AUTO)
    private Integer id;
    private String nameCh;
    private String nameEn;
    private String sex;
    private Integer age;
    // 将telephone对应数据库中的tell字段，并且由于这是隐私数据，所以不参与查询
    @TableField(value = "tell",select = false)
    private Integer telephone;
    // 这是数据库没有而实体类独有的，为了防止Java查询数据库
    // 试图查询该数据时报错需要标记其不存在
    @TableField(exist = false)
    private boolean isGraduate;
    // 默认值与数据库中的一样为0 被删除标记为1
    // @TableLogic(value = "0",delval = "1")
    private Integer deleted;
    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }
    public boolean isGraduate() {
        return isGraduate;
    }
    public void setGraduate(boolean isGraduate) {
        this.isGraduate = isGraduate;
    }
    public Integer getTelephone() {
        return telephone;
    }
    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNameCh() {
        return nameCh;
    }
    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getDeleted() {
        return deleted;
    }
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", nameCh=" + nameCh + ", nameEn=" + nameEn + ", sex=" + sex + ", age=" + age
                + ", telephone=" + telephone + ", isGraduate=" + isGraduate + ", deleted=" + deleted + ", version="
                + version + "]";
    }

}
