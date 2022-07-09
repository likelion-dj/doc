package com.ll.dj.doc.attr.entiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(
        indexes = {
                @Index(name = "uniqueIndex1", columnList = "relTypeCode, relId, typeCode, type2Code", unique = true),
                @Index(name = "index1", columnList = "relTypeCode, typeCode, type2Code")
        }
)
@NoArgsConstructor
public class Attr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime expireDate;
    private String relTypeCode;
    private long relId;
    private String typeCode;
    private String type2Code;
    private String value;

    public Attr(String relTypeCode, long relId, String typeCode, String type2Code, String value, LocalDateTime expireDate) {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.typeCode = typeCode;
        this.type2Code = type2Code;
        this.value = value;
        this.expireDate = expireDate;
    }
}
