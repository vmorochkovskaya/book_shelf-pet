package org.example.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("InvalidatedToken")
@AllArgsConstructor
@NoArgsConstructor
public class InvalidatedToken implements Serializable {
    private String id;
}
