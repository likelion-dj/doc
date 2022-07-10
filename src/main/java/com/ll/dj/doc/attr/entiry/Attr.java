package com.ll.dj.doc.attr.entiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private LocalDateTime createdDate;
    @NotNull
    private LocalDateTime modifiedDate;
    @NotNull
    private String relTypeCode;
    @NotNull
    private long relId;
    @NotNull
    private String typeCode;
    @NotNull
    private String type2Code;
    @NotNull
    private String value;
    private LocalDateTime expireDate;

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
