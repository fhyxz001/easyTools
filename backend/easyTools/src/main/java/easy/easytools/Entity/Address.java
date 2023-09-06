package easy.easytools.Entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Address {
    String id;
    String location;
    Boolean isStart;
    String name;
    BigDecimal distance;

}
