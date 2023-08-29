package hello.back.backproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass @Getter
public abstract class BaseEntity {

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(nullable = false, length = 100)
  private String createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime modifiedAt;

  @LastModifiedBy
  @Column(nullable = false, length = 100)
  private String modifiedBy;
}
