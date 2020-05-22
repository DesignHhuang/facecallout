package com.face.callout.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "call_out")
@EntityListeners(AuditingEntityListener.class)
public class CallOut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 所属Person
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // 所属数据集
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    //图片id
    private String imageId;

    //图片地址
    private String imageUrl;

    //姓名
    private String name;

    //扩展属性数组
    private String attrArray;

    //性别（0：男，1：女）
    private boolean sex;

    //年龄
    private int age = 5;

    //是否有眼镜（0：没有，1：有）
    private boolean isGlasses = false;

    //是否有墨镜（0：没有，1：有）
    private boolean isSunGlasses = false;

    //是否有帽子（0：没有，1：有）
    private boolean isHat = false;

    //是否有口罩（0：没有，1：有）
    private boolean isMasks = false;

    //国家(0：中国，1：外国)
    private boolean country = false;

    //是否标注(0：没标注，1：已标注)
    private boolean isMark = false;

    //创建时间
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    //标注时间
    private Date labeledAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGlasses() {
        return isGlasses;
    }

    public void setGlasses(boolean glasses) {
        isGlasses = glasses;
    }

    public boolean isSunGlasses() {
        return isSunGlasses;
    }

    public void setSunGlasses(boolean sunGlasses) {
        isSunGlasses = sunGlasses;
    }

    public boolean isHat() {
        return isHat;
    }

    public void setHat(boolean hat) {
        isHat = hat;
    }

    public boolean isMasks() {
        return isMasks;
    }

    public void setMasks(boolean masks) {
        isMasks = masks;
    }

    public boolean isCountry() {
        return country;
    }

    public void setCountry(boolean country) {
        this.country = country;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAttrArray() {
        return attrArray;
    }

    public void setAttrArray(String attrArray) {
        this.attrArray = attrArray;
    }

    public Date getLabeledAt() {
        return labeledAt;
    }

    public void setLabeledAt(Date labeledAt) {
        this.labeledAt = labeledAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
